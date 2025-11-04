package com.ort.parcial.c2.tp3.grupo10.di

import android.content.Context
import androidx.room.Room
import com.ort.parcial.c2.tp3.grupo10.data.dao.ExpenseDao
import com.ort.parcial.c2.tp3.grupo10.data.dao.CategoryDao
import com.ort.parcial.c2.tp3.grupo10.data.initial.InitialExpensesData
import com.ort.parcial.c2.tp3.grupo10.data.network.ExpenseDatabase
import com.ort.parcial.c2.tp3.grupo10.data.repository.ExpenseRepository
import com.ort.parcial.c2.tp3.grupo10.data.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideExpenseDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expenses_database"
        )
        .fallbackToDestructiveMigration() // Para desarrollo
        .build()
    }
    
    @Provides
    fun provideExpenseDao(
        database: ExpenseDatabase
    ): ExpenseDao {
        return database.expenseDao
    }
    
    @Provides
    fun provideCategoryDao(
        database: ExpenseDatabase
    ): CategoryDao {
        return database.categoryDao
    }
    
    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao
    ): ExpenseRepository {
        val repository = ExpenseRepository(expenseDao)
        
        // Inicializar base de datos con datos iniciales
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        applicationScope.launch {
            val initialExpenses = InitialExpensesData.getInitialExpenses()
            repository.initializeDatabase(initialExpenses)
        }
        
        return repository
    }
    
    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository {
        val repository = CategoryRepository(categoryDao)
        
        // Inicializar base de datos con categorías iniciales
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        applicationScope.launch {
            val initialCategories = InitialExpensesData.getInitialCategories()
            repository.initializeDatabase(initialCategories)
        }
        
        return repository
    }
}