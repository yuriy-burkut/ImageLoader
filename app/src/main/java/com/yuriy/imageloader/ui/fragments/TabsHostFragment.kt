package com.yuriy.imageloader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.yuriy.imageloader.R
import com.yuriy.imageloader.ui.adapters.MainTabsPagerAdapter
import kotlinx.android.synthetic.main.tabs_host_fragment.*

class TabsHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return if (view != null) {
            view
        } else inflater.inflate(R.layout.tabs_host_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            FindImagesFragment(),
            FavoritesImagesFragment()
        )

        val viewPagerAdapter = MainTabsPagerAdapter(requireActivity(), fragments)
        view_pager.adapter = viewPagerAdapter

        val tabTitles = resources.getStringArray(R.array.tab_titles)
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}