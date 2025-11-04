package com.ort.parcial.c2.tp3.grupo10.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category
import com.ort.parcial.c2.tp3.grupo10.data.dao.ExpenseDao
import com.ort.parcial.c2.tp3.grupo10.data.dao.CategoryDao

@Database(
    entities = [Expense::class, Category::class],
    version = 2,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
    abstract val categoryDao: CategoryDao
}