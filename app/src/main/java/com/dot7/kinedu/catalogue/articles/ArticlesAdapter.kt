package com.dot7.kinedu.catalogue.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dot7.kinedu.R
import com.dot7.kinedu.interfaces.OnExerciseListener
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.util.customview.RectangleImageView

class ArticlesAdapter(private val mContext: Context, private val listener: OnExerciseListener) :
    RecyclerView.Adapter<ArticlesAdapter.ActivityViewHolder>() {
    private var allArticles: MutableList<ArticleInfoData> = ArrayList()
    private var articlesFiltered: MutableList<ArticleInfoData> = ArrayList()

    fun setListInfo(list: MutableList<ArticleInfoData>) {
        allArticles.clear()
        allArticles.addAll(list)
        articlesFiltered.clear()
        articlesFiltered.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityViewHolder(
            layoutInflater.inflate(
                R.layout.item_article_info,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return articlesFiltered.size
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val info = articlesFiltered[position]
        holder.bind(mContext, info, listener)
    }

    fun filterList(age: Int) {
        if(age != 0){
            val newList: List<ArticleInfoData> =
                allArticles.filter { it.minAge == age.toString() }
            articlesFiltered.clear()
            articlesFiltered.addAll(newList)
            notifyDataSetChanged()

        }else{
            articlesFiltered.clear()
            articlesFiltered.addAll(allArticles)
            notifyDataSetChanged()
        }

       listener.updateView(articlesFiltered.size)
    }

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cvArticle = view.findViewById<CardView>(R.id.cv_article_info)
        private val ivCover = view.findViewById<RectangleImageView>(R.id.iv_item_article_info)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_item_article_info_title)
        private val tvDescription = view.findViewById<TextView>(R.id.tv_item_article_info_desc)

        fun bind(mContext: Context, info: ArticleInfoData, listener: OnExerciseListener) {
            Glide.with(mContext).load(info.picture).into(ivCover)
            tvTitle.text = info.name
            tvDescription.text = info.shortDesc
            cvArticle.setOnClickListener { listener.showArticleDetail(info, ivCover) }
        }
    }
}