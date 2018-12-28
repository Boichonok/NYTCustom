package com.example.nytdevelopersmpapiclient.View.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.ViewUtils.FavoriteArticlesRecyclerViewAdapter
import com.example.nytdevelopersmpapiclient.View.ViewUtils.SwipeToDeleteCallback
import com.example.nytdevelopersmpapiclient.ViewModel.FavoriteArticleViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.favorite_articles_fragment.view.*


class FavoriteArticlesFragment : Fragment() {

    private lateinit var favoriteArticleViewModel: FavoriteArticleViewModel

    private lateinit var articlesRecyclerViewAdapter: FavoriteArticlesRecyclerViewAdapter

    private var list: ArrayList<ArticleMainInfo> = ArrayList()

    private val compositeDisposable_ = CompositeDisposable()

    private lateinit var _view: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _view = inflater.inflate(R.layout.favorite_articles_fragment, container, false)

        favoriteArticleViewModel = ViewModelProviders.of(this).get(FavoriteArticleViewModel::class.java)

        initRecycleView()
        swipeHolder()





        Log.d("Favorite", favoriteArticleViewModel.getSomething())
        initRecycleViewAdapter()

        return _view
    }

    private fun swipeHolder() {
        val swipeHolder = object : SwipeToDeleteCallback(context = NTArticlesApp.getAppComponent().getContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = articlesRecyclerViewAdapter
                val id = adapter.removeAndGetSelectedPositionId(viewHolder.adapterPosition)
                Log.d("DELETE", "Position ${viewHolder.adapterPosition}")
                compositeDisposable_.add(favoriteArticleViewModel.deleteFavoriteArticleById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Utils.ShowToast(
                            "Item ${viewHolder.adapterPosition} was deleted",
                            NTArticlesApp.getAppComponent().getContext()
                        )
                    }, {
                        Utils.ShowToast("${it.localizedMessage}}", NTArticlesApp.getAppComponent().getContext())
                    })
                )
                //_view.favoriteArticlesRecyclerView.adapter!!.notifyDataSetChanged()

            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHolder)
        itemTouchHelper.attachToRecyclerView(_view.favoriteArticlesRecyclerView)
    }

    private fun initRecycleView() {


        articlesRecyclerViewAdapter = FavoriteArticlesRecyclerViewAdapter(list, activity!!)

        _view.favoriteArticlesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                NTArticlesApp.getAppComponent().getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        _view.favoriteArticlesRecyclerView.layoutManager =
                LinearLayoutManager(NTArticlesApp.getAppComponent().getContext())
        _view.favoriteArticlesRecyclerView.adapter = articlesRecyclerViewAdapter

    }

    private fun initRecycleViewAdapter() {
        compositeDisposable_.add(favoriteArticleViewModel.getAllFavoriteArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Result", "Result Size: ${it.size}")
                list.clear()
                list.addAll(it)
                _view.favoriteArticlesRecyclerView.adapter!!.notifyDataSetChanged()
            }, {
                Utils.ShowToast(it.localizedMessage, NTArticlesApp.getAppComponent().getContext())
            })
        )

    }


    override fun onDetach() {
        super.onDetach()
        compositeDisposable_.clear()
    }

}