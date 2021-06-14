package com.vkochenkov.filmscatalog

import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.viewmodel.FavouriteFilmsViewModel
import com.vkochenkov.filmscatalog.viewmodel.FilmsViewModel
import com.vkochenkov.filmscatalog.viewmodel.NotificationViewModel
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ViewModelIntegrationTests {

    @Mock
    lateinit var repository: Repository

    var filmName: String = "test"

    lateinit var filmsViewModel: FilmsViewModel
    lateinit var favouriteFilmsViewModel: FavouriteFilmsViewModel
    lateinit var notificationViewModel: NotificationViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        filmsViewModel = FilmsViewModel(repository)
        favouriteFilmsViewModel = FavouriteFilmsViewModel(repository)
        notificationViewModel = NotificationViewModel(repository)
    }

    @Test
    fun filmsViewModelShouldUseRepository() {
        var isLikeFilm: Boolean = false
        var isUnlikeFilm: Boolean = false
        var isGetFilmsWithoutPaging: Boolean = false

        Mockito.`when`(repository.likeFilm(Mockito.anyString())).then {
            isLikeFilm = true
            Any()
        }
        Mockito.`when`(repository.unlikeFilm(Mockito.anyString())).then {
            isUnlikeFilm = true
            Any()
        }
        Mockito.`when`(
            repository.getFilmsFromApi(
                Mockito.anyInt(),
                any(Repository.GetFilmsFromApiCallback::class.java)
            )
        ).then {
            isGetFilmsWithoutPaging = true
            Any()
        }

        filmsViewModel.likeFilm(filmName)
        filmsViewModel.unlikeFilm(filmName)
        filmsViewModel.getFilmsWithoutPaging()

        assertTrue(isLikeFilm)
        assertTrue(isUnlikeFilm)
        assertTrue(isGetFilmsWithoutPaging)
    }

    @Test
    fun favouriteFilmsViewModelShouldUseRepository() {
        var isLikeFilm: Boolean = false
        var isUnlikeFilm: Boolean = false
        var isGetFavourites: Boolean = false

        Mockito.`when`(repository.likeFilm(Mockito.anyString())).then {
            isLikeFilm = true
            Any()
        }
        Mockito.`when`(repository.unlikeFilm(Mockito.anyString())).then {
            isUnlikeFilm = true
            Any()
        }
        Mockito.`when`(
            repository.getFavourites(any(Repository.GetFilmsFromDatabaseCallback::class.java))
        ).then {
            isGetFavourites = true
            Any()
        }

        favouriteFilmsViewModel.likeFilm(filmName)
        favouriteFilmsViewModel.unlikeFilm(filmName)
        favouriteFilmsViewModel.getFavourites()

        assertTrue(isLikeFilm)
        assertTrue(isUnlikeFilm)
        assertTrue(isGetFavourites)
    }

    @Test
    fun notificationViewModelShouldUseRepository() {
        var isLikeFilm: Boolean = false
        var isUnlikeFilm: Boolean = false
        var isNotifyFilm: Boolean = false
        var isSetNotification: Boolean = false

        Mockito.`when`(repository.likeFilm(Mockito.anyString())).then {
            isLikeFilm = true
            Any()
        }
        Mockito.`when`(repository.unlikeFilm(Mockito.anyString())).then {
            isUnlikeFilm = true
            Any()
        }
        Mockito.`when`(
            repository.getFilm(
                Mockito.anyString(),
                any(Repository.GetFilmFromDatabaseCallback::class.java)
            )
        ).then {
            isNotifyFilm = true
            Any()
        }
        Mockito.`when`(
            repository.setNotificationFilm(Mockito.anyString(), Mockito.anyLong())
        ).then {
            isSetNotification = true
            Any()
        }

        notificationViewModel.likeFilm(filmName)
        notificationViewModel.unlikeFilm(filmName)
        notificationViewModel.isNotifyFilm(filmName)
        notificationViewModel.setNotificationFilm(Mockito.anyString(), Mockito.anyLong())

        assertTrue(isLikeFilm)
        assertTrue(isUnlikeFilm)
        assertTrue(isNotifyFilm)
        assertTrue(isSetNotification)
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}