package com.ort.parcial.c2.tp3.grupo10.data.repository

import com.ort.parcial.c2.tp3.grupo10.data.dao.ExpenseDao
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getExpensesByCategory(categoryName: String): Flow<List<Expense>> {
        return expenseDao.getExpensesByCategory(categoryName)
    }
    
    fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }
    
    suspend fun getExpenseById(id: String): Expense? {
        return expenseDao.getExpenseById(id)
    }
    
    suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }
    
    suspend fun insertAllExpenses(expenses: List<Expense>) {
        expenseDao.insertAllExpenses(expenses)
    }
    
    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense)
    }
    
    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
    
    suspend fun initializeDatabase(initialExpenses: List<Expense>) {
        // Verificar si la base de datos está vacía
        val existingExpenses = expenseDao.getAllExpenses().first()
        if (existingExpenses.isEmpty()) {
            expenseDao.insertAllExpenses(initialExpenses)
        }
    }
}