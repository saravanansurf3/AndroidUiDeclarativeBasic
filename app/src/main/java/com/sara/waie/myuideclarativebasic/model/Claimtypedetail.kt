package com.sara.waie.myuideclarativebasic.model

import com.google.gson.annotations.SerializedName


data class Claimtypedetail(

    @SerializedName("claimfield_id") var claimfieldId: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("claimtype_id") var claimtypeId: String? = null,
    @SerializedName("Claimfield") var claimfield: Claimfield? = Claimfield()

)