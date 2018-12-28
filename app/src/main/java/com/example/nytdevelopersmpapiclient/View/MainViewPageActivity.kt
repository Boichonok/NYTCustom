package com.example.nytdevelopersmpapiclient.View

import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.Fragments.*
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.activity_main_view_page.*
import okhttp3.internal.Util
import java.io.InterruptedIOException
import java.net.SocketException
import java.sql.SQLException

class MainViewPageActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_page)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))




        RxJavaPlugins.setErrorHandler({
            if (it is InterruptedException) {
                Utils.ShowToast("Thread interrupted", this)
            } else if (it is InterruptedIOException) {
                Utils.ShowToast("Io interrupted", this)
            } else if (it is SocketException) {
                Utils.ShowToast("Socket error", this)
            } else if (it is SQLException) {
                Utils.ShowToast(it.localizedMessage, this)
            }
        })

    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    return MostEmailedArticleFragment()
                }
                1 -> {
                    return MostSharedArticleFragment()
                }
                2 -> {
                    return MostViewedArticleFragment()
                }
                3 -> {
                    return SearchArticleFragment()
                }
                4 -> {
                    return FavoriteArticlesFragment()
                }
            }
            return null
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 5
        }
    }


}
