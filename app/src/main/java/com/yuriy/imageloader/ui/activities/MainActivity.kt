package com.yuriy.imageloader.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.yuriy.imageloader.R
import com.yuriy.imageloader.application.AppClass
import com.yuriy.imageloader.ui.adapters.MainTabsPagerAdapter
import com.yuriy.imageloader.ui.fragments.FavoritesImagesFragment
import com.yuriy.imageloader.ui.fragments.FindImagesFragment
import com.yuriy.imageloader.viewmodel.ImagesLoaderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy {
        val fragments = listOf(
            FindImagesFragment(),
            FavoritesImagesFragment()
        )

        MainTabsPagerAdapter(this, fragments)
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    val viewModel by lazy {
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