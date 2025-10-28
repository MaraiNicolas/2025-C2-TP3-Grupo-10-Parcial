package com.ort.parcial.c2.tp3.grupo10.domain.repository

interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    fun currentUserId(): String?
    fun signOut()
}

