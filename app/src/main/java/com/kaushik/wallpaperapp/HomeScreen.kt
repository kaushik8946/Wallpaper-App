package com.kaushik.wallpaperapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val input = rememberSaveable { mutableStateOf("") }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current!!
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = input.value,
            onValueChange = { input.value = it },
            label = { Text(text = "Enter category:") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.height(60.dp),
            singleLine = true
        )
        Button(onClick = {
            if (input.value.isEmpty()) {
                Toast.makeText(context, "enter text", Toast.LENGTH_SHORT).show()
            } else {
                keyboardController.hide()
                isLoading.value = true
                val isSuccess = validate(context, input.value)
                if (isSuccess) {
                    Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate("images")
                } else
                    Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show()
                isLoading.value = false
            }
        }) {
            Text(text = "Search")
        }
    }
}


@OptIn(DelicateCoroutinesApi::class)
fun validate(context: Context, input: String): Boolean {
    var response = ""
    var isFinish = false
    GlobalScope.launch {
        response = API.getResponse(input).toString()
        isFinish = true
    }
    while (!isFinish);
    val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("images", response).commit()
    sharedPreferences.edit().putString("category", input).commit()
    return true
}