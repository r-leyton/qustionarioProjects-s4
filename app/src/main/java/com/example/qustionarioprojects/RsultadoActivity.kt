package com.example.qustionarioprojects

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RsultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rsultado)
        val txtResultado = findViewById<TextView>(R.id.txtResultadoFinal)
        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)

        val puntaje = intent.getIntExtra("PUNTAJE_FINAL", 0)
        val tiempoRestante = intent.getIntExtra("TIEMPO_RESTANTE", 0)
        val puntajeTotal = puntaje * 10 + tiempoRestante

        txtResultado.text = "Tu puntaje final es: $puntajeTotal\n\n" +
                "✔️ Respuestas correctas: $puntaje\n" +
                "⏱️ Tiempo restante: $tiempoRestante s"

        btnReiniciar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}