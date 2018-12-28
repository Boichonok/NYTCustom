package com.example.nytdevelopersmpapiclient.View.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.ViewUtils.ArticlesRecyclerViewAdapter
import com.example.nytdevelopersmpapiclient.ViewModel.SearchArticleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.search_article_fragment.view.*

class MostEmailedArticleFragment: BaseFragment() {
    override var layoutId: Int
        get() = R.layout.most_emailed_article_fragment
        set(value) {}


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
            .setSocial(SearchArticleViewModel.Social.MOST_EMAILED)
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