package com.example.test.data.database.daos

import androidx.room.*
import com.example.test.data.database.entities.Account
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAllAsFlow(): Flow<List<Account>>

    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun getById(id: Int): Account?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountData: Account)

    @Update
    suspend fun update(accountData: Account)

    @Delete
    suspend fun delete(accountData: Account)
}