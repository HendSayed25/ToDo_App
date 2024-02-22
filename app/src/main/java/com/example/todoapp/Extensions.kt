package com.example.todoapp


import java.util.Calendar

fun Calendar.clearTime():Calendar{

    this.clear(Calendar.MILLISECOND)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.HOUR)

    return this
}