package com.example.flickr.ui.models

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

//https://www.droidcon.com/2022/04/12/jetpack-compose-navigation-with-custom-navtype/
class PhotoLocalNavType : NavType<PhotoLocal>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PhotoLocal? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, PhotoLocal::class.java)
        } else {
            bundle.getParcelable(key)
        }

    }

    override fun parseValue(value: String): PhotoLocal {
        /*val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(PhotoLocal::class.java)
        return jsonAdapter.fromJson(value)!!*/

        return Gson().fromJson(value, PhotoLocal::class.java)

    }

    override fun put(bundle: Bundle, key: String, value: PhotoLocal) {
        bundle.putParcelable(key, value)
    }
}