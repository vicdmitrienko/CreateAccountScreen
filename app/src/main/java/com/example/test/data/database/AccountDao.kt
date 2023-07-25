package com.example.test.data.database

import androidx.room.*
import com.example.test.data.database.entities.AccountData
import kotlinx.coroutines.flow.Flow

//TODO: Сущностей будет десяток-два, могу предложить Dao интерфейсы
// сложить в com.example.test.data.database.daos

@Dao
interface AccountDao {

    @Query("SELECT * FROM accountData")
    fun getAllAsFlow(): Flow<List<AccountData>>

    @Query("SELECT * FROM accountData WHERE id = :id")
    suspend fun getById(id: Int): AccountData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountData: AccountData)

    @Update
    suspend fun update(accountData: AccountData)

    @Delete
    suspend fun delete(accountData: AccountData)
}