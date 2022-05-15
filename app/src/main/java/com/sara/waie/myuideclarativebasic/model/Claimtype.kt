package com.sara.waie.myuideclarativebasic.model

import com.google.gson.annotations.SerializedName


data class Claimtype(

    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: String? = null

)