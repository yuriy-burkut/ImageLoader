package com.yuriy.imageloader.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yuriy.imageloader.R
import com.yuriy.imageloader.application.AppClass
import com.yuriy.imageloader.livadata.ViewAction
import com.yuriy.imageloader.ui.fragments.FullscreenImageFragment
import com.yuriy.imageloader.ui.fragments.TabsHostFragment
import com.yuriy.imageloader.viewmodel.ImagesLoaderViewModel
import javax.inject.Inject

const val IMAGE_URL_KEY = "IMAGE_URL_KEY"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            vmFactory
        ).get(ImagesLoaderViewModel::class.java)
    }

    private var tabsFragment: TabsHostFragment = TabsHostFragment()
    private val fullscreenImageFragment: FullscreenImageFragment = FullscreenImageFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        AppClass.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showTabsFragment()
        }

        observeLiveData()
    }

    private fun showTabsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, tabsFragment)
            .commit()
    }

    private fun showFullscreenFragment(imageUrl: String) {

        val bundle = Bundle().apply {
            putString(IMAGE_URL_KEY, imageUrl)
        }

        fullscreenImageFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, fullscreenImageFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeLiveData() {
        viewModel.viewAction.observe(this, Observer {
            if (it is ViewAction.ShowFullScreen) {
                showFullscreenFragment(it.imageUrl)
            }
        })
    }
}