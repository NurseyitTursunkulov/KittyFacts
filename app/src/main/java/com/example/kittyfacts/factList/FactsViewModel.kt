package com.example.kittyfacts.factList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FactItemModel
import com.example.data.Result
import com.example.domain.GetFactsUseCase
import com.example.kittyfacts.R
import com.example.kittyfacts.util.Event
import kotlinx.coroutines.launch

class FactsViewModel(val getFactsUseCase: GetFactsUseCase) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _items = MutableLiveData<List<FactItemModel>>().apply { value = emptyList() }
    val items: LiveData<List<FactItemModel>> = _items

    fun loadTasks() {
        _dataLoading.value = true
        viewModelScope.launch {
            val factsResult = getFactsUseCase()
            if (factsResult is Result.Success) {
                _isDataLoadingError.value = false
                _items.value = factsResult.data
            } else {
                _isDataLoadingError.value = true
                _items.value = emptyList()
                showSnackbarMessage(R.string.loading_facts_error)
            }

            _dataLoading.value = false
        }

    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}
