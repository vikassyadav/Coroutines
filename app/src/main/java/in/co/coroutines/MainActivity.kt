package `in`.co.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var parentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /*
         * Parent coroutine created using GlobalScope (demo purpose only).
         * Storing the Job reference allows:
         * - Manual cancellation
         * - User-initiated cancellation (back press)
         */
        parentJob = GlobalScope.launch(Dispatchers.Main) {

            Log.d("CoroutineDemo", "Parent started")

            /*
             * Child coroutine with cooperative cancellation
             */
            launch(Dispatchers.IO) {

                try {
                    Log.d("CoroutineDemo", "Child started")

                    /*
                     * isActive:
                     * - Becomes false when coroutine is cancelled
                     * - Allows cooperative cancellation in loops / long work
                     */
                    while (isActive) {
                        Log.d("CoroutineDemo", "Child working...")
                        delay(100) // cancellable suspension point
                    }

                } catch (e: CancellationException) {
                    /*
                     * CancellationException is thrown when coroutine is cancelled
                     * This is NORMAL behavior, not an error.
                     */
                    Log.d("CoroutineDemo", "Child cancelled: ${e.message}")

                } finally {
                    /*
                     * finally block ALWAYS executes:
                     * - normal completion
                     * - cancellation
                     *
                     * Used for cleanup:
                     * - close resources
                     * - stop timers
                     * - save state
                     */
                    Log.d("CoroutineDemo", "Child cleanup in finally")
                }
            }

            /*
             * Another child inheriting Main dispatcher
             */
            launch {
                try {
                    Log.d("CoroutineDemo", "Child 2 started")
                    delay(500)
                    Log.d("CoroutineDemo", "Child 2 finished normally")
                } finally {
                    Log.d("CoroutineDemo", "Child 2 cleanup")
                }
            }

            delay(300)
            Log.d("CoroutineDemo", "Parent finished")
        }

        /*
         * MANUAL cancellation demo
         */
        GlobalScope.launch {
            delay(400)
            Log.d("CoroutineDemo", "Manually cancelling parent job")
            parentJob?.cancel()
        }
    }

    /*
     * USER-INITIATED CANCELLATION
     */
    override fun onBackPressed() {
        Log.d("CoroutineDemo", "Back pressed → cancelling parent job")
        parentJob?.cancel()
        super.onBackPressed()
    }
}
