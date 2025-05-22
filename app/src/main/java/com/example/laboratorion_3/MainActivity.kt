package com.example.laboratorion_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessingGame()
        }
    }
}

@Composable
fun GuessingGame() {
    var guess by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableStateOf(3) }
    var numberToGuess by remember { mutableStateOf(Random.nextInt(0, 101)) }
    var gameOver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "¡Bienvenido al juego de adivinanzas!",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tendrás 3 intentos para adivinar un número del 0 al 100. Te daré pistas si el número es mayor o menor.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

        if (!gameOver) {
            OutlinedTextField(
                value = guess,
                onValueChange = { guess = it },
                label = { Text("Tu intento") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = result,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val userGuess = guess.toIntOrNull()
                if (userGuess == null || userGuess !in 0..100) {
                    result = "Por favor, introduce un número válido entre 0 y 100."
                } else {
                    attemptsLeft--
                    when {
                        userGuess == numberToGuess -> {
                            result = "¡Correcto! Adivinaste el número."
                            gameOver = true
                        }
                        attemptsLeft > 0 -> {
                            val hint = if (userGuess < numberToGuess) "mayor" else "menor"
                            result = "Incorrecto. El número es $hint. Te quedan $attemptsLeft intentos."
                        }
                        else -> {
                            result = "Perdiste. El número era $numberToGuess."
                            gameOver = true
                        }
                    }
                }
                guess = ""
            }) {
                Text("Intentar")
            }
        } else {
            Text(
                text = result,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                numberToGuess = Random.nextInt(0, 101)
                attemptsLeft = 3
                guess = ""
                result = ""
                gameOver = false
            }) {
                Text("Reiniciar juego")
            }
        }
    }
}
