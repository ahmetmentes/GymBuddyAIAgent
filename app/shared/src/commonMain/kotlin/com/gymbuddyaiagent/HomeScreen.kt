package com.gymbuddyaiagent

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigateToChat: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor1 by infiniteTransition.animateColor(
        initialValue = Color(0xFF4CAF50), // Green
        targetValue = Color(0xFF2196F3), // Blue
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animatedColor2 by infiniteTransition.animateColor(
        initialValue = Color(0xFF2196F3), // Blue
        targetValue = Color(0xFF9C27B0), // Purple
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(animatedColor1, animatedColor2)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Welcome to Gym Buddy AI Agent!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                textAlign = TextAlign.Center, // Added for horizontal centering
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Text(
                text = "Your personalized fitness companion is here to help you achieve your goals.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center, // Added for horizontal centering
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            )

            // Placeholder for summary of latest status
            Text(
                text = "Summary of your last session: You completed a full-body workout focusing on strength training. Keep up the great work!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center, // Added for horizontal centering
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            )

            Button(onClick = onNavigateToChat) {
                Text("Start Chatting")
            }
        }
    }
}