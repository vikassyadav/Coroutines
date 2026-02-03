package `in`.co.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // lifecycleScope is tied to Activity lifecycle (safe, no memory leaks)
        lifecycleScope.async {
            Log.d("CoroutineDemo", "Main coroutine started")

            // Calling suspend function
            printFollowers()

            Log.d("CoroutineDemo", "Main coroutine ended")
        }
    }

    // This must be suspend because it calls another suspend function
    private suspend fun printFollowers() {

        // Switch to IO thread for network / DB work
        val fbFollowers = withContext(Dispatchers.IO) {
            getFbFollowers()
        }

        // This runs on Main thread again
        Log.d("CoroutineDemo", "Facebook Followers: $fbFollowers")
    }

    // Simulating network call
    private suspend fun getFbFollowers(): Int {
        delay(200) // pretend API delay
        return 52
    }
}
