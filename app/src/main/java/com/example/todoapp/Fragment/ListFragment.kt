package com.example.todoapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Database.MyDataBase
import com.example.todoapp.Database.model.Todo
import com.example.todoapp.ListAdapter
import com.example.todoapp.R
import com.example.todoapp.clearTime
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialCalendar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.zerobranch.layout.SwipeLayout
import java.util.Calendar

class ListFragment: Fragment() {

    private lateinit var recyclerView:RecyclerView
    private var adapter=ListAdapter(null)
    private lateinit var calenderView:MaterialCalendarView
    var date =Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView=view.findViewById(R.id.recycler)
        calenderView=view.findViewById(R.id.calendarView)
        calenderView.selectedDate= CalendarDay.today()
        recyclerView.adapter=adapter


        //to show tasks based on the date which i selected
        calenderView.setOnDateChangedListener { widget, calenderDay, selected ->

            date.set(Calendar.DAY_OF_MONTH, calenderDay.day)
            date.set(Calendar.MONTH, calenderDay.month - 1) // minus 1 because the calender start of 0 and calenderDay start from 1
            date.set(Calendar.YEAR, calenderDay.year)

            getTaskListFromDB()
        }

        onItemClicked()

    }

    override fun onResume() { //this fun will call when the list is appear in application
        super.onResume()
        getTaskListFromDB()
    }

    lateinit var tasksList:MutableList<Todo>

    fun onItemClicked()
    {
        adapter.onItemClick=object:ListAdapter.onItemClicked{

            override fun onItemClickedToBeDeleted(position: Int,todo:Todo) {

                //delete from GUI
                tasksList.removeAt(position)//remove from list
                //remove from adapter
                adapter.notifyItemRemoved(position)
                adapter.notifyDataSetChanged()

                //delete from database
                MyDataBase.getInstance(requireContext()).todoDao().deleteTodo(todo)

                Toast.makeText(requireContext(),"Deleted Successfully",Toast.LENGTH_LONG).show()

                getTaskListFromDB()
            }


        }
    }

    fun getTaskListFromDB()
    {
        //when i try to add task and not open list fragment should not do anything
        if(context==null)return
        //  date.time return time in millseconds so when add new task not appear in the same minute to ignore this problem  will ignore the time and need only the day using class extenstion
        tasksList=MyDataBase.getInstance(requireContext()).todoDao().getAllTodoByDate(date.clearTime().time)
        adapter.ChangeData(tasksList.toMutableList())
    }
}