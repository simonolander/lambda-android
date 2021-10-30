package org.simonolander.lambda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TutorialViewModel : ViewModel() {
    private val pageIndex: MutableLiveData<Int> = MutableLiveData(0)

    fun nextPage() {
        val currentPageIndex = pageIndex.value ?: 0
        pageIndex.postValue(currentPageIndex + 1)
    }

    fun getPage(): LiveData<Int> {
        return pageIndex
    }
}
