package com.example.pockedex

import com.example.pockedex.ui.details.DetailsViewModel
import com.example.pockedex.ui.home.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object Module{

    fun load(){
        loadKoinModules(viewModelModule())
    }
    private fun viewModelModule():Module
    {
        return module {
            factory { HomeViewModel(get()) }
            factory { DetailsViewModel(get()) }
        }
    }
}
