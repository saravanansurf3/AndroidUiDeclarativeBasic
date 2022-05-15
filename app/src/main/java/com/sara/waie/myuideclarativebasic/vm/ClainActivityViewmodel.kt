package com.sara.waie.myuideclarativebasic.vm

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composer.Companion.Empty
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.sara.waie.myuideclarativebasic.data.AppRepository
import com.sara.waie.myuideclarativebasic.model.Claim
import com.sara.waie.myuideclarativebasic.model.ClaimFormResponse
import com.sara.waie.myuideclarativebasic.utils.FormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ClainActivityViewmodel @Inject constructor(application: Application,private val appRepository: AppRepository) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val _uiState = MutableStateFlow<ClaimFormResponse>(ClaimFormResponse())
    val uiState: StateFlow<ClaimFormResponse> = _uiState


    public val _claimTypeSelected = MutableStateFlow<Claim>(Claim())
    val claimTypeSelected: StateFlow<Claim> = _claimTypeSelected


    var claimFormState=FormState(HashMap())


    init {
        loadClaimForm()
    }

     fun loadClaimForm(){
         viewModelScope.async {
             appRepository.getFormDataFromAsset(context)?.let {  _uiState.value= it}
         }
    }

    fun submitForm() :Boolean{
//        Log.d("ClaimActivity","isValid:"+claimFormState.validate())
//        Log.d("Data to submit",claimFormState.getData().toString())
       return (claimFormState.validate())
    }
}