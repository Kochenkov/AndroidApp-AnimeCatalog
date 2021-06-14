package com.vkochenkov.filmscatalog


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vkochenkov.filmscatalog.presentation.activity.MainActivity
import com.vkochenkov.filmscatalog.presentation.viewholders.FilmViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class e2eEspressoTests {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun start() {
        //дропаем базу перед тестами (с помощью оркестратора)
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .executeShellCommand("pm clear com.vkochenkov.filmscatalog")
            .close()
    }

    @Test
    fun getDateFromApiAndCheckAddingFilmToFavourites() {
        //when
        waitForCompleteApiCall()
        addFilmToFavourites()
        goToFavouritesScreen()
        //then
        checkFilmDisplayedOnFavouritesScreen()
    }

    private fun waitForCompleteApiCall() {
        var count = 5
        while (count>0) {
            try {
                onView(withId(R.id.films_list)).check(matches(isDisplayed()))
                count = 0
            } catch (ex: NoMatchingViewException) {
                Thread.sleep(500)
                count--
            }
        }
    }

    private fun addFilmToFavourites() {
        var count = 5
        while (count>0) {
            try {
                onView(withId(R.id.films_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(0, CustomItemAction(R.id.item_film_like_btn)))
                count = 0
            } catch (ex: PerformException) {
                Thread.sleep(500)
                count--
            }
        }
    }

    private fun goToFavouritesScreen() {
        onView(withId(R.id.menu_favourites_item)).perform(click())
    }

    private fun checkFilmDisplayedOnFavouritesScreen() {
        onView(withId(R.id.item_favourite_film_delete_btn)).check(matches(isDisplayed()))
    }
}
