package com.example.todoapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.example.todoapp.Database.MyDataBase
import com.example.todoapp.Database.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar

//this button like the fragment
class AddToDoButtonSheet :BottomSheetDialogFragment(){

    private lateinit var titleLayout:TextInputLayout
    private lateinit var detailsLayout:TextInputLayout
    private lateinit var chooseDate:TextView
    private lateinit var addTask:Button
    private var calender=Calendar.getInstance() // this to get the date of the day i use it


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLayout=view.findViewById(R.id.title_layout)
        detailsLayout=view.findViewById(R.id.details_layout)
        chooseDate=view.findViewById(R.id.ChooseDate)
        addTask=view.findViewById(R.id.add_task)

        // this to assign the default date in the textview is the current date
        chooseDate.setText(""+calender.get(Calendar.DAY_OF_MONTH)+"/"+(calender.get(Calendar.MONTH)+1)+"/"+calender.get(Calendar.YEAR))

        chooseDate.setOnClickListener{
            ShowDatePicker()
        }

        addTask.setOnClickListener{
            //validation
            if(Validate())
            {
                // add task to data base
                var title=titleLayout.editText?.text.toString()
                var details=detailsLayout.editText?.text.toString()

                insertToDo(title,details)
            }

        }
    }

    private fun ShowDatePicker()
    {
        // this code to show the date in the text view
        val datePicker=DatePickerDialog(requireContext(),object:OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                chooseDate.setText(""+dayOfMonth+"/"+(month+1)+"/"+year) // in calender the month begin from index 0 so we will add 1 to month
                //to save the date which i select
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            }
        },calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH))

        datePicker.show()

    }

    private fun Validate():Boolean
    {
        var isValid=true
        if(titleLayout.editText?.text.toString().isBlank())
        {
            titleLayout.error="Please Enter Name Of The Task"
            isValid=false
        }
        else
        {
            titleLayout.error=null
        }
        if(detailsLayout.editText?.text.toString().isBlank())
        {
            titleLayout.error="Please Enter Details Of The Task"
            isValid=false
        }
        else
        {
            detailsLayout.error=null
        }
        return isValid
    }

    private fun insertToDo(title:String, details:String)
    {

        var formatter = SimpleDateFormat("dd-MMMM-yyyy")
        var formattedDate = formatter.parse(formatter.format(calender.time))

        val todo= Todo(
            name=title,
            details=details,
            date=formattedDate,
            isDone = false
        )
        MyDataBase.getInstance(requireContext().applicationContext).todoDao().addTodo(todo)


        Toast.makeText(requireContext(),"Todo Added Successfully",Toast.LENGTH_LONG).show()

        //call back to activity to notify insertion
        onTodoListner?.onTodoAdded()

        dismiss() //this to close the button sheet when added the task
    }

    var onTodoListner:onTodoAddedListner?=null
    interface onTodoAddedListner{
        fun onTodoAdded()
    }
}