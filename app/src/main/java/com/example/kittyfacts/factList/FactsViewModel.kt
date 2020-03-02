package com.example.kittyfacts.factList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FactItemModel
import com.example.data.Result
import com.example.domain.GetFactsUseCase
import com.example.domain.GetFactsUseCaseImpl
import com.example.kittyfacts.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FactsViewModel(val getFactsUseCase: GetFactsUseCaseImpl) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _items = MutableLiveData<List<FactItemModel>>().apply { value = emptyList() }
    val items: LiveData<List<FactItemModel>> = _items

    private val _openDetailsEvent = MutableLiveData<Event<FactItemModel>>()
    val openDetailsEvent: LiveData<Event<FactItemModel>> = _openDetailsEvent

    init {
        loadFacts()
    }
    fun loadFacts() {
        _dataLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val factsResult = getFactsUseCase()
                if (factsResult is Result.Success) {
                    _isDataLoadingError.postValue(false)
                    _items.postValue(factsResult.data)
                } else {
                    _isDataLoadingError.postValue( true)
                    _items.postValue(emptyList())
                    showSnackbarMessage(factsResult.toString())
                }

                _dataLoading.postValue(false)
            }
        }

    }

    fun openDetails(factItemModel: FactItemModel){
        _openDetailsEvent.value = Event(factItemModel)
    }
    private fun showSnackbarMessage(message: String) {
        _snackbarText.postValue(Event(message))
    }
}
