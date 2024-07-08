package com.example.password_manager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.password_manager.common.CommonDetailsBox
import com.example.password_manager.common.CommonTextBox
import com.example.password_manager.common.CommonTextField
import com.example.password_manager.common.PasswordDetailsBox
import com.example.password_manager.common.PasswordTextField
import com.example.password_manager.data.PassEntity
import com.example.password_manager.ui.theme.DarkGrey
import com.example.password_manager.ui.theme.LightBlue
import java.security.SecureRandom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var addLayoutBtn by remember { mutableStateOf(false) }
    var detailsLayoutBtn by remember { mutableStateOf(false) }
    var alertDialog by remember { mutableStateOf(false) }
    var addAccountName by remember { mutableStateOf("") }
    var addUserName by remember { mutableStateOf("") }
    var addPassword by remember { mutableStateOf("") }

    val viewModel = hiltViewModel<MainViewModel>()
    val allPassword = viewModel.passwordList.collectAsState().value
    var currentPassword = remember { mutableStateOf(PassEntity("", "", "")) }
    var edtAddAccountName by remember { mutableStateOf("") }
    var edtAddUserName by remember { mutableStateOf("") }
    var edtAddPassword by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                containerColor = LightBlue,
                onClick = { addLayoutBtn = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Details",
                    modifier = Modifier.size(65.dp),
                    tint = Color.White
                )
            }
        },
        topBar = {
            TopAppBar(title = { Text(text = "Password Manager", fontWeight = FontWeight.Bold) })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(allPassword) { passwords ->
                CommonTextBox(passwords) {
                    currentPassword.value = it
                    detailsLayoutBtn = true
                    edtAddAccountName = viewModel.decrypt(currentPassword.value.accountName)
                    edtAddUserName = viewModel.decrypt(currentPassword.value.userName)
                    edtAddPassword = viewModel.decrypt(currentPassword.value.password)
                }
            }
        }

        if (viewModel.loading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1F)
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }

        if (addLayoutBtn) {
            ModalBottomSheet(onDismissRequest = {
                addLayoutBtn = false
                addPassword = ""
                addAccountName = ""
                addUserName = ""
            }) {
                CommonTextField(
                    inputText = addAccountName,
                    "Account Type",
                    onChange = { addAccountName = it }
                )
                CommonTextField(
                    inputText = addUserName,
                    "UserName/ Email",
                    onChange = { addUserName = it }
                )
                PasswordTextField(
                    inputText = addPassword,
                    "Password",
                    onChange = { addPassword = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PasswordStrengthMeter(password = addPassword)
                    Text(
                        text = "Generate",
                        color = LightBlue,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                addPassword = generatePassword()
                            },
                        textAlign = TextAlign.End,
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = {
                        val encryptedAccountName = viewModel.encrypt(addAccountName)
                        val encryptedUserName = viewModel.encrypt(addUserName)
                        val encryptedPassword = viewModel.encrypt(addPassword)
                        viewModel.AddPassword(
                            PassEntity(
                                encryptedAccountName,
                                encryptedUserName,
                                encryptedPassword
                            )
                        )
                        addPassword = ""
                        addAccountName = ""
                        addUserName = ""
                        addLayoutBtn = false
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .padding(bottom = 35.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)
                ) {
                    Text(
                        text = "Add New Account",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }
            }
        }

        if (detailsLayoutBtn) {
            ModalBottomSheet(onDismissRequest = { detailsLayoutBtn = false }) {
                val decryptedAccountName = viewModel.decrypt(currentPassword.value.accountName)
                val decryptedUserName = viewModel.decrypt(currentPassword.value.userName)
                val decryptedPassword = viewModel.decrypt(currentPassword.value.password)

                Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                    Text(
                        text = "Account Details",
                        fontSize = 24.sp,
                        color = LightBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    CommonDetailsBox("Account Type", decryptedAccountName)
                    CommonDetailsBox("Username/Email", decryptedUserName)
                    PasswordDetailsBox("Password", decryptedPassword)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { alertDialog = true },
                        modifier = Modifier
                            .padding(bottom = 35.dp)
                            .height(50.dp)
                            .width(170.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)
                    ) {
                        Text(
                            text = "Edit",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.Delete(
                                PassEntity(
                                    id = currentPassword.value.id,
                                    accountName = viewModel.decrypt(currentPassword.value.accountName),
                                    userName = viewModel.decrypt(currentPassword.value.userName),
                                    password = viewModel.decrypt(currentPassword.value.password)
                                )
                            )
                            detailsLayoutBtn = false
                        },
                        modifier = Modifier
                            .padding(bottom = 35.dp)
                            .width(170.dp)
                            .height(50.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(
                            text = "Delete",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        if (alertDialog) {
            AlertDialog(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5),
                onDismissRequest = {
                    alertDialog = false
                    edtAddPassword = ""
                    edtAddAccountName = ""
                    edtAddUserName = ""
                },
                text = {
                    Column {
                        Text(
                            text = "Account Details",
                            fontSize = 20.sp,
                            color = LightBlue,
                            fontWeight = FontWeight.ExtraBold
                        )
                        CommonTextField(
                            inputText = edtAddAccountName,
                            text = "AccountType",
                            onChange = { edtAddAccountName = it }
                        )
                        CommonTextField(
                            inputText = edtAddUserName,
                            text = "UserName/Email",
                            onChange = { edtAddUserName = it }
                        )
                        PasswordTextField(
                            inputText = edtAddPassword,
                            text = "Password",
                            onChange = { edtAddPassword = it }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PasswordStrengthMeter(password = addPassword)
                            Text(
                                text = "Generate",
                                color = LightBlue,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        addPassword = generatePassword()
                                    },
                                textAlign = TextAlign.End,
                                fontSize = 16.sp
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { alertDialog = false }) {
                        Text(text = "Cancel")
                    }
                    TextButton(
                        onClick = {
                            alertDialog = false
                            viewModel.Update(
                                PassEntity(
                                    id = currentPassword.value.id,
                                    accountName = viewModel.encrypt(edtAddAccountName),
                                    userName = viewModel.encrypt(edtAddUserName),
                                    password = viewModel.encrypt(edtAddPassword)
                                )
                            )
                            detailsLayoutBtn = false
                        },
                    ) {
                        Text(text = "Confirm")
                    }
                }
            )
        }
    }
}



@Composable
fun PasswordStrengthMeter(password: String) {
    val strength = evaluatePasswordStrength(password)
    val color = when (strength) {
        PasswordStrength.WEAK -> Color.Red
        PasswordStrength.MEDIUM -> Color.Yellow
        PasswordStrength.STRONG -> Color.Green
    }
    Text(
        text = when (strength) {
            PasswordStrength.WEAK -> "Password = WEAK"
            PasswordStrength.MEDIUM -> "Password = MEDIUM"
            PasswordStrength.STRONG -> "Password = STRONG"
        },
        color = color,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )
}

enum class PasswordStrength {
    WEAK, MEDIUM, STRONG
}

fun evaluatePasswordStrength(password: String): PasswordStrength {
    return when {
        password.length < 6 -> PasswordStrength.WEAK
        password.length < 10 -> PasswordStrength.MEDIUM
        else -> PasswordStrength.STRONG
    }
}

fun generatePassword(length: Int = 12): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()"
    val secureRandom = SecureRandom()
    return (1..length)
        .map { chars[secureRandom.nextInt(chars.length)] }
        .joinToString("")
}
