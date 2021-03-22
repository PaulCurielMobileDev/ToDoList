package mexicandeveloper.com.todolist.api

class ApiHelper(val apiService: ApiTodoListService) {
    suspend fun getTodoList() = apiService.getTodos()
}