package br.com.bozo.todolist

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
data class ToDo (var texto: String,
                 @PrimaryKey(autoGenerate = true)
                 var id: Int = 0): Serializable