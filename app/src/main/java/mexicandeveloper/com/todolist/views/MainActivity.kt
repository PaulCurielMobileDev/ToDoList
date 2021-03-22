package mexicandeveloper.com.todolist.views

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import mexicandeveloper.com.todolist.R
import mexicandeveloper.com.todolist.api.ApiHelper
import mexicandeveloper.com.todolist.models.ToDoElementData
import mexicandeveloper.com.todolist.repository.RetrofitBuilder
import mexicandeveloper.com.todolist.utils.Status
import mexicandeveloper.com.todolist.viewmodels.ToDoListViewModel.ToDoListViewModel
import mexicandeveloper.com.todolist.viewmodels.ToDoListViewModel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var adapter: TodosListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                ToDoListViewModel::class.java
            )
        viewModel.getTodos().observe(this, Observer {

            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        rvMain.visibility = View.VISIBLE
                        pbMain.visibility = View.GONE
                        it.data?.let { todoList ->
                            updateAdapter(todoList)

                        }
                    }
                    Status.ERROR -> {
                        rvMain.visibility = View.VISIBLE
                        pbMain.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        pbMain.visibility = View.VISIBLE
                        rvMain.visibility = View.GONE
                    }
                }
            }
        })
        rvMain.layoutManager = LinearLayoutManager(this)


        adapter = TodosListAdapter(null, viewModel)
        rvMain.adapter = adapter

        val touchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder,
                    target: ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    adapter.delete(viewHolder.adapterPosition)

                }

            }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvMain)



        btnMainAdd.setOnClickListener {
            if (etMainNewToDo.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_empty_line), Toast.LENGTH_LONG).show()
            } else {
                var newElementData =
                    ToDoElementData(1, adapter.itemCount, etMainNewToDo.text.toString(), false)
                adapter.theTodoList!!.add(0, newElementData)
                viewModel.loadTodos(adapter.theTodoList!!)
                etMainNewToDo.text.clear()
                adapter.notifyItemInserted(0)
                rvMain.scrollToPosition(0)
            }
        }

    }

    private fun updateAdapter(todoList: List<ToDoElementData>) {
        adapter.apply { theTodoList = todoList.toMutableList() }
    }

}