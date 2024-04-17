package com.example.todoapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Database.model.Todo

//use mutable list to can delete and add as we need
// ? this means i may send empty list
class ListAdapter(var items:MutableList<Todo>?): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Title:TextView=itemView.findViewById(R.id.title_of_task)
        var Desc:TextView=itemView.findViewById(R.id.description)
        var CheckAsDone:ImageView=itemView.findViewById(R.id.check)
        var done:TextView=itemView.findViewById(R.id.done)
        var delete_pin_right:ImageView=itemView.findViewById(R.id.right_view)
        var delete_pin_left:ImageView=itemView.findViewById(R.id.left_view)

        var cardItem:CardView=itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val value= items?.get(position)
        holder.Title.setText(value?.name)
        holder.Desc.setText(value?.details)

        if(value?.isDone==true){
            holder.done.visibility=View.VISIBLE
            holder.CheckAsDone.visibility=View.INVISIBLE
           // holder.Title.setTextColor(Color.parseColor("#0D9276"))
            holder.Desc.setTextColor(Color.parseColor("#0D9276"))
        }
        else{
            holder.done.visibility=View.INVISIBLE
            holder.CheckAsDone.visibility=View.VISIBLE
            holder.Title.setTextColor(Color.parseColor("#FF5D9CEC"))
            holder.Desc.setTextColor(Color.parseColor("#FF5D9CEC"))
        }

        if(onItemClick!=null){
            holder.delete_pin_right.setOnClickListener {
                onItemClick?.onItemClickedToBeDelete(position,value!!)
            }
            holder.delete_pin_left.setOnClickListener {
                onItemClick?.onItemClickedToBeDelete(position,value!!)
            }

            holder.cardItem.setOnClickListener{
                onItemClick?.onItemClickedToBeUpdate(value!!)
            }

            holder.CheckAsDone.setOnClickListener {
                onItemClick?.checkAsDone(holder,value!!)
            }

        }

    }
    fun ChangeData(Task:MutableList<Todo>)
    {
        items=Task
        notifyDataSetChanged() // this make adapter to reload and update it's data

    }

    var onItemClick:onItemClicked?=null

    interface onItemClicked{

        fun onItemClickedToBeDelete(position:Int, todo:Todo)

        fun onItemClickedToBeUpdate(todo:Todo)

        fun checkAsDone(holder:ListViewHolder,todo:Todo)

    }
}