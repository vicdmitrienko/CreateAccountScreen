package com.example.test.di

import androidx.room.Room
import com.example.test.data.database.AccountDatabase
import com.example.test.ui.account.edit.EditAccountViewModel
import com.example.test.ui.account.list.AccountsListViewModel
import com.example.test.ui.account.menu.MenuViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        EditAccountViewModel()
    }

    viewModel {
        AccountsListViewModel(get())
    }

    viewModel {
        MenuViewModel(get())
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
}