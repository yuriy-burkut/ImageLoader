package com.yuriy.imageloader.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuriy.imageloader.ui.fragments.FavoritesImagesFragment
import com.yuriy.imageloader.ui.fragments.FindImagesFragment

class MainTabsPagerAdapter(
    fragmentActivity: FragmentActivity, private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
/*        return when (position) {
            0 -> FindImagesFragment()
            1 -> FavoritesImagesFragment()
            else -> Fragment()
        }*/
    }
}