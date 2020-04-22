package com.neo.pod.repository.provider.preferences

import android.content.SharedPreferences

interface AppSharedPreferences {
    fun sharedPreferences(): SharedPreferences
}