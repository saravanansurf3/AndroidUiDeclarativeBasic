package com.sara.waie.myuideclarativebasic.vm

import android.app.Application
import androidx.compose.runtime.Composer.Companion.Empty
import androidx.lifecycle.*
import com.sara.waie.myuideclarativebasic.data.AppRepository
import com.sara.waie.myuideclarativebasic.model.ClaimFormResponse
import com.sara.waie.myuideclarativebasic.utils.FileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ClainActivityViewmodel @Inject constructor(application: Application,private val appRepository: AppRepository) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val _uiState = MutableStateFlow<ClaimFormResponse>(ClaimFormResponse())
    val uiState: StateFlow<ClaimFormResponse> = _uiState


    init {
        loadClaimForm()
    }

     fun loadClaimForm(){
         viewModelScope.async {
             appRepository.getFormDataFromAsset(context)?.let {  _uiState.value= it}
         }
    }
}