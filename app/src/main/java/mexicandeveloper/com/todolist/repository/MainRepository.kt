package mexicandeveloper.com.todolist.repository

import mexicandeveloper.com.todolist.api.ApiHelper

class MainRepository(val apiHelper: ApiHelper) {
    suspend fun getTodoList() = apiHelper.getTodoList()
}