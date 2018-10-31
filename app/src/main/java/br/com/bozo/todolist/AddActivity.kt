package br.com.bozo.todolist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddActivity : AppCompatActivity() {

    companion object {
        public const val DO: String = "ToDo"
    }

    var toDo: ToDo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

       toDo = intent.getSerializableExtra(DO) as ToDo?
        if(toDo != null){
            carregaDados()
            //edtToDo.setText(toDo)
        }

        btnSalvar.setOnClickListener{
            salvaToDo()
        }
    }


    private fun salvaToDo() {

        if(edtToDo.text.isEmpty()){
            edtToDo.requestFocus()
            edtToDo.setError(getString(R.string.campo_obriatorio))
            return
        }

        if(toDo == null){
            toDo = ToDo(edtToDo.text.toString())
        }else{
            toDo?.texto = edtToDo.text.toString()
        }

        val toDoDao: ToDoDao = AppDatabase.getInstance(this).toDoDao()
        doAsync {
            try {
                toDoDao.insert(toDo!!)
            }catch (err: Error){
                val x = err
            }

            uiThread {
                finish()
            }
        }

    }

    private fun carregaDados() {
        edtToDo.setText(toDo?.texto)
    }

}
