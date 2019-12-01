package com.dot7.kinedu.ui.catalogue.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dot7.kinedu.R

class ActivitiesAdapter(mContext: Context) :
    RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {
    private var allActivities: MutableList<ActivityData> = ArrayList()
    private var filteredList: MutableList<ActivityData> = ArrayList()

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
        holder.bind(info)
    }

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*  val tvTask = view.findViewById<TextView>(R.id.tv_item_task)
          val cbIsDone = view.findViewById<CheckBox>(R.id.cb_item_is_done)*/

        fun bind(info: ActivityData) {
            /* tvTask.text = task.name
             cbIsDone.isChecked = task.isDone*/


        }
    }
}