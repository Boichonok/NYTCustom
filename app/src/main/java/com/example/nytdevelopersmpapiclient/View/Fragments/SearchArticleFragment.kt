package com.example.nytdevelopersmpapiclient.View.Fragments

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.os.Build

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent


import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.ViewUtils.ArticlesRecyclerViewAdapter

import com.example.nytdevelopersmpapiclient.ViewModel.SearchArticleViewModel
import com.mancj.slideup.SlideUp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.search_article_fragment.*
import kotlinx.android.synthetic.main.categories.*
import kotlinx.android.synthetic.main.categories.view.*
import kotlinx.android.synthetic.main.search_article_fragment.view.*


class SearchArticleFragment : BaseFragment() {


    override var layoutId: Int
        get() = R.layout.search_article_fragment
        set(value) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super<BaseFragment>.onCreateView(inflater, container, savedInstanceState)

        initSlideUp()
        initOnClickButtons()

        return _view
    }



    @SuppressLint("ResourceType")
    private fun initOnClickButtons() {


        _view.most_emailed.setOnClickListener { requestBuilder.setSocial(SearchArticleViewModel.Social.MOST_EMAILED) }
        _view.most_viewed.setOnClickListener { requestBuilder.setSocial(SearchArticleViewModel.Social.MOST_VIEWED) }
        _view.most_shared.setOnClickListener { requestBuilder.setSocial(SearchArticleViewModel.Social.MOST_SHARED) }

        _view.one_month.setOnClickListener { requestBuilder.setTimePeriod(30) }
        _view.one_day.setOnClickListener { requestBuilder.setTimePeriod(1) }
        _view.seven_day.setOnClickListener { requestBuilder.setTimePeriod(7) }

        _view.all_sections.setOnClickListener { requestBuilder.setSection(getString(R.string.all_sections)) }
        _view.arts.setOnClickListener { requestBuilder.setSection(getString(R.string.arts)) }
        _view.automobiles.setOnClickListener { requestBuilder.setSection(getString(R.string.automobiles)) }
        _view.blogs.setOnClickListener { requestBuilder.setSection(getString(R.string.blogs)) }
        _view.books.setOnClickListener { requestBuilder.setSection(getString(R.string.books)) }
        _view.business_day.setOnClickListener { requestBuilder.setSection(getString(R.string.business_day)) }
        _view.education.setOnClickListener { requestBuilder.setSection(getString(R.string.education)) }
        _view.fashion_and_style.setOnClickListener { requestBuilder.setSection(getString(R.string.fashion_and_style)) }
        _view.food.setOnClickListener { requestBuilder.setSection(getString(R.string.food)) }
        _view.health.setOnClickListener { requestBuilder.setSection(getString(R.string.health)) }
        _view.job_market.setOnClickListener { requestBuilder.setSection(getString(R.string.job_market)) }
        _view.magazine.setOnClickListener { requestBuilder.setSection(getString(R.string.magazine)) }
        _view.membercenter.setOnClickListener { requestBuilder.setSection(getString(R.string.membercenter)) }
        _view.movies.setOnClickListener { requestBuilder.setSection(getString(R.string.movies)) }
        _view.multimedia.setOnClickListener { requestBuilder.setSection(getString(R.string.multimedia)) }
        _view.NY202F20Region.setOnClickListener { requestBuilder.setSection(getString(R.string.NY202F20Region)) }
        _view.NYTNow.setOnClickListener { requestBuilder.setSection(getString(R.string.NYTNow)) }
        _view.obituaries.setOnClickListener { requestBuilder.setSection(getString(R.string.Obituaries)) }
        _view.open.setOnClickListener { requestBuilder.setSection(getString(R.string.Open)) }
        _view.opinion.setOnClickListener { requestBuilder.setSection(getString(R.string.Opinion)) }
        _view.public_editor.setOnClickListener { requestBuilder.setSection(getString(R.string.Public_Editor)) }
        _view.real_estate.setOnClickListener { requestBuilder.setSection(getString(R.string.Real_Estate)) }
        _view.science.setOnClickListener { requestBuilder.setSection(getString(R.string.Science)) }
        _view.sports.setOnClickListener { requestBuilder.setSection(getString(R.string.Sports)) }
        _view.style.setOnClickListener { requestBuilder.setSection(getString(R.string.Style)) }
        _view.sunday_review.setOnClickListener { requestBuilder.setSection(getString(R.string.Sunday_Review)) }
        _view.t_magazine.setOnClickListener { requestBuilder.setSection(getString(R.string.T_Magazine)) }
        _view.technology.setOnClickListener { requestBuilder.setSection(getString(R.string.Technology)) }
        _view.the_upshot.setOnClickListener { requestBuilder.setSection(getString(R.string.The_Upshot)) }
        _view.theater.setOnClickListener { requestBuilder.setSection(getString(R.string.Theater)) }
        _view.times_insider.setOnClickListener { requestBuilder.setSection(getString(R.string.Times_Insider)) }
        _view.todays_paper.setOnClickListener { requestBuilder.setSection(getString(R.string.Todays_Paper)) }
        _view.travel.setOnClickListener { requestBuilder.setSection(getString(R.string.Travel)) }
        _view.u_s.setOnClickListener { requestBuilder.setSection(getString(R.string.US)) }
        _view.world_category.setOnClickListener { requestBuilder.setSection(getString(R.string.World)) }
        _view.your_money_category.setOnClickListener { requestBuilder.setSection(getString(R.string.Your_Money)) }


    }





    override fun initRecycleView() {

        articlesRecyclerViewAdapter = ArticlesRecyclerViewAdapter(list, searchArticleViewModel, activity!!)

        _view.articlesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                NTArticlesApp.getAppComponent().getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        _view.articlesRecyclerView.layoutManager = LinearLayoutManager(NTArticlesApp.getAppComponent().getContext())
        _view.articlesRecyclerView.adapter = articlesRecyclerViewAdapter

    }

    override fun initRecyclerViewAdapter() {
        compositeDisposable = searchArticleViewModel.requestBuilder()
            .setSection(getString(R.string.all_sections))
            .setTimePeriod(30)
            .setSocial(SearchArticleViewModel.Social.MOST_SHARED)
            .build()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list.clear()
                list.addAll(it)
                _view.articlesRecyclerView.adapter!!.notifyDataSetChanged()

            }, {
                Utils.ShowToast(it.message!!, NTArticlesApp.getAppComponent().getContext())

            })

    }



}
