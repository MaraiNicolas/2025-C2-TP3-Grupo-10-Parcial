package com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.parcial.c2.tp3.grupo10.data.repository.ExpenseRepository
import com.ort.parcial.c2.tp3.grupo10.data.repository.CategoryRepository
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    fun getExpensesByCategory(categoryName: String): StateFlow<List<Expense>> {
        return expenseRepository.getExpensesByCategory(categoryName)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }
    
    fun getAllCategories(): StateFlow<List<Category>> {
        return categoryRepository.getAllCategories()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }
    
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.insertExpense(expense)
        }
    }
}

