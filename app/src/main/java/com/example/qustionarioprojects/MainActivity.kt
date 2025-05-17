package com.example.qustionarioprojects

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var txtPregunta: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnResponder: Button
    private lateinit var txtPuntaje: TextView
    private lateinit var txtTemporizador: TextView
    private lateinit var barraTiempo: ProgressBar

    private lateinit var temporizador: CountDownTimer
    private var segundosRestantes = 120
    private var puntaje = 0
    private var preguntaActual = 0

    private val preguntas = listOf(
        "¿Qué mecanismo utiliza Android para liberar memoria cuando está bajo presión?" to listOf("Compilación Ahead-of-Time", "Garbage Collection", "Low Memory Killer (LMK)", "FragmentManager"),
        "¿Cuál de los siguientes no es un componente básico del framework de aplicaciones Android?" to listOf("Activities", "Services", "Broadcast Receivers", "ListActivity"),
        "¿Qué clase base es utilizada por todas las Views en Android?" to listOf("android.view.View", "android.widget.Widget", "android.view.ViewGroup", "android.content.Context"),
        "¿Cuál es el propósito de un Broadcast Receiver?" to listOf("Ejecutar tareas en segundo plano", "Responder a eventos o intents de difusión", "Proporcionar interfaz de usuario", "Gestionar bases de datos"),
        "¿Qué propiedad XML controla la alineación dentro de un LinearLayout?" to listOf("android:layout_weight", "android:gravity", "android:layout_gravity", "android:orientation"),
        "¿Cuál de estas librerías facilita la inyección de dependencias en aplicaciones Android?" to listOf("Gson", "Timber", "Dagger/Hilt", "Picasso"),
        "¿Qué componente Android se utiliza para mostrar grandes conjuntos de datos en listas o cuadrículas con alto rendimiento?" to listOf("ListView", "RecyclerView", "GridLayout", "LinearLayout"),
        "¿Qué método del ciclo de vida de un Fragment se llama cuando la vista del Fragment ha sido destruida?" to listOf("onDestroy()", "onDestroyView()", "onDetach()", "onStop()"),
        "¿Cuál es el propósito principal del FrameLayout?" to listOf("Organizar vistas en una cuadrícula", "Apilar vistas una encima de otra", "Organizar vistas en línea horizontal o vertical", "Permitir desplazamiento vertical"),
        "¿Qué componente se encarga de administrar la instalación, desinstalación e información de aplicaciones en Android?" to listOf("Application Framework", "Package Manager", "Content Provider", "Broadcast Receiver"),
        "¿Qué patrón de diseño es utilizado para optimizar el reciclaje de Views en RecyclerView?" to listOf("ViewHolder", "Singleton", "Observer", "Factory"),
        "¿Qué clase de adaptador permite mostrar datos de un Cursor en ListView?" to listOf("ArrayAdapter", "SimpleAdapter", "SimpleCursorAdapter", "BaseAdapter"),
        "¿Qué característica tiene un Bound Service?" to listOf("Ejecuta una tarea larga y se detiene solo", "Se enlaza a componentes para comunicación directa", "No puede ejecutar en segundo plano", "Siempre es visible al usuario"),
        "¿Qué es un Intent en Android?" to listOf("Una librería para animaciones", "Un mecanismo para comunicación asíncrona entre componentes", "Un tipo de layout", "Una herramienta para pruebas unitarias"),
        "¿Qué propiedad XML de un View se utiliza para asignarle un identificador único?" to listOf("android:id", "android:name", "android:tag", "android:layout_id"),
        "¿Qué librería se recomienda para realizar solicitudes HTTP seguras y eficientes en Android?" to listOf("Volley", "Retrofit", "Glide", "Gson"),
        "¿Qué clase de View permite al usuario ingresar y editar texto?" to listOf("TextView", "EditText", "Button", "CheckBox"),
        "¿Qué clase permite agrupar múltiples Views y controlar su disposición?" to listOf("View", "ViewGroup", "Widget", "Intent"),
        "¿Qué método se usa para añadir un Fragment dinámicamente a una Activity?" to listOf("addFragment()", "add() del FragmentManager", "inflate()", "replaceFragment()"),
        "¿Cuál es el propósito del archivo AndroidManifest.xml?" to listOf("Definir la interfaz gráfica", "Declarar permisos, actividades y componentes de la aplicación", "Gestionar recursos multimedia", "Configurar el entorno de desarrollo")
    )

    private val respuestasCorrectas = listOf(
        2, // LMK
        3, // ListActivity
        0, // android.view.View
        1, // Responder a eventos
        1, // android:gravity
        2, // Dagger/Hilt
        1, // RecyclerView
        1, // onDestroyView
        1, // Apilar vistas
        1, // Package Manager
        0, // ViewHolder
        2, // SimpleCursorAdapter
        1, // Se enlaza a componentes
        1, // Comunicación asincrónica
        0, // android:id
        1, // Retrofit
        1, // EditText
        1, // ViewGroup
        1, // add() del FragmentManager
        1  // Declarar permisos...
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        txtPregunta = findViewById(R.id.txtPregunta)
        radioGroup = findViewById(R.id.radioGroup)
        btnResponder = findViewById(R.id.btnResponder)
        txtPuntaje = findViewById(R.id.txtPuntaje)
        txtTemporizador = findViewById(R.id.txtTemporizador)
        barraTiempo = findViewById(R.id.barraTiempo)

        barraTiempo.max = 120
        iniciarTemporizador()
        mostrarPregunta()

        btnResponder.setOnClickListener {
            val seleccion = radioGroup.checkedRadioButtonId
            if (seleccion != -1) {
                val respuestaSeleccionada = radioGroup.indexOfChild(findViewById(seleccion))
                if (respuestaSeleccionada == respuestasCorrectas[preguntaActual]) {
                    puntaje++
                }
                preguntaActual++
                if (preguntaActual < preguntas.size) {
                    mostrarPregunta()
                } else {
                    temporizador.cancel()
                    irAResultado()
                }
            } else {
                Toast.makeText(this, "Selecciona una opción", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarTemporizador() {
        temporizador = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                segundosRestantes = (millisUntilFinished / 1000).toInt()
                val minutos = segundosRestantes / 60
                val segundos = segundosRestantes % 60
                txtTemporizador.text = "Tiempo restante: %02d:%02d".format(minutos, segundos)
                barraTiempo.progress = segundosRestantes
            }

            override fun onFinish() {
                segundosRestantes = 0
                irAResultado()
            }
        }.start()
    }

    private fun mostrarPregunta() {
        val (pregunta, opciones) = preguntas[preguntaActual]
        txtPregunta.text = pregunta
        radioGroup.removeAllViews()
        for (opcion in opciones) {
            val radioButton = RadioButton(this)
            radioButton.text = opcion
            radioGroup.addView(radioButton)
        }
        txtPuntaje.text = "Puntaje: $puntaje"
    }

    private fun irAResultado() {
        val intent = Intent(this, RsultadoActivity::class.java)
        intent.putExtra("PUNTAJE_FINAL", puntaje)
        intent.putExtra("TIEMPO_RESTANTE", segundosRestantes)
        startActivity(intent)
        finish()
    }
}