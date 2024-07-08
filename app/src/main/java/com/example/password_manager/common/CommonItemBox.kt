package com.example.password_manager.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.password_manager.data.PassEntity
import com.example.password_manager.screens.MainViewModel
import com.example.password_manager.ui.theme.LightBlack

@Preview(showBackground = true)
@Composable
fun Preview() {

}

@Composable
fun CommonTextBox(account: PassEntity, onClick: (PassEntity) -> Unit) {


    val viewModel = hiltViewModel<MainViewModel>()
    val decryptedAccountName = viewModel.decrypt(account.accountName)
//    val decryptedUserName = viewModel.decrypt(account.userName)
    val decryptedPassword = viewModel.decrypt(account.password)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .shadow(
                elevation = 3.dp,
                ambientColor = Color.Gray,
                shape = RoundedCornerShape(50),
                spotColor = Color.Blue
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        content = {

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 3.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .wrapContentSize(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = decryptedAccountName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = LightBlack
                    )
                    Spacer(modifier = Modifier.padding(start = 25.dp))
                    val textLength = "*".repeat(decryptedPassword.length)
                    Text(
                        modifier = Modifier.padding(top = 7.dp),
                        text = textLength,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 24.sp
                    )
                }
                Box(
                    modifier = Modifier.padding(end = 1.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(onClick = { onClick(account) }) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "open",
                            tint = Color.Black
                        )

                    }
                }

            }
        }
    )

}