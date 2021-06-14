package com.vkochenkov.filmscatalog

import com.vkochenkov.filmscatalog.di.ViewModelFactory
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.viewmodel.CommonViewModel
import com.vkochenkov.filmscatalog.viewmodel.FavouriteFilmsViewModel
import com.vkochenkov.filmscatalog.viewmodel.FilmsViewModel
import com.vkochenkov.filmscatalog.viewmodel.NotificationViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ViewModelFactoryTests {

    @Mock
    lateinit var repository: Repository
    lateinit var viewModelFactory: ViewModelFactory

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModelFactory = ViewModelFactory(repository)
    }

    @Test
    fun positiveViewModelFactoryTest() {
        val filmsViewModel = viewModelFactory.create(FilmsViewModel::class.java)
        val notificationViewModel = viewModelFactory.create(NotificationViewModel::class.java)
        val favouriteFilmsViewModel = viewModelFactory.create(FavouriteFilmsViewModel::class.java)

        assertEquals(FilmsViewModel::class.java, filmsViewModel.javaClass)
        assertEquals(FavouriteFilmsViewModel::class.java, favouriteFilmsViewModel.javaClass)
        assertEquals(NotificationViewModel::class.java, notificationViewModel.javaClass)
    }

    @Test
    fun negativeViewModelFactoryTest() {
        class UnknownViewModel (override val repository: Repository) : CommonViewModel(repository)
        var thrown = false

        try {
            val testViewModel = viewModelFactory.create(UnknownViewModel::class.java)
        } catch (ex: IllegalArgumentException) {
            thrown = true
        }

        assertTrue(thrown)
    }
}