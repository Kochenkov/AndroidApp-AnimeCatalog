package com.vkochenkov.filmscatalog

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher


class CustomItemAction(val buttonId: Int) : ViewAction {
    override fun getDescription(): String {
        return CustomItemAction::class.java.name
    }

    override fun getConstraints(): Matcher<View>? {
        return null
    }

    override fun perform(uiController: UiController?, view: View?) {
        val button: View = view!!.findViewById(buttonId)
        button.performClick()
    }
}