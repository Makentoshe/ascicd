package com.example.company.lib

import android.app.Activity
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.AfterClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class Checker {

    companion object {
        private var results: String = ""

        @AfterClass
        @JvmStatic
        fun tearDown() {
            Log.d("CHECKER", results)
        }
    }

    open fun getActivity(): Activity? {
        return null
    }

    private fun getId(name: String): Int {
        val activity: Activity = getActivity() ?: return -1
        return activity.resources.getIdentifier(name, "id", activity.packageName)
    }

}