package `in`.co.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val suspendingFunction = SuspendingFunction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val job = CoroutineScope(Dispatchers.IO).launch {
            Log.d("CoroutineDemo", "Main coroutine started")
        }

//        job.join()
        Log.d("CoroutineDemo", "Main coroutine ended")


// Suspending Function class call
        // Coroutine 1
//        lifecycleScope.launch {
//            Log.d("CoroutineDemo", "Main coroutine 1 started")
//
//            withContext(Dispatchers.IO) {
//                suspendingFunction.task1()
//            }
//
//            Log.d("CoroutineDemo", "Main coroutine 1 ended")
//        }

        // Coroutine 2
//        lifecycleScope.launch {
//            Log.d("CoroutineDemo", "Main coroutine 2 started")
//
//            withContext(Dispatchers.IO) {
//                suspendingFunction.task2()
//            }
//
//            Log.d("CoroutineDemo", "Main coroutine 2 ended")
//        }
    }
}
