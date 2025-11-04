package com.ort.parcial.c2.tp3.grupo10.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey
    val name: String,  // "Food", "Transport", etc.
    val iconResId: Int
)

