package com.binod.quotessansar.data.remote.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryResponse(

    @field:SerializedName("data")
    val data: List<CategoryDataItem?>? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
):Serializable

data class CategoryDataItem(

    @field:SerializedName("added_date")
    val addedDate: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("quotes")
    val quotes: List<QuotesItem?>? = null
):Serializable

data class QuotesItem(

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
):Serializable
