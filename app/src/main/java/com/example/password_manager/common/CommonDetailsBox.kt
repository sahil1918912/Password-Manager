package com.example.password_manager.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun PreviewDetailsBox(){
   CommonDetailsBox(title = "Sahil", content = "Content")
}

@Preview(showBackground = true)
@Composable
fun PreviewPasswordDetailsBox(){
    PasswordDetailsBox(title = "Sahil", content = "Content")
}

@Composable
fun CommonDetailsBox(title: String, content: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = title,
                color = Color.DarkGray,
                fontSize = 13.5.sp,
                modifier = Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight.Light
            )
            Text(
                text = content,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}


@Composable
fun PasswordDetailsBox(title: String, content: String) {


    var showPassword by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = title,
                color = Color.DarkGray,
                fontSize = 13.5.sp,
                modifier = Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight.Light
            )
            if (showPassword){
                Text(
                    text = content,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }else{
                val textlength = "*".repeat(content.length)
                Text(
                    text = textlength,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
        if (title == "Password") {
            IconButton(
                onClick = { showPassword  =  !showPassword },
                modifier = Modifier.padding(top = 15.dp)
            ) {

                if (showPassword){
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "show",
                        tint = Color.DarkGray
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "show",
                        tint = Color.DarkGray
                    )
                }


            }

        }
    }

}