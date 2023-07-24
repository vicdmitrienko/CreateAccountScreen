package com.example.test.di

import androidx.room.Room
import com.example.test.data.data_source.AccountDatabase
import com.example.test.data.repository.AccountRepositoryImpl
import com.example.test.domain.repository.AccountRepository
import com.example.test.domain.usecase.AccountUseCases
import com.example.test.domain.usecase.AddAccount
import com.example.test.domain.usecase.GetAccounts
import com.example.test.ui.account.edit.EditAccountViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        EditAccountViewModel(get())
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AccountDatabase::class.java,
            AccountDatabase.DATABASE_NAME
        ).build()
    }

    single {
        get<AccountDatabase>().accountDao
    }

    single<AccountRepository> {
        AccountRepositoryImpl(get())
    }

    single {
        AccountUseCases(
            addAccount = AddAccount(get()),
            getAccounts = GetAccounts(get())
        )
    }
}