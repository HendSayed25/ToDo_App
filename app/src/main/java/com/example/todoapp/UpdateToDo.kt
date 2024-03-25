package com.example.todoapp


import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.todoapp.Database.MyDataBase
import com.example.todoapp.Database.model.Todo
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UpdateToDo : AppCompatActivity() {
    private lateinit var  title:TextView
    private lateinit var  details:TextView
    private lateinit var  title_layout:TextInputLayout
    private lateinit var  details_layout:TextInputLayout
    private lateinit var  choose_Date:TextView
    private lateinit var  update_btn: Button
    private var calender=Calendar.getInstance()
    private lateinit var todo:Todo
    private var updateDate:Boolean=false
    private lateinit var update_todo:Todo
    private lateinit var old_date:Date
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_todo)

        initialize()
        recieveData()
    }

    private fun initialize() {

        title=findViewById(R.id.update_task_name)
        details=findViewById(R.id.update_task_detalis)
        choose_Date=findViewById(R.id.update_ChooseDate)
        update_btn=findViewById(R.id.update_task)
        title_layout=findViewById(R.id.update_title_layout)
        details_layout=findViewById(R.id.update_details_layout)


        choose_Date.setOnClickListener{
            ShowDatePicker()
            updateDate=true
        }

        update_btn.setOnClickListener{
            if(valid()){
                //taking data from fields after update it

                var Title:String=title.text.toString()
                var Details:String=details.text.toString()

                var formatter = SimpleDateFormat("dd-MM-yyyy")
                var formattedDate = formatter.parse(formatter.format(calender.time))

                if(updateDate==true) {
                     update_todo = Todo(todo.id, Title, Details, formattedDate, false)
                }
                else{
                    update_todo = Todo(todo.id, Title, Details,old_date, false)

                }
                Update(update_todo)

            }
        }
    }

    private fun  recieveData() {

         todo=(intent.getSerializableExtra("todo") as? Todo)!! // used as? to make custom

        //insert this data into fields
        title.setText(todo.name)
        details.setText(todo.details)
        var formatter = SimpleDateFormat("dd-MM-yyyy")
        var formattedDate = formatter.format(todo.date) 
        choose_Date.setText(formattedDate)
        old_date=todo.date

    }

    private fun valid():Boolean
    {
        var isValid=true
        if(title_layout.editText?.text.toString().isBlank())
        {
            title_layout.error="Please Enter Name Of The Todo"
            isValid=false
        }
        else
        {
            title_layout.error=null
        }
        if(details_layout.editText?.text.toString().isBlank())
        {
            title_layout.error="Please Enter Details Of The Task"
            isValid=false
        }
        else
        {
            details_layout.error=null
        }
        return isValid
    }

    private fun ShowDatePicker()
    {
        // this code to show the date in the text view
        val datePicker=
            DatePickerDialog(this ,object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                choose_Date.setText(""+dayOfMonth+"/"+(month+1)+"/"+year) // in calender the month begin from index 0 so we will add 1 to month
                //to save the date which i select
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            }
        },calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH))

        datePicker.show()

    }

    private fun Update(todo:Todo){

        MyDataBase.getInstance(this).todoDao().updateTodo(todo)
        Toast.makeText(this,"Todo Updated Successfully",Toast.LENGTH_LONG).show()

        onBackPressed() //this function return me to the back activity

    }
}
