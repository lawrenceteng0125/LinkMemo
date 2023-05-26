package com.example.linkmemo

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkmemo.data.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivityViewModel: ViewModel() {
    private val repo =  WordRepository()

    fun getDefinition(word:String):String {
        var res:String = ""
        viewModelScope.launch {
            runBlocking {
                try {
                    res =  repo.searchWord(word)
                } catch (e: Exception) {

                }
            }
        }
        return res
    }
}