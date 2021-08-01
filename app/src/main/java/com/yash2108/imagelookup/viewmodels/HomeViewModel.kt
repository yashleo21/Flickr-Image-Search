package com.yash2108.openissuesreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.models.ResultUI
import com.yash2108.openissuesreader.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    var query = "food"
    var page = 1L
    var pageSize = 5L

    @Inject
    lateinit var repository: HomeRepository

    private val homeDataMutableLiveData = MutableLiveData<ResultUI<List<FlickrDataObject>>>()
    val homeDataObjectDataLiveData: LiveData<ResultUI<List<FlickrDataObject>>> get() = homeDataMutableLiveData

    fun getPhotosList(query: String?, page: Long?, pageSize: Long?) = viewModelScope.launch(Dispatchers.IO) {
        repository.getPhotos(query, page, pageSize)
            .collect { issue ->
                homeDataMutableLiveData.postValue(issue)
            }
    }
}