package com.vkochenkov.filmscatalog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vkochenkov.filmscatalog.presentation.activity.MainActivity
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

//todo написать еще тестов

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

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
    fun test() {

        Thread.sleep(3000)
        //when
        onView(withId(R.id.item_film_like_btn)).perform(click())
        onView(withId(R.id.menu_favourites_item)).perform(click())

        //then
        onView(Matchers.allOf(withId(R.id.item_favourite_film_delete_btn)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}