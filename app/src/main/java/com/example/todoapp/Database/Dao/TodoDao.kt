package com.example.todoapp.Database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Database.model.Todo

import java.util.Date

@Dao
interface TodoDao {

    @Insert
    fun addTodo(todo: Todo)

    @Update
    fun updateTodo(todo:Todo)

    @Delete
    fun deleteTodo(todo:Todo)

    @Query("select * from Todo")
    fun getAllTodo():List<Todo>

    @Query("select * from Todo where date=:date")
    fun getAllTodoByDate(date: Date):MutableList<Todo>


}