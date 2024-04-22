package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todoapp.Fragment.ListFragment
import com.example.todoapp.Fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale



class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNivigation:BottomNavigationView
    private lateinit var addButton:FloatingActionButton
    var todoListFragment=ListFragment()
    var todoSettingFragment=SettingFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)


        //to apply the last language the user choose before close the app
        val selected_language= SharedPreferenceHelper.LanguagePreferenceHelper.getLanguage(applicationContext)
        changeLanguage(selected_language)

        //to apply the last Mode the user choose before close the app
        val selected_Mode= SharedPreferenceHelper.LanguagePreferenceHelper.getMode(applicationContext)
        if(selected_Mode==0){//light
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{//dark
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }


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

    //to restart the main activity which contains the fragment
    fun restartFragment(){
        val intent=intent
        finish()
        startActivity(intent)
    }

    fun changeLanguage(language:String){

        val locale = Locale(language)
        val res: Resources =resources
        val config: Configuration =res.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        res.updateConfiguration(config,res.displayMetrics)
    }


}