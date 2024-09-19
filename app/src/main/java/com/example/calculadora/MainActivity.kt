package com.example.calculadora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var panel1: TextView
    private lateinit var panel2: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Edge-to-Edge window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views
        panel1 = findViewById(R.id.panel1)
        panel2 = findViewById(R.id.panel2)

        // Set up number and operator buttons
        setupNumberButtons()
        setupOperatorButtons()

        // Clear and equal button handlers
        findViewById<Button>(R.id.borrar).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.igual).setOnClickListener { calculateResult() }
    }

    // Setup number buttons
    private fun setupNumberButtons() {
        val numberIds = listOf(R.id.cero, R.id.uno, R.id.dos, R.id.tres, R.id.cuatro, R.id.cinco, R.id.seis, R.id.siete, R.id.ocho, R.id.nueve, R.id.punto)
        for (id in numberIds) {
            findViewById<Button>(id).setOnClickListener {
                onNumberClick(it as Button)
            }
        }
    }

    // Setup operator buttons
    private fun setupOperatorButtons() {
        val operatorIds = listOf(R.id.suma, R.id.resta, R.id.multiplicacion, R.id.division)
        for (id in operatorIds) {
            findViewById<Button>(id).setOnClickListener {
                onOperatorClick(it as Button)
            }
        }
    }

    // Handle number clicks
    private fun onNumberClick(button: Button) {
        currentInput += button.text.toString()
        panel2.text = currentInput
    }

    // Handle operator clicks
    private fun onOperatorClick(button: Button) {
        operator = button.text.toString()
        firstValue = currentInput.toDoubleOrNull() ?: 0.0
        currentInput = ""
        panel1.text = "$firstValue $operator"
    }

    // Handle the equal button click
    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondValue = currentInput.toDoubleOrNull() ?: 0.0
            val result = when (operator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "*" -> firstValue * secondValue
                "/" -> if (secondValue != 0.0) firstValue / secondValue else Double.NaN
                else -> 0.0
            }
            panel1.text = "$firstValue $operator $secondValue ="
            panel2.text = if (result.isNaN()) "Error" else result.toString()
            currentInput = result.toString()
        }
    }

    // Clear the calculator
    private fun clearAll() {
        currentInput = ""
        operator = ""
        firstValue = 0.0
        panel1.text = ""
        panel2.text = "0"
    }
}
