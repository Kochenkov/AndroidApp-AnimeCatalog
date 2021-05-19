package com.vkochenkov.filmscatalog

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vkochenkov.filmscatalog.presentation.activity.MainActivity

import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//todo написать еще тестов

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun test() {
        //when
        Espresso.onView(withId(R.id.menu_info_item)).perform(ViewActions.click())
        Thread.sleep(5000)

        //then
        Espresso.onView(Matchers.allOf(withId(R.id.btn_share)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}