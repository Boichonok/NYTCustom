package com.example.nytdevelopersmpapiclient.View.ViewUtils

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.AboutArticleWebActivity
import com.example.nytdevelopersmpapiclient.ViewModel.SearchArticleViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.articles_recycle_view_item.view.*

class ArticlesRecyclerViewAdapter(val items: ArrayList<Article>,
                                  val viewModel: ViewModel,
                                  val activity: FragmentActivity) :
    RecyclerView.Adapter<ArticlesRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(NTArticlesApp.getAppComponent().getContext()).inflate(R.layout.articles_recycle_view_item,parent,false))
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = items[position].title
        Log.d("Adapter","Media size: ${items[position].media.size}")
        val allPictures = items[position].media[0].mediaMetoData.size
        Picasso.get()
            .load(items[position].media[0].mediaMetoData[allPictures-1].url)
            .placeholder(R.drawable.ic_image_loading)
            .error(R.drawable.ic_image_error)
            .into(holder.picture)

        holder.tvByLine.text = items[position].byLine
        Log.d("Adapter","Author: " + items[position].byLine)
        holder.tvSource.text = items[position].source
        holder.tvSocialFilter.text = items[position].countType

        holder.tvDatePublished.text = items[position].publishedDate
        holder.picture.setOnClickListener {

                val dialog = ArticlesDialog.Builder(activity)
                    .setTitle(items[position].title)
                    .setAbstract(items[position].abstractText)
                    .setAuthor(items[position].byLine)
                    .setSource(items[position].source)
                    .setPublishedDate(items[position].publishedDate)
                    .setIsCancelAction(true)
                    .setIsAddToFavoriteAction(true)
                    .setCancelAction {
                        Utils.ShowToast("Article closed", NTArticlesApp.getAppComponent().getContext())
                    }
                    .setAddToFavoriteAction {
                        val searchArticleViewModel = viewModel as SearchArticleViewModel
                        Log.d("Dialog","Abstract ${items[position].abstractText}")
                        val articleToSave = ArticleMainInfo(
                            items[position].url,
                            items[position].media[0].mediaMetoData[allPictures - 1].url,
                            items[position].countType,
                            items[position].section,
                            items[position].byLine,
                            items[position].abstractText,
                            items[position].publishedDate,
                            items[position].source,
                            items[position].title
                        )
                        Log.d("Dialog","Abstract ${articleToSave.abstract_}")
                        it.add(searchArticleViewModel.saveArticle(articleToSave)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Utils.ShowToast("Article was saved", NTArticlesApp.getAppComponent().getContext())
                            }, { it ->
                                Utils.ShowToast(
                                    "${it.localizedMessage}",
                                    NTArticlesApp.getAppComponent().getContext()
                                )
                            })
                        )

                    }
                    .build()
                dialog.showDialog()


        }
        holder.saveButton.setOnClickListener {
            val intent = Intent(activity, AboutArticleWebActivity::class.java)
            intent.putExtra("URL",items[position].url)
            activity.startActivity(intent)
        }
    }





    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        val tvTitle = view.title
        val picture = view.picture

        val tvSource = view.source
        val tvByLine = view.byLine
        val tvSocialFilter = view.social_filter
        val tvDatePublished = view.datePublished
        val saveButton = view.more_button
    }

}