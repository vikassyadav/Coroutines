package `in`.co.coroutines

import android.util.Log
import kotlinx.coroutines.delay



/*
Call this in main activity

 // Coroutine 1
        lifecycleScope.launch {
            Log.d("CoroutineDemo", "Main coroutine 1 started")

            withContext(Dispatchers.IO) {
                suspendingFunction.task1()
            }

            Log.d("CoroutineDemo", "Main coroutine 1 ended")
        }

        // Coroutine 2
        lifecycleScope.launch {
            Log.d("CoroutineDemo", "Main coroutine 2 started")

            withContext(Dispatchers.IO) {
                suspendingFunction.task2()
            }

            Log.d("CoroutineDemo", "Main coroutine 2 ended")
        }
 */
class SuspendingFunction {

    suspend fun task1() {
        Log.d("CoroutineDemo", "Task1 STARTED")
        delay(2000)
        Log.d("CoroutineDemo", "Task1 ENDED")
    }

    suspend fun task2() {
        Log.d("CoroutineDemo", "Task2 STARTED")
        delay(2000)
        Log.d("CoroutineDemo", "Task2 ENDED")
    }
}
