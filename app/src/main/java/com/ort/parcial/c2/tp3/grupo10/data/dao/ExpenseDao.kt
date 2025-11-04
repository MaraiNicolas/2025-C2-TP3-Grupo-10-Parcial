package com.ort.parcial.c2.tp3.grupo10.data.dao

import androidx.room.*
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses_table WHERE category = :categoryName ORDER BY date DESC, time DESC")
    fun getExpensesByCategory(categoryName: String): Flow<List<Expense>>
    
    @Query("SELECT * FROM expenses_table ORDER BY date DESC, time DESC")
    fun getAllExpenses(): Flow<List<Expense>>
    
    @Query("SELECT * FROM expenses_table WHERE id = :id")
    suspend fun getExpenseById(id: String): Expense?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExpenses(expenses: List<Expense>)
    
    @Update
    suspend fun updateExpense(expense: Expense)
    
    @Delete
    suspend fun deleteExpense(expense: Expense)
    
    @Query("DELETE FROM expenses_table")
    suspend fun deleteAllExpenses()
}