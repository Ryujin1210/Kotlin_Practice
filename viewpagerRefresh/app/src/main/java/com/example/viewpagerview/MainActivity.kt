package com.example.viewpagerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textList = listOf("뷰A","뷰B","뷰C","뷰D") 
        val adapter = CustomPagerAdapter()

        adapter.textList = textList
        viewPager.adapter = adapter
        val tabTitles = listOf("view A","view B","view C","view D")
        TabLayoutMediator(tabLayout, viewPager) {tab,position ->
            tab.text = tabTitles[position]
        }.attach()

    }
}