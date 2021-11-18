package com.example.missingpets.dataRV

import android.util.Log
import com.example.missingpets.network.ApiClient
import com.example.missingpets.network.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDatasource {

    fun getUserId(username: String, password: String): Int {
        var result: Int;
        result = -1
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            try {
                result = ApiClient.backendService.getUserId(username, password)
            } catch (e: Exception) {
                Log.d("ERROR", "Error Occurred: ${e.message}")
            }
        }
        return result
    }
    fun getUserById(id: Int): User {
        var user: User
        user = User();
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            try {
                user = ApiClient.backendService.getUserById(id)
            } catch (e: Exception) {
                Log.d("ERROR", "Error Occurred: ${e.message}")
            }
        }
        return user
    }
}