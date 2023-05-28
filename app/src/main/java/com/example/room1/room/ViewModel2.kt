package com.example.room1.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelActivity2(private val dao: Dao): ViewModel() {

    private val _dataList = MutableStateFlow(listOf<Data?>())
    val dataList: StateFlow<List<Data?>> get() = _dataList

    private val _usernameAsKey: MutableStateFlow<String?> = MutableStateFlow("")
    val usernameAsKey: StateFlow<String?> get() = _usernameAsKey

    fun insertData(data: Data) {
        viewModelScope.launch {
            dao.insertData(data)
        }
    }

    fun getDataList(key: String?) {
        viewModelScope.launch {
            val getList = dao.getListByKey(key)
            _dataList.update { getList }
        }
    }

    fun memorizeKey(key: String?) {
        viewModelScope.launch {
            _usernameAsKey.update { key }
        }
    }
}