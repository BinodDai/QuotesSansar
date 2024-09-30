package com.binod.quotessansar.data.local.quotes.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val quoteId: Int,
    val quotes: String,
    val author: String
)
