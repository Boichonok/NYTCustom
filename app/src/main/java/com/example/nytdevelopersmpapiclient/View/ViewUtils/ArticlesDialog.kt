package com.example.nytdevelopersmpapiclient.View.ViewUtils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.nytdevelopersmpapiclient.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.articles_dialog.*

open class ArticlesDialog: Dialog {

    private lateinit var activity: Activity



    private lateinit var title:String
    private lateinit var abstract: String
    private lateinit var author:String
    private lateinit var source: String
    private lateinit var publishedDate: String
    private lateinit var action: () -> Unit
    private lateinit var addToFavoriteAction: (compositeDisposable: CompositeDisposable) -> Unit
    private var isCancelAction: Boolean = false
    private var isAddToFavoriteAction: Boolean = false

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    constructor(activity: Activity): super(activity)
    {
        this.activity = activity
    }

    private constructor(activity: Activity,
                        title: String,
                        abstract: String,
                        source: String,
                        author: String,
                        publishedDate: String,
                        isCancelAction: Boolean,
                        cancelAction: () -> Unit,
                        isAddToFavoriteAction: Boolean,
                        addToFavoriteAction: (compositeDisposable: CompositeDisposable) -> Unit): super(activity)
    {
        this.title = title?: "No title"
        this.abstract = abstract?:"No abstract"
        this.source = source?:"No source"
        this.author = author?:"No author"
        this.publishedDate = publishedDate?:"No published date"
        this.action = cancelAction
        this.addToFavoriteAction = addToFavoriteAction
        this.isCancelAction = isCancelAction
        this.isAddToFavoriteAction = isAddToFavoriteAction
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)


        setContentView(R.layout.articles_dialog)
        setTitle(title)
        setAbstract(abstract)
        setAuthor(author)
        setSource(source)
        setPublishedDate(publishedDate)
        if(isCancelAction)
        {
            setCancelClickListener(action)
        }
        else
        {
            cancel_button.visibility = View.GONE
        }
        if(isAddToFavoriteAction)
        {
            setAddToFavoriteAction(addToFavoriteAction)
        }
        else
        {
            save_button.visibility = View.GONE
        }
    }


    public fun showDialog()
    {
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.attributes.windowAnimations = R.style.CustomDialogTheme
        show()
    }

    class Builder
    {
        private lateinit var title: String
        private lateinit var abstract: String
        private lateinit var source: String
        private lateinit var author: String
        private lateinit var publishedDate: String
        private lateinit var action: () -> Unit
        private lateinit var addToFavoriteAction: (compositeDisposable: CompositeDisposable) -> Unit
        private var isCancelAction: Boolean = false
        private var isAddToFavoriteAction:Boolean = false

        private lateinit var activity: Activity

        public constructor(activity: Activity)
        {
            this.activity = activity
        }

        fun setTitle(title: String): Builder
        {
            this.title = title?:"No title"
            return this
        }

        fun setAbstract(abstract: String): Builder
        {
            this.abstract = abstract?:"No abstract"
            return this
        }

        fun setSource(source: String): Builder
        {
            this.source = source?:"No source"
            return this
        }

        fun setAuthor(author: String): Builder
        {
            this.author = author?:"No author"
            return this
        }

        fun setPublishedDate(publishedDate: String): Builder
        {
            this.publishedDate = publishedDate?:"No published Date"
            return this
        }

        fun setCancelAction(action: () -> Unit): Builder
        {
            this.action = action
            return this
        }

        fun setAddToFavoriteAction(addToFavoriteAction: (compositeDisposable: CompositeDisposable) -> Unit): Builder
        {
            this.addToFavoriteAction = addToFavoriteAction
            return this
        }

        fun setIsCancelAction(value: Boolean): Builder
        {
            this.isCancelAction = value
            return this
        }

        fun setIsAddToFavoriteAction(value:Boolean): Builder
        {
            this.isAddToFavoriteAction = value
            return this
        }

        fun build() : ArticlesDialog
        {
            return ArticlesDialog(activity,title,abstract,source,author,publishedDate,isCancelAction,action,isAddToFavoriteAction,addToFavoriteAction)
        }
    }
    private fun setTitle(title:String)
    {
        title_article_dialog.text = title?:"No title"
    }

    private fun setAbstract(abstract:String)
    {
        abstract_article_dialog.text = abstract?:"No abstract"
    }

    private fun setSource(source:String)
    {
        source_article_dialog.text = source?:"No source"
    }

    private fun setAuthor(author:String)
    {
        by_line_article_dialog.text = author?:"No author"
    }

    private fun setPublishedDate(publishedDate: String)
    {
        published_date_articles_dialog.text = publishedDate?:"No published date"
    }

    private fun setCancelClickListener(action: ()-> Unit)
    {
        cancel_button.visibility = View.VISIBLE
        cancel_button.setOnClickListener {
            action()
            compositeDisposable.clear()
            dismiss()
        }
    }

    private fun setAddToFavoriteAction(addToFavoriteAction: (compositeDisposable: CompositeDisposable) -> Unit)
    {
        save_button.visibility = View.VISIBLE
        save_button.setOnClickListener {
            addToFavoriteAction(compositeDisposable)
            dismiss()
        }
    }

}