package br.com.bozo.todolist

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CADASTRO: Int = 1
        private const val DO: String = "ToDo"
        private const val TODOLIST: String = "ToDoList"
    }

    var toDoList: MutableList<ToDo> = mutableListOf()
    //var indexLista: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener {
            val addToDo = Intent(this, AddActivity::class.java)
            startActivity(addToDo)
        }
    }

    override fun onResume() {
        super.onResume()
        carregaLista()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == REQUEST_CADASTRO && resultCode == Activity.RESULT_OK){
//            val toDo: ToDo? = data?.getSerializableExtra(AddActivity.DO) as ToDo
//
//            if(toDo != null) {
//
//                if (indexLista >= 0) {
//                    toDoList[indexLista] = toDo
//                    indexLista = -1
//                } else {
//                    toDoList.add(toDo)
//                }
//            }
//        }
//    }

    private fun carregaLista() {

        val toDoDao = AppDatabase.getInstance(this).toDoDao()

        doAsync {
            toDoList = toDoDao.getAll() as MutableList<ToDo>

            activityUiThreadWithContext {
                val adapter = ToDoAdapter(this, toDoList)

                adapter.setOnItemClickListener { toDo, indexLista ->
                    val editaToDo = Intent(this, AddActivity::class.java)
                    editaToDo.putExtra(DO, toDoList.get(indexLista))
                    startActivity(editaToDo)
                }

                adapter.setOnItemClickListenerDone { toDo, indexLista ->
                    doAsync {
                        Log.d("Meu Deus", indexLista.toString())
                        //if (toDoList[indexLista] != null)
                        toDoDao.delete(toDo)
                        uiThread {
                            carregaLista()
                        }

                    }
                }

                adapter.configuraCliqueLongo { indexLista ->
                    doAsync {
                        toDoDao.delete(toDoList.get(indexLista))

                        uiThread {
                            carregaLista()
                        }
                    }

                    true
                }

                val layoutManager = LinearLayoutManager(this)

                rvToDo.adapter = adapter
                rvToDo.layoutManager = layoutManager
            }
        }
    }
}
