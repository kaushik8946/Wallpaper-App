package com.kaushik.wallpaperapp

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageScreen(navController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val responseString = sharedPreferences.getString("images", "").toString()
        val category = sharedPreferences.getString("category", null).toString()
        val responseJson = jsonRecursive(responseString)
        var imagesString = responseJson["hits"].toString()
        imagesString = imagesString.substring(2, imagesString.length - 2)
        val imagesMapList = parseImageString(imagesString)
        Log.d("data", imagesMapList[0].toString())
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Results for \"$category\"",
                modifier = Modifier.align(Alignment.CenterStart),
                fontSize = 20.sp
            )
            Card(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd),
                backgroundColor = Color(0xFF1A5092),
                shape = RoundedCornerShape(10.dp),
                elevation = 8.dp
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                var i = 0
                while (i < imagesMapList.size) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        var imageData = imagesMapList[i]
                        var imageUrl = imageData[" largeImageURL"].toString()
                        i++
                        Card(
                            onClick = {
                                sharedPreferences.edit()
                                    .putString("imageUrl", imageUrl)
                                    .commit()
                                navController.navigate("set_wallpaper")
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(.5f)
                                .height(200.dp),
                            shape = RoundedCornerShape(20.dp),
                            elevation = 15.dp
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.placeholder)
                            )
                        }
                        if (i < imagesMapList.size) {
                            imageData = imagesMapList[i]
                            imageUrl = imageData[" largeImageURL"].toString()
                            i++
                            Card(
                                onClick = {
                                    sharedPreferences.edit().putString(
                                        "imageUrl",
                                        imageUrl
                                    ).commit()
                                    navController.navigate("set_wallpaper")
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .height(200.dp),
                                shape = RoundedCornerShape(20.dp),
                                elevation = 15.dp
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(id = R.drawable.placeholder)
                                )
}
                        }
                    }
                }
            }
        }
    }
}
