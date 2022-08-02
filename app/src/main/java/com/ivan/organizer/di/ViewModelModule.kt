package com.ivan.organizer.di

import com.ivan.organizer.viewmodel.CardsScreenViewModel
import com.ivan.organizer.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(repository = get())
    }

    viewModel {
        CardsScreenViewModel()
    }
}