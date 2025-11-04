package com.ort.parcial.c2.tp3.grupo10.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense(
    @PrimaryKey
    val id: String,
    val title: String,
    val amount: Double,
    val date: String,  // Formato: "2024-04-30"
    val time: String,  // Formato: "18:27"
    val category: String,  // "Food", "Transport", etc.
    val iconResId: Int  // No-nullable, sin valor por defecto - siempre debe ser asignado
)