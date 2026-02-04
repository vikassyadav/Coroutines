package `in`.co.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /*
         * GlobalScope.launch creates a NEW coroutine with its own Job.
         * This Job becomes the PARENT for all coroutines launched inside this block.
         *
         * Dispatcher.Main -> runs on Android main (UI) thread.
         *
         * ⚠️ GlobalScope is used here ONLY for learning/demo.
         * In real apps, prefer lifecycleScope or viewModelScope.
         */
        GlobalScope.launch(Dispatchers.Main) {  // Parent coroutine (Main thread)

            Log.d(
                "CoroutineDemo",
                "Parent (GlobalScope) -> Thread: ${Thread.currentThread().name}, " +
                        "CoroutineContext: $coroutineContext"
            )

            /*
             * launch(Dispatchers.IO)
             *
             * - Creates a CHILD coroutine
             * - Inherits the PARENT Job automatically
             * - Changes only the Dispatcher to IO
             *
             * Result:
             * Parent Job
             *    └── Child Job (IO thread)
             */
            launch(Dispatchers.IO) {  // Child coroutine on IO thread

                Log.d(
                    "CoroutineDemo",
                    "Child 1 -> Thread: ${Thread.currentThread().name}, " +
                            "CoroutineContext: $coroutineContext"
                )

                delay(200) // suspends this coroutine, does NOT block the thread
                Log.d("CoroutineDemo", "Child 1 finished")
            }

            /*
             * launch() without dispatcher:
             *
             * - Inherits BOTH Job and Dispatcher from parent
             * - Runs on the SAME thread as parent (Main thread)
             */
            launch {  // Child coroutine on Main thread

                Log.d(
                    "CoroutineDemo",
                    "Child 2 -> Thread: ${Thread.currentThread().name}, " +
                            "CoroutineContext: $coroutineContext"
                )

                delay(200)
                Log.d("CoroutineDemo", "Child 2 finished")
            }

            /*
             * delay here suspends ONLY the parent coroutine.
             * Children continue running independently but are still tied to parent Job.
             */
            delay(100)
            Log.d("CoroutineDemo", "Parent finished")
        }
    }
}
