package com.dot7.kinedu.catalogue.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dot7.kinedu.R
import com.dot7.kinedu.interfaces.OnExerciseListener
import com.dot7.kinedu.models.ActivityDataInfo

class ActivitiesAdapter(private val mContext: Context, private val listener: OnExerciseListener) :
    RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {
    private var allActivities: MutableList<ActivityDataInfo> = ArrayList()
    private var filteredList: MutableList<ActivityDataInfo> = ArrayList()

    fun setListInfo(list: MutableList<ActivityDataInfo>) {
        list?.let {
            allActivities.clear()
            allActivities.addAll(list)
            filteredList.clear()
            filteredList.addAll(list)
            notifyDataSetChanged()
        }
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
        holder.bind(mContext, info, listener)
    }

    fun filterList(age: Int) {
        if (age != 0) {
            val newList: List<ActivityDataInfo> =
                allActivities.filter { it.age == age.toString() }
            filteredList.clear()
            filteredList.addAll(newList)
            notifyDataSetChanged()
        } else {
            filteredList.clear()
            filteredList.addAll(allActivities)
            notifyDataSetChanged()
        }
        listener.updateView(filteredList.size)
    }

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cvActivity = view.findViewById<CardView>(R.id.cv_activity_info)
        private val ivCover = view.findViewById<ImageView>(R.id.iv_item_activity_info)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_item_activity_info_title)
        private val tvDescription = view.findViewById<TextView>(R.id.tv_item_activity_info_desc)
        private val lnCheck = view.findViewById<LinearLayout>(R.id.ln_item_activity_info_checks)

        fun bind(mContext: Context, info: ActivityDataInfo, listener: OnExerciseListener) {
            lnCheck.removeAllViews()
            Glide.with(mContext).load(info.thumbnail).into(ivCover)
            tvTitle.text = info.name
            tvDescription.text = info.purpose
            paintChecks(mContext, info.activeMilestones)
            cvActivity.setOnClickListener { listener.showActivityDetail(info) }
        }

        /**
         * Paint the green circle checks
         */
        private fun paintChecks(mContext: Context, activeMilestones: Int) {
            var currentMilestone = 0
            while (currentMilestone < activeMilestones) {
                currentMilestone++
                val ivFoot = ImageView(mContext)
                val lp = LinearLayout.LayoutParams(40, 40)
                ivFoot.layoutParams = lp
                Glide.with(mContext).load(R.drawable.ic_check_circle_green).into(ivFoot)
                lnCheck.addView(ivFoot)
            }
        }
    }
}