package mexicandeveloper.com.todolist.api

import mexicandeveloper.com.todolist.models.ToDoElementData
import retrofit2.http.GET

interface ApiTodoListService {
    @GET("todos?userId=1")
    suspend fun getTodos() : List<ToDoElementData>
}