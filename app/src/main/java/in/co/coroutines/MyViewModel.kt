package `in`.co.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {

    fun loadUserData() {
        // Launch coroutine tied to ViewModel
        viewModelScope.launch {

            // Runs on Main thread by default
            println("Starting network call on thread: ${Thread.currentThread().name}")

            // Switch to IO for network/database operation
            val userData = withContext(Dispatchers.IO) {
                simulateNetworkCall()
            }

            // Back to Main thread to update UI
            println("Received user data: $userData on thread: ${Thread.currentThread().name}")
        }
    }

    // Simulate network call
    private suspend fun simulateNetworkCall(): String {
        delay(1000) // simulate network delay
        return "John Doe"
    }
}
