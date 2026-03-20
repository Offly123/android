package com.example.hehenewproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hehenewproject.ui.theme.HeheNewProjectTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // MaterialTheme {
            HeheNewProjectTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun OperationButton (
    operation: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button (
        onClick = onClick,
        modifier = Modifier.size(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Blue else Color.Gray
        )
    ) {
        Text(
            text = operation,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@Composable
fun CalculatorApp() {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun calculate() {
        if (firstNumber.isEmpty() || secondNumber.isEmpty()) {
            Toast.makeText(context, "Введите оба числа!", Toast.LENGTH_SHORT).show()
            return
        }

        if (operation.isEmpty()) {
            Toast.makeText(context, "Выберите операцию!", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = firstNumber.toDoubleOrNull()
        val num2 = secondNumber.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            Toast.makeText(context, "Введите корректные числа!", Toast.LENGTH_SHORT).show()
            return
        }

        if (operation == "/" && num2 == 0.0) {
            Toast.makeText(context, "Делить на ноль нельзя!", Toast.LENGTH_SHORT).show()
            return
        }

        val calculationResult = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> 0.0
        }

        result = calculationResult.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Простой калькулятор",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = firstNumber,
            onValueChange = { firstNumber = it} ,
            label = { Text("Первое число") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = secondNumber,
            onValueChange = { secondNumber = it} ,
            label = { Text("Второе число") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Выберите операцию", modifier = Modifier.padding(bottom = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OperationButton(
                operation = "+",
                isSelected = operation == "+",
                onClick = { operation = "+" }
            )

            OperationButton(
                operation = "-",
                isSelected = operation == "-",
                onClick = { operation = "-" }
            )

            OperationButton(
                operation = "*",
                isSelected = operation == "*",
                onClick = { operation = "*" }
            )

            OperationButton(
                operation = "/",
                isSelected = operation == "/",
                onClick = { operation = "/" }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { calculate() },
            modifier = Modifier
                .fillMaxSize()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Посчитать", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text(
                    text = "Результат: $result",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}