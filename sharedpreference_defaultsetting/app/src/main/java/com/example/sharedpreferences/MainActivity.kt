package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shared = PreferenceManager.getDefaultSharedPreferences(this)

        val checkboxvalue = shared.getBoolean("key_add_shortcut",false)
        val switchvalue = shared.getBoolean("key_switch_on",false)
        val name = shared.getString("key_edit_name","")
        val selected = shared.getString("key_set_item","")

        Log.d("설정값", "add_shortcut=${checkboxvalue}")
        Log.d("설정값", "switchValue=${switchvalue}")
        Log.d("설정값", "name=${name}")
        Log.d("설정값", "selected=${selected}")
    }
}