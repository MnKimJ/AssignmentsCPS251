package com.example.assignment6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat
            .getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        setContent {
            MaterialTheme {
                Surface {
                    LoginApp()
                }
            }
        }
    }
}

@Composable
fun LoginApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { userName ->
                    navController.navigate("welcome/$userName")
                }
            )
        }

        composable(
            route = "welcome/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val maybeName = backStackEntry.arguments?.getString("userName")
            val userName =
                if (maybeName != null) maybeName
                else ""

            WelcomeScreen(
                userName = userName,
                onViewProfile = { navController.navigate("profile/$userName") },
                onLogout = {
                    navController.popBackStack(
                        route = "login",
                        inclusive = false
                    )
                }
            )
        }

        composable(
            route = "profile/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val maybeName = backStackEntry.arguments?.getString("userName")
            val userName = if (maybeName != null) maybeName else ""

            ProfileScreen(
                userName = userName,
                onBackToWelcome = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var emailIsCorrectByValue by remember { mutableStateOf(true) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    val validEmail = "student@wccnet.edu"
    val validPassword = "password123"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Student Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (nameError != null) {
                            nameError = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    isError = nameError != null,
                    supportingText = {
                        if (nameError != null) {
                            Text(nameError!!)
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it

                        if (email.isEmpty()) {
                            emailError = null
                        } else {
                            if (isValidEmail(email)) {
                                emailError = null
                            } else {
                                emailError = "Please enter a valid email"
                            }
                        }

                        emailIsCorrectByValue = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    isError = (emailError != null) || !emailIsCorrectByValue,
                    supportingText = {
                        if (emailError != null) {
                            Text(emailError!!)
                        } else {
                            if (!emailIsCorrectByValue) {
                                Text("Please enter the correct email")
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (passwordError != null) {
                            passwordError = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                if (passwordVisible) {
                                    passwordVisible = false
                                } else {
                                    passwordVisible = true
                                }
                            }
                        ) {
                            if (passwordVisible) {
                                Text("Hide")
                            } else {
                                Text("Show")
                            }
                        }
                    },
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    isError = passwordError != null,
                    supportingText = {
                        if (passwordError != null) {
                            Text(passwordError!!)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        var hasError = false

                        if (name.isBlank()) {
                            nameError = "Please enter your name"
                            hasError = true
                        } else {
                            nameError = null
                        }

                        if (email.isBlank()) {
                            emailError = "Email is required"
                            emailIsCorrectByValue = true
                            hasError = true
                        } else {
                            if (!isValidEmail(email)) {
                                emailError = "Please enter a valid email"
                                emailIsCorrectByValue = true
                                hasError = true
                            } else {
                                emailError = null
                                if (email == validEmail) {
                                    emailIsCorrectByValue = true
                                } else {
                                    emailIsCorrectByValue = false
                                    hasError = true
                                }
                            }
                        }

                        if (password.isBlank()) {
                            passwordError = "Password is required"
                            hasError = true
                        } else {
                            if (password == validPassword) {
                                passwordError = null
                            } else {
                                passwordError = "Incorrect password"
                                hasError = true
                            }
                        }

                        if (!hasError) {
                            val trimmed = name.trim()
                            val nextName = if (trimmed.isNotEmpty()) trimmed else ""
                            onLoginSuccess(nextName)
                        }
                    }
                ) {
                    Text("Log In")
                }

                Text(
                    text = "Demo: $validEmail / $validPassword",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(userName: String, onViewProfile: () -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Welcome!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Hello, $userName!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onViewProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Profile")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun ProfileScreen(userName: String, onBackToWelcome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "User Profile",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileRow("Name", userName)
                ProfileRow("Email", "student@wccnet.edu")
                ProfileRow("Student ID", "2025001")
                ProfileRow("Major", "Computer Science")
                ProfileRow("Year", "Freshman")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBackToWelcome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Welcome")
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun isValidEmail(email: String): Boolean {
    val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    return regex.matches(email)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            userName = "John Doe",
            onViewProfile = {},
            onLogout = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            userName = "John Doe",
            onBackToWelcome = {}
        )
    }
}
