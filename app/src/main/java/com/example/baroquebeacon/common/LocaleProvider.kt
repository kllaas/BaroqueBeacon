package com.example.baroquebeacon.common

import java.util.Locale
import javax.inject.Inject

class LocaleProvider @Inject constructor() {

    fun getLanguage(): String {
        return when (Locale.getDefault().language) {
            NETHERLANDS_LANGUAGE -> NETHERLANDS_LANGUAGE
            else -> ENGLISH_LANGUAGE
        }
    }

    private companion object {
        const val NETHERLANDS_LANGUAGE = "nl"
        const val ENGLISH_LANGUAGE = "en"
    }
}

