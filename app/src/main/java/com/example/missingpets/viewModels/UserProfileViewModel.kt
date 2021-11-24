package com.example.missingpets.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class UserProfileViewModel (
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var id=-1;

    override fun onCleared() {
        super.onCleared()
        Log.d("UserProfileViewModel", "GameViewModel destroyed!")
    }

}