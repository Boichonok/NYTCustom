package com.example.nytdevelopersmpapiclient.View.ViewUtils

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.Model.Repository.NtArticleRepository
import com.example.nytdevelopersmpapiclient.R
import com.example.nytdevelopersmpapiclient.Utils
import com.example.nytdevelopersmpapiclient.View.AboutArticleWebActivity
import com.example.nytdevelopersmpapiclient.ViewModel.FavoriteArticleViewModel
import com.example.nytdevelopersmpapiclient.ViewModel.SearchArticleViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.articles_recycle_view_item.view.*

class FavoriteArticlesRecyclerViewAdapter(val items: ArrayList<ArticleMainInfo>,
                                          val activity: FragmentActivity) :
    RecyclerView.Adapter<FavoriteArticlesRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(NTArticlesApp.getAppComponent().getContext()).inflate(R.layout.articles_recycle_view_item,parent,false))
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = items[position].title


        Picasso.get()
            .load(items[position].url_picture)
            .placeholder(R.drawable.ic_image_loading)
            .error(R.drawable.ic_image_error)
            .into(holder.picture)

        holder.tvByLine.text = items[position].byline
        Log.d("Adapter","Abstract: " + items[position].abstract_)
        holder.tvSource.text = items[position].source
        holder.tvSocialFilter.text = items[position].sortType

        holder.tvDatePublished.text = items[position].publishedDate
        holder.picture.setOnClickListener {

                val dialog = ArticlesDialog.Builder(activity)
                    .setTitle(items[position].title)
                    .setAbstract(items[position].abstract_)
                    .setAuthor(items[position].byline)
                    .setSource(items[position].source)
                    .setPublishedDate(items[position].publishedDate)
                    .setIsCancelAction(true)
                    .setIsAddToFavoriteAction(false)
                    .setAddToFavoriteAction {  }
                    .setCancelAction {
                        Utils.ShowToast("Article closed", NTArticlesApp.getAppComponent().getContext())
                        Log.d("Adapter","Abstract: " + items[position].abstract_)
                    }
                    .build()
                dialog.showDialog()


        }
        holder.moreButton.setOnClickListener {
            val intent = Intent(activity, AboutArticleWebActivity::class.java)
            intent.putExtra("URL",items[position].url)
            activity.startActivity(intent)
        }
    }



    fun removeAndGetSelectedPositionId(position: Int): Int {
        val id = items.removeAt(position).id
        notifyItemRemoved(position)
        return id
    }



    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        val tvTitle = view.title
        val picture = view.picture

        val tvSource = view.source
        val tvByLine = view.byLine
        val tvSocialFilter = view.social_filter
        val tvDatePublished = view.datePublished
        val moreButton = view.more_button
    }

}