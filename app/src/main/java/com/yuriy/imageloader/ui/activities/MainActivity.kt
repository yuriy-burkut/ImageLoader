package com.yuriy.imageloader.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.yuriy.imageloader.R
import com.yuriy.imageloader.application.AppClass
import com.yuriy.imageloader.ui.adapters.MainTabsPagerAdapter
import com.yuriy.imageloader.viewmodels.ImagesLoaderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy {
        MainTabsPagerAdapter(this)
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            vmFactory
        ).get(ImagesLoaderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppClass.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = viewPagerAdapter
        val tabTitles = resources.getStringArray(R.array.tab_titles)

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}