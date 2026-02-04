package `in`.co.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        /*
         * Launch a coroutine tied to Activity lifecycle
         */
        lifecycleScope.launch {
            Log.d("CoroutineDemo", "Activity coroutine started on thread: ${Thread.currentThread().name}")

            // Call a suspend function directly from ViewModel
            val userData = withContext(Dispatchers.IO) {
                viewModel.loadUserData() // suspend function in ViewModel
            }

            // Back on Main thread to update UI
            Log.d("CoroutineDemo", "Received user data in Activity: $userData")

            Log.d("CoroutineDemo", "Activity coroutine ended")
        }
    }
}
