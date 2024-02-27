package com.example.todoapp.Database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id:Int?=null,
    @ColumnInfo
    val name:String,
    @ColumnInfo
    val details:String,
    @ColumnInfo
    val date: Date,
    @ColumnInfo
    var isDone:Boolean=false
):Serializable