package com.vkochenkov.filmscatalog.di.component

import com.vkochenkov.filmscatalog.di.module.*
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.receivers.FilmNotificationReceiver
import com.vkochenkov.filmscatalog.view.MainActivity
import com.vkochenkov.filmscatalog.view.fragments.FavouriteFilmsListFragment
import com.vkochenkov.filmscatalog.view.fragments.FilmInfoFragment
import com.vkochenkov.filmscatalog.view.fragments.FilmsListFragment

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class, AppModule::class, DatabaseModule::class, RepositoryModule::class))
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(repository: Repository)
    fun inject(fragment: FilmsListFragment)
    fun inject(fragment: FavouriteFilmsListFragment)
    fun inject(fragment: FilmInfoFragment)
    fun inject(broadcastReceiver: FilmNotificationReceiver)
}