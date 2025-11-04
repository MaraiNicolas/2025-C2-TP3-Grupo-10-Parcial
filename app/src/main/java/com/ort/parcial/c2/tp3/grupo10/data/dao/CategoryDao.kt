package com.ort.parcial.c2.tp3.grupo10.data.dao

import androidx.room.*
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories_table ORDER BY name ASC")
    fun getAllCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories_table WHERE name = :name")
    suspend fun getCategoryByName(name: String): Category?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<Category>)
    
    @Delete
    suspend fun deleteCategory(category: Category)
    
    @Query("DELETE FROM categories_table")
    suspend fun deleteAllCategories()
}

