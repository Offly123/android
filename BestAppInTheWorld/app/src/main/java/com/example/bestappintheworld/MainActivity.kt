package com.example.bestappintheworld

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var editTextNumber: EditText? = null
    private var operationTextView: TextView? = null
    private var resultTextView: TextView? = null

    private var inputtedNumber: String = ""


    private var lastInputtedOperation: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        operationTextView = findViewById<TextView>(R.id.operationText)
        resultTextView = findViewById<TextView>(R.id.resultText)

        editTextNumber!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Синхронизируем переменную inputtedNumber с текстом из EditText
                inputtedNumber = s?.toString() ?: ""
            }
        })
    }

    fun handleNumberButtonClick(clickedButton: View) {
        val inputtedDigit = (clickedButton as Button).text.toString()

        if (inputtedNumber.length < 8) {
            if (inputtedDigit == "." && (inputtedNumber.length == 0 || inputtedNumber[inputtedNumber.length - 1] == '.')) {
                return
            }
            inputtedNumber += inputtedDigit;
        } else {
            Toast.makeText(this, "Бюджета хватило только на 4-байтовое число", Toast.LENGTH_SHORT).show()
            return
        }

        editTextNumber!!.setText(inputtedNumber.toString())
    }

    fun handleButtonACClick(meaninglessVariable: View) {
        editTextNumber!!.setText("")
        inputtedNumber = ""

        operationTextView!!.text = ""
        lastInputtedOperation = ""

        resultTextView!!.text = ""
    }

    // Худшая функция, когда-либо написанная человеком
    fun handleButtonOperationClick(clickedButton: View) {
        fun calculateNewNumber(oldNumber: Double, newNumber: Double, operation: String): String {
            var calculated: String = "0.0"
            when (operation) {
                "+" -> calculated = (oldNumber + newNumber).toString()
                "-" -> calculated = (oldNumber - newNumber).toString()
                "*" -> calculated = (oldNumber * newNumber).toString()
                "/" -> {
                    if (newNumber != 0.0)
                        calculated = (oldNumber / newNumber).toString()
                    else {
                        calculated = "Деление на 0"
                    }
                }
                "=" -> {
                    if (lastInputtedOperation == "=") {
                        lastInputtedOperation = ""
                        operationTextView!!.text = ""
                        return "0.0"
                    }
                    calculated = calculateNewNumber(oldNumber, newNumber, lastInputtedOperation)
                    lastInputtedOperation = ""
                    operationTextView!!.text = ""

                }
                else -> return "0.0"
            }
            return calculated
        }

        val inputtedOperation = (clickedButton as Button).text.toString()
        if (inputtedNumber == "") {
            lastInputtedOperation = inputtedOperation
            operationTextView!!.text = inputtedOperation
            return
        }

        operationTextView!!.text = inputtedOperation

        var calculatedNumber: String
        if (resultTextView!!.text.toString() == "") {
            calculatedNumber = inputtedNumber
        } else {
            calculatedNumber = resultTextView!!.text.toString()
            calculatedNumber = calculateNewNumber(calculatedNumber.toDouble(), inputtedNumber.toDouble(), inputtedOperation)
        }

        resultTextView!!.text = calculatedNumber.toString()
        lastInputtedOperation = inputtedOperation
        inputtedNumber = ""
        editTextNumber!!.setText("")
    }
}