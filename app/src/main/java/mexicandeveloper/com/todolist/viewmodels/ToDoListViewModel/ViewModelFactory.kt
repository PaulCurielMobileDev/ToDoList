package mexicandeveloper.com.todolist.viewmodels.ToDoListViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mexicandeveloper.com.todolist.api.ApiHelper
import mexicandeveloper.com.todolist.repository.MainRepository

class ViewModelFactory(val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            return ToDoListViewModel(MainRepository(apiHelper)) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}