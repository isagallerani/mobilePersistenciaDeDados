package br.com.bozo.todolist

import android.arch.persistence.room.*

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDo: ToDo)

    @Query("SELECT * FROM ToDo")
    fun getAll(): List<ToDo>

//    @Update
//    fun update(toDo: ToDo)

    @Delete
    fun delete(toDo: ToDo)

    @Query("SELECT * FROM ToDo WHERE id = :toDoId LIMIT 1")
    fun getToDo(toDoId: Int): ToDo
}