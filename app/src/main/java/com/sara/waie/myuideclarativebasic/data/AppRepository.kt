package com.sara.waie.myuideclarativebasic.data

import android.content.Context
import com.google.gson.Gson
import com.sara.waie.myuideclarativebasic.model.ClaimFormResponse
import java.io.IOException
import javax.inject.Inject

class AppRepository @Inject constructor() {


    suspend fun getFormDataFromAsset(context: Context ): ClaimFormResponse? {
        var response: ClaimFormResponse? = null
        try {
            val jsonString: String =
                context.assets.open("req_claim_data_response.json").bufferedReader().use { it.readText() }
            response = Gson().fromJson(jsonString, ClaimFormResponse::class.java)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return response
    }
}