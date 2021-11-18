package com.prinkal.searchableapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prinkal.searchableapp.data.model.SampleData
import com.prinkal.searchableapp.utils.DummyDataGenerator
import com.prinkal.searchableapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val users = MutableLiveData<Resource<List<SampleData>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        users.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userList = DummyDataGenerator.getSampleData()
                users.postValue(Resource.success(userList))
            } catch (e: Exception) {
                e.printStackTrace()
                users.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<SampleData>>> {
        return users
    }

}