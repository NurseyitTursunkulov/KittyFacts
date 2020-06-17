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

class FactsViewModel(val getFactsUseCase: GetFactsUseCase) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _items =
        MutableLiveData<MutableList<FactItemModel>>().apply { value = mutableListOf() }
    val items: LiveData<MutableList<FactItemModel>> = _items

    private val _totalItemsSize = MutableLiveData<Int>()
    val totalItemsSize: LiveData<Int> = _totalItemsSize

    private val _openDetailsEvent = MutableLiveData<Event<FactItemModel>>()
    val openDetailsEvent: LiveData<Event<FactItemModel>> = _openDetailsEvent

    private val STARTING_PAGE = 1

    init {
        loadFacts()
    }

    fun loadFacts() {
        _dataLoading.value = true
        viewModelScope.launch {
            val factsResult = getFactsUseCase()
            if (factsResult is Result.Success) {
                _isDataLoadingError.postValue(false)
                _items.postListValue(factsResult.data)
                loadFactsItemsSize()
            } else {
                _isDataLoadingError.postValue(true)
                _items.postListValue(mutableListOf())
                showSnackbarMessage(factsResult.toString())
            }

            _dataLoading.postValue(false)

        }

    }

    fun openDetails(factItemModel: FactItemModel) {
        _openDetailsEvent.value = Event(factItemModel)
    }

    private fun showSnackbarMessage(message: String) {
        _snackbarText.postValue(Event(message))
    }

    fun refreshData() {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var result = getFactsUseCase.refreshFactsRepository()
                if (result is Result.Success) {
                    _isDataLoadingError.postValue(false)
                    getFactsUseCase.page = STARTING_PAGE
                    _items.postValue(mutableListOf())
                    withContext(Dispatchers.Main) {
                        loadFacts()
                    }
                } else {
                    _isDataLoadingError.postValue(true)
                    _items.postValue(mutableListOf())
                    showSnackbarMessage(result.toString())
                }

                _dataLoading.postValue(false)
            }
        }

    }

    fun loadFactsItemsSize() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _totalItemsSize.postValue(getFactsUseCase.getFactsItemsSize())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getFactsUseCase.page = STARTING_PAGE
    }
}
