package com.example.sarah_malik_tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sarah_malik_tasks.ui.theme.Sarah_malik_tasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sarah_malik_tasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TaskList(modifier: Modifier = Modifier) {
    val tasks = remember { mutableStateListOf<Task>() }
    var newTaskText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = newTaskText,
            onValueChange = { newTaskText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.padding(8.dp)) {
                    if (newTaskText.text.isEmpty()) {
                        Text(text = "Enter new task...")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTaskText.text.isNotBlank()) {
                    tasks.add(Task(newTaskText.text, false))
                    newTaskText = TextFieldValue("")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                tasks.removeAll { it.isCompleted }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Completed")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            tasks.forEachIndexed { index, task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = { isChecked ->
                            tasks[index] = tasks[index].copy(isCompleted = isChecked)
                        }
                    )
                    Text(
                        text = task.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

data class Task(
    val name: String,
    val isCompleted: Boolean
)

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    Sarah_malik_tasksTheme {
        TaskList()
    }
}