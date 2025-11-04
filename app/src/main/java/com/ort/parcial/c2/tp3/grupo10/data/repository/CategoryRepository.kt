package com.ort.parcial.c2.tp3.grupo10.data.repository

import com.ort.parcial.c2.tp3.grupo10.data.dao.CategoryDao
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }
    
    suspend fun getCategoryByName(name: String): Category? {
        return categoryDao.getCategoryByName(name)
    }
    
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }
    
    suspend fun insertAllCategories(categories: List<Category>) {
        categoryDao.insertAllCategories(categories)
    }
    
    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
    
    suspend fun initializeDatabase(initialCategories: List<Category>) {
        // Verificar si la base de datos está vacía
        val existingCategories = categoryDao.getAllCategories().first()
        if (existingCategories.isEmpty()) {
            categoryDao.insertAllCategories(initialCategories)
        }
    }
}

