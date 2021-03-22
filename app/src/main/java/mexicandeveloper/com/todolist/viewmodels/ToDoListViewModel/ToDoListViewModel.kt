package mexicandeveloper.com.todolist.viewmodels.ToDoListViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mexicandeveloper.com.todolist.models.ToDoElementData
import mexicandeveloper.com.todolist.repository.MainRepository
import mexicandeveloper.com.todolist.utils.Resource
import java.lang.Exception

class ToDoListViewModel(val mainRepository: MainRepository) : ViewModel() {

    val toDoList: MutableLiveData<MutableList<ToDoElementData>> by lazy {
        MutableLiveData<MutableList<ToDoElementData>>()
    }

    fun getTodos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getTodoList()))
        }catch (e:Exception){
            emit(Resource.error(data = null,message = e.message ?: "Error ocurred"))
        }


    }

    fun loadTodos(data:MutableList<ToDoElementData>) {
        toDoList.value=data
    }

    fun updateVal(position: Int) {
        toDoList.value!!.get(position).completed = !toDoList.value!!.get(position).completed
    }

    fun getValRow(position: Int): ToDoElementData? {
        return toDoList.value?.get(position)
    }

}