package com.example.nytdevelopersmpapiclient.View.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.ViewUtils.ArticlesRecyclerViewAdapter
import com.example.nytdevelopersmpapiclient.ViewModel.SearchArticleViewModel
import com.mancj.slideup.SlideUp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.categories.view.*
import kotlinx.android.synthetic.main.search_article_fragment.view.*

abstract class BaseFragment: Fragment() {

    protected lateinit var articlesRecyclerViewAdapter: ArticlesRecyclerViewAdapter

    protected lateinit var searchArticleViewModel: SearchArticleViewModel

    protected lateinit var requestBuilder: SearchArticleViewModel.BuildRequestForArticle

    protected lateinit var compositeDisposable: Disposable

    protected var list: ArrayList<Article> = ArrayList()

    protected lateinit var _view: View

    protected abstract var layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _view = inflater.inflate(layoutId, container, false)
        searchArticleViewModel = ViewModelProviders.of(this).get(SearchArticleViewModel::class.java)
        requestBuilder = searchArticleViewModel.requestBuilder()

       // initSlideUp()
        initRecycleView()

        return _view
    }

    abstract fun initRecycleView()

    abstract fun initRecyclerViewAdapter()


    protected fun initSlideUp() {
        val slideUpCategories = SlideUp(_view.slideViewCategories)
        slideUpCategories.hideImmediately()
        _view.categories.setOnClickListener {
            slideUpCategories.animateIn()
            _view.categories.visibility = View.INVISIBLE

        }

        slideUpCategories.setSlideListener(object : SlideUp.SlideListener {
            override fun onVisibilityChanged(state: Int) {
                if (state == View.GONE) {
                    _view.categories.visibility = View.VISIBLE
                    compositeDisposable = requestBuilder.build()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            list.clear()
                            list.addAll(it.filterNotNull())
                            _view.articlesRecyclerView.adapter!!.notifyDataSetChanged()
                        }, {
                            Log.d("SearchArticleFragment", it.message!!)
                            Utils.ShowToast(it.message!!, NTArticlesApp.getAppComponent().getContext())
                        })
                }
            }

            override fun onSlideDown(v: Float) {
                _view.dim.alpha = 1.0f - (v / 100.0f)

            }

        })
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.dispose()
    }

    override fun onStart() {
        super.onStart()
        initRecyclerViewAdapter()
    }
}