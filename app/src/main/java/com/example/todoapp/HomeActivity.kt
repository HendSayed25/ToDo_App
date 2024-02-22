package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todoapp.Fragment.ListFragment
import com.example.todoapp.Fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNivigation:BottomNavigationView
    private lateinit var addButton:FloatingActionButton
    var todoListFragment=ListFragment()
    var todoSettingFragment=SettingFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        bottomNivigation=findViewById(R.id.bottom_navigation)
        addButton=findViewById(R.id.add)

        bottomNivigation.setOnItemSelectedListener {
            if(it.itemId==R.id.navigation_list)
            {
                PushFragment(todoListFragment)
            }
            else if(it.itemId==R.id.navigation_setting)
            {
                PushFragment(todoSettingFragment)
            }
            return@setOnItemSelectedListener true
        }

        bottomNivigation.selectedItemId=R.id.navigation_list  // should be here because he should check first then put it as default


        //Add Task
        addButton.setOnClickListener{
            ShowAddButtonSheet()
        }

    }
    private fun PushFragment(fragment:Fragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }
    private fun ShowAddButtonSheet()
    {
        val addButtonSheet=AddToDoButtonSheet()
        addButtonSheet.show(supportFragmentManager,"")

        addButtonSheet.onTodoListner=object:AddToDoButtonSheet.onTodoAddedListner{
            override fun onTodoAdded() {
                //  refresh todoList from database inside fragment
                todoListFragment.getTaskListFromDB()
            }
        }
    }
}