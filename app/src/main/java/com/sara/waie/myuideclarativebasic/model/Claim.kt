package com.sara.waie.myuideclarativebasic.model

import com.google.gson.annotations.SerializedName


data class Claim(

    @SerializedName("Claimtype") var Claimtype: Claimtype? = Claimtype(),
    @SerializedName("Claimtypedetail") var Claimtypedetails: ArrayList<Claimtypedetail> = arrayListOf()

)