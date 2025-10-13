package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.saveable.rememberSaveable


/**
 * This project covers concepts from Chapter 8 lessons:
 * - "Lazy Column" - for creating scrollable contact lists
 * - "Handling Clicks and Selection" - for interactive contact selection
 * - "Combining LazyColumn and LazyRow" - for understanding list composition
 *
 * Students should review these lessons before starting:
 * - LazyColumn lesson for list implementation
 * - Clicks and Selection lesson for interactive behavior
 * - Combined lesson for understanding how lists work together
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactListApp()
                }
            }
        }
    }
}

@Composable
fun ContactListApp() {
    val contacts = listOf(
        Contact("Matthew Kim", "matthewkim@example.com", "555-0101"),
        Contact("Bob Smith", "bobsmith@example.com", "555-0102"),
        Contact("Carla Gomez", "carlagomez@example.com", "555-0103"),
        Contact("David Lee", "davidlee@example.com", "555-0104"),
        Contact("Ella Brown", "ellabrown@example.com", "555-0105"),
        Contact("Frank Turner", "frankturner@example.com", "555-0106"),
        Contact("Grace Kim", "gracekim@example.com", "555-0107"),
        Contact("Henry Miller", "henrymiller@example.com", "555-0108"),
        Contact("Isla Patel", "islapatel@example.com", "555-0109"),
        Contact("Jack Wilson", "jackwilson@example.com", "555-0110"),
        Contact("Kara Nguyen", "karanguyen@example.com", "555-0111"),
        Contact("Liam Carter", "liamcarter@example.com", "555-0112"),
        Contact("Maya Rivera", "mayarivera@example.com", "555-0113"),
        Contact("Noah Brooks", "noahbrooks@example.com", "555-0114"),
        Contact("Olivia Davis", "oliviadavis@example.com", "555-0115"),
        Contact("Paul Edwards", "pauledwards@example.com", "555-0116"),
        Contact("Quinn Foster", "quinnfoster@example.com", "555-0117"),
        Contact("Rosa Hernandez", "rosahernandez@example.com", "555-0118"),
        Contact("Samir Iqbal", "samiriqbal@example.com", "555-0119"),
        Contact("Tara Johnson", "tarajohnson@example.com", "555-0120"),
        Contact("Uma Kapoor", "umakapoor@example.com", "555-0121"),
        Contact("Victor Lopez", "victorlopez@example.com", "555-0122"),
        Contact("Wendy Moore", "wendymoore@example.com", "555-0123"),
        Contact("Xander Nash", "xandernash@example.com", "555-0124"),
        Contact("Yara Ortiz", "yaraortiz@example.com", "555-0125")
        // Asked AI to create this block of code with random names to make it look better
    )


    ContactList(contacts = contacts)
}

@Composable
fun ContactList(contacts: List<Contact>) {

    var selectedContact by rememberSaveable { mutableStateOf<Contact?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {

        Text(
            text = "Contact List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = if (selectedContact != null)
                "Selected: ${selectedContact!!.name}"
            else
                "No contact selected",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(contacts) { contact ->
                ContactItem(
                    contact = contact,
                    isSelected = contact == selectedContact,
                    onClick = { selectedContact = contact }
                )
            }
        }

        Button(
            onClick = { selectedContact = null },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ){
            Text("Clear Selection")
        }
    }
}


@Composable
fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            Color(0xFF625B71),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                val initials = getInitials(contact.name)
                Text(
                    text = initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    val first = parts[0][0].uppercase()
    val second = parts[1][0].uppercase()
    return first + second
}


data class Contact(
    val name: String,
    val email: String,
    val phone: String
)


/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactListAppPreview() {
    ContactListApp()
}
