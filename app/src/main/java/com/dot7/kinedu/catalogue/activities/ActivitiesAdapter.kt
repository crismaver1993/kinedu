package com.dot7.kinedu.catalogue.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dot7.kinedu.R

class ActivitiesAdapter(private val mContext: Context) :
    RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {
    private var allActivities: MutableList<ActivityInfo> = ArrayList()
    private var filteredList: MutableList<ActivityInfo> = ArrayList()

    fun setListInfo(list: MutableList<ActivityInfo>) {
        allActivities.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityViewHolder(
            layoutInflater.inflate(
                R.layout.item_activity_info,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val info = filteredList[position]
        holder.bind(mContext, info)
    }

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivCover = view.findViewById<ImageView>(R.id.iv_item_activity_info)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_item_activity_info_title)
        private val tvDescription = view.findViewById<TextView>(R.id.tv_item_activity_info_desc)
        private val lnCheck = view.findViewById<LinearLayout>(R.id.ln_item_activity_info_checks)

        fun bind(mContext: Context, info: ActivityInfo) {
            Glide.with(mContext).load(info.thumbnail).into(ivCover)
            tvTitle.text = info.name
            tvDescription.text = info.purpose
            var currentMilestone = 0
            while (currentMilestone < info.activeMilestones) {
                currentMilestone++
                val ivFoot = ImageView(mContext)
                val lp = LinearLayout.LayoutParams(80, 80)
                ivFoot.layoutParams = lp
                Glide.with(mContext).load(R.drawable.img_foot).into(ivFoot)
                lnCheck.addView(ivFoot)
            }
        }
    }
}