package br.com.bozo.todolist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lista_layout.view.*

class ToDoAdapter(val context: Context, val toDoList: List<ToDo>)
    : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    var clickListener: ((toDo: ToDo, index: Int) -> Unit)? = null
    var clickListenerDone: ((toDo: ToDo, index: Int) -> Unit)? = null
    var cliqueLongoListener: ((index: Int) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(context, toDoList[position], clickListener, clickListenerDone, cliqueLongoListener)
    }

    fun setOnItemClickListener(clique: ((toDo: ToDo, index: Int) -> Unit)){
        this.clickListener = clique
    }

    fun setOnItemClickListenerDone(cliqueDone: ((toDo: ToDo, index: Int) -> Unit)){
        this.clickListenerDone = cliqueDone
    }

    fun configuraCliqueLongo(cliqueLongo: ((index: Int) -> Boolean)) {
        this.cliqueLongoListener = cliqueLongo
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(context:Context, toDo: ToDo, clickListener: ((toDo: ToDo, index: Int) -> Unit)?, clickListenerDone: ((toDo: ToDo, index: Int) -> Unit)?, cliqueLongoListener: ((index: Int) -> Boolean)?) {
            itemView.txtItem.text = toDo.texto

            if(clickListener != null) {
                itemView.setOnClickListener {
                    clickListener.invoke(toDo, adapterPosition)
                }
            }

            if(clickListenerDone != null){
                itemView.btnDone.setOnClickListener{
                    clickListenerDone.invoke(toDo, adapterPosition)
                }
            }

            if(cliqueLongoListener != null) {
                itemView.setOnLongClickListener{
                    cliqueLongoListener.invoke((adapterPosition))
                }
            }

        }

    }
}