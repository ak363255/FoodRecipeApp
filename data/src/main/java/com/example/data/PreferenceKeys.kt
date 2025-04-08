package com.example.data

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferenceKeys {
    val onboarding_completed = booleanPreferencesKey("onboarding_completed")
}