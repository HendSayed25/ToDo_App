package com.example.todoapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.Database.Dao.TodoDao
import com.example.todoapp.Database.model.Todo

@Database(entities = [Todo::class],version=1)
@TypeConverters(DateConverter::class)
abstract class MyDataBase:RoomDatabase() {

    abstract fun todoDao():TodoDao
    companion object{
        private const val DATABASE_NAME="todo-Database"
        private var myDatabase:MyDataBase?=null
        fun getInstance(context:Context): MyDataBase {
            // this condition to ensure we build one object from my database
            if(myDatabase==null) {
                // this will create object from my database( this named singleton pattern)
                myDatabase=Room.databaseBuilder(context, MyDataBase::class.java, DATABASE_NAME).
                fallbackToDestructiveMigration().//this function to if we change the database this will replace the old by the new
                allowMainThreadQueries(). //this fun to allow make query in main thread which control of user interface
                build()
            }
            return myDatabase!!
        }
    }
}