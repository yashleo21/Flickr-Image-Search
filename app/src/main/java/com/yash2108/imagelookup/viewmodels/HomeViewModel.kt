package com.yash2108.openissuesreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.models.ResultUI
import com.yash2108.openissuesreader.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var query = ""
    var page = 1L
    var pageSize = 5L
    var isPaginationCallInProgress = false

    val adapterList = ArrayList<FlickrDataObject>()

    var currentItem: FlickrDataObject? = null
    var transitionName = ""

    @Inject
    lateinit var repository: HomeRepository

    var homeDataMutableLiveData = MutableLiveData<ResultUI<List<FlickrDataObject>>>()
    val homeDataObjectDataLiveData: LiveData<ResultUI<List<FlickrDataObject>>> get() = homeDataMutableLiveData

    var favoriteDataMutableLiveData = MutableLiveData<ResultUI<List<FlickrDataObject>>>()
    val favoriteDataLiveData: LiveData<ResultUI<List<FlickrDataObject>>> get() = favoriteDataMutableLiveData

    fun getPhotosList(query: String?, page: Long?, pageSize: Long?) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPhotos(query, page, pageSize)
                .collect { photos ->
                    homeDataMutableLiveData.postValue(photos)
                }
        }

    fun getFavorites() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFavorites()
            .collect { photos ->
                favoriteDataMutableLiveData.postValue(photos)
            }
    }

    fun setFavoriteState(data: FlickrDataObject) = viewModelScope.launch(Dispatchers.IO) {
        repository.setFavoriteState(data)
    }
}