package com.binod.quotessansar.data.remote.models

import com.google.gson.annotations.SerializedName

data class QuotesResponse(

    @field:SerializedName("data")
    val data: List<QuotesDataItem?>? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class QuotesDataItem(

    @field:SerializedName("quote")
    val quote: String? = null,

    @field:SerializedName("added_date")
    val addedDate: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: String? = null
)
