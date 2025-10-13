package com.example.Assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This project covers concepts from Chapter 7 lessons:
 * - "Validation" - for form validation and error handling
 * - "Managing Input State" - for state management in forms
 * - "Text Fields" - for input field styling and error states
 * - "Regular Expressions" - for email, phone, and ZIP code validation
 *
 * Students should review these lessons before starting:
 * - Validation lesson for form validation patterns
 * - Managing Input State lesson for state management
 * - Text Fields lesson for input field styling
 * - Regular Expressions lesson for validation patterns
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactValidatorApp()
            }
        }
    }
}

@Composable
fun ContactValidatorApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ContactForm()
    }
}

@Composable
fun ContactForm() {
    
    // TODO: Create state variables for form fields
    // Hint: You need variables for:
    // - name (string for user's name)
    // - email (string for email address)
    // - phone (string for phone number)
    // - zipCode (string for ZIP code)
    // Use remember and mutableStateOf for each
    // See "Validation" lesson for examples of state management

    // TODO: Create validation state variables
    // Hint: You need boolean variables for:
    // - isNameValid, isEmailValid, isPhoneValid, isZipValid
    // Use remember and mutableStateOf for each
    // See "Managing Input State" lesson for examples of validation state management

    // TODO: Create submitted information state variable
    // Hint: You need a variable for: submittedInfo (string for displaying submitted data)
    // Use remember and mutableStateOf

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                // TODO: Apply a RoundedCornerShape with a reasonable dp value
                // See "Text Fields" lesson for examples of shape customization
            )
            // TODO: Add horizontal and vertical padding with a reasonable dp value
            // See "Text Fields" lesson for examples of padding
            .padding(
                horizontal = /* TODO: Provide a reasonable value for horizontal padding */ .dp,
        vertical = /* TODO: Provide a reasonable value for vertical padding */ .dp
    ),
    // TODO: Arrange items vertically with a reasonable dp spacing
    // See "Text Fields" lesson for examples of vertical arrangement
    verticalArrangement = Arrangement.spacedBy(/* TODO: Provide a reasonable value for vertical spacing */ .dp)
    ) {
        // TODO: Call the NameField composable here
        // Pass the name state, validation state, and onValueChange lambda
        // See "Text Fields" lesson for examples of error state styling

        // TODO: Call the EmailField composable here
        // Pass the email state, validation state, and onValueChange lambda
        // See "Validation" lesson for email validation examples

        // TODO: Call the PhoneField composable here
        // Pass the phone state, validation state, and onValueChange lambda
        // See "Regular Expressions" lesson for phone number validation patterns

        // TODO: Call the ZipCodeField composable here
        // Pass the zipCode state, validation state, and onValueChange lambda
        // See "Regular Expressions" lesson for ZIP code validation examples

        // TODO: Create the Submit button
        // Use Button composable with enabled state based on all validations
        // The button should only be enabled when all fields are valid and not empty
        // See "Validation" lesson for examples of complex button state management

        // TODO: Add display for submitted information
    }
}

@Composable
fun NameField(
    name: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the name input field
    //
    // See "Text Fields" lesson for complete examples of error state styling
}

@Composable
fun EmailField(
    email: String,
    isEmailValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the email input field
    //
    // See "Validation" lesson for email validation examples with regex
}

@Composable
fun PhoneField(
    phone: String,
    isPhoneValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the phone input field
    // Use OutlinedTextField with:
    //
    // See "Regular Expressions" lesson for phone number validation patterns and examples
}

@Composable
fun ZipCodeField(
    zipCode: String,
    isZipValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the ZIP code input field
    // Use OutlinedTextField with:
    //
    // See "Regular Expressions" lesson for ZIP code validation examples
}

// TODO: Create validation functions using regex
// Hint: You need three functions:
// 1. validateEmail() - checks email format using regex pattern
//
// 2. validatePhone() - checks for phone numbers like 123-456-7890 or 123/456/7890
//
// 3. validateZipCode() - checks for exactly 5 digits
//
// Use the .matches() function with regex patterns
// See "Regular Expressions" lesson for complete regex examples and validation patterns

// You will need to enable the submit button when all the fields are valid:
//
// See "Validation" lesson for detailed examples of complex button state management
//when button is clicked and all fields are valid and not empty, the submitted information should be displayed
//in a text field below the button.

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactValidatorAppPreview() {
    ContactValidatorApp()
}