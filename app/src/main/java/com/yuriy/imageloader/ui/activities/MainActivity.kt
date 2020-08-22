package com.yuriy.imageloader.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yuriy.imageloader.R
import com.yuriy.imageloader.ui.adapters.MainTabsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy {
        MainTabsPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = viewPagerAdapter
        val tabTitles = resources.getStringArray(R.array.tab_titles)

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}