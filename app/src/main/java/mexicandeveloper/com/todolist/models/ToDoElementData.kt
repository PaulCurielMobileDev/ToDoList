package mexicandeveloper.com.todolist.models

data class ToDoElementData(
    var userId: Int,
    var id: Int,
    var title: String,
    var completed: Boolean,
    var wasChecked: Boolean = false
)