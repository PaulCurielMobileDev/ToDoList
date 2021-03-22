package mexicandeveloper.com.todolist.views

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mexicandeveloper.com.todolist.R
import mexicandeveloper.com.todolist.models.ToDoElementData
import mexicandeveloper.com.todolist.viewmodels.ToDoListViewModel.ToDoListViewModel


class TodosListAdapter(
    var theTodoList: MutableList<ToDoElementData>?,
    val viewModel: ToDoListViewModel
) :
    RecyclerView.Adapter<TodosListAdapter.TodosViewHolder>() {


    inner class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRowTodoTitle = itemView.findViewById<TextView>(R.id.tvRowTodoTitle)
        val ivRowTodoCheckbox = itemView.findViewById<ImageView>(R.id.ivRowTodoCheckbox)

        init {
            ivRowTodoCheckbox.setOnClickListener {
                theTodoList?.get(adapterPosition)?.wasChecked = true
                theTodoList?.get(adapterPosition)?.completed =
                    !theTodoList?.get(adapterPosition)?.completed!!
                notifyItemChanged(adapterPosition)
                viewModel.loadTodos(theTodoList!!)
            }

            tvRowTodoTitle.setOnClickListener {

                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    tvRowTodoTitle.isSingleLine = !tvRowTodoTitle.isSingleLine
                } else {
                    // do something for phones running an SDK before lollipop
                }
            }
        }

        fun bind(data: ToDoElementData) {
            tvRowTodoTitle.text = data.title
            if (data.completed) {
                ivRowTodoCheckbox.setImageResource(android.R.drawable.checkbox_on_background)
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorGreen
                    )
                )
                tvRowTodoTitle.alpha = 1f
            } else {
                ivRowTodoCheckbox.setImageResource(android.R.drawable.checkbox_off_background)
                if (data.wasChecked) {
                    val content = SpannableString(data.title)
                    content.setSpan(UnderlineSpan(), 0, content.length, 0)
                    tvRowTodoTitle.setText(content)
                    tvRowTodoTitle.alpha = 0.5f
                }

                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorWhite
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.row_todolist, parent, false)
        return TodosViewHolder(root)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        holder.bind(theTodoList!!.get(position))
    }

    override fun getItemCount(): Int {
        return theTodoList?.size ?: 0
    }

    fun delete(adapterPosition: Int) {
        theTodoList!!.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        viewModel.loadTodos(theTodoList!!)
    }
}