package com.example.app_ficheros_alejandragarcia.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_ficheros_alejandragarcia.MainActivity
import com.example.app_ficheros_alejandragarcia.R
import com.example.app_ficheros_alejandragarcia.modelo.User
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_form.*
import java.io.BufferedReader
import java.io.InputStreamReader


class FormActivity : AppCompatActivity() {
    var miUser = User()
    var miFichero: String = "miFichero.txt"
    val gson = Gson()

    lateinit var editTextUsuario: EditText
    lateinit var editTextContrasena: EditText
    lateinit var editTextNombre: EditText
    lateinit var editTextApellidos: EditText
    lateinit var txtViewRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        editTextContrasena = findViewById<EditText>(R.id.editTextContrasena)
        editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        editTextApellidos = findViewById<EditText>(R.id.editTextApellidos)
        txtViewRegistro = findViewById<TextView>(R.id.textViewRegistro)
    }

    fun onAceptar(view: View) {
        //Pasar informacion al fichero

        miUser.usuario = editTextUsuario.text.toString()
        miUser.password = editTextContrasena.text.toString()
        miUser.nombre = editTextNombre.text.toString()
        miUser.apellidos = editTextApellidos.text.toString()

        if (miUser.usuario == "" || miUser.password == "" || miUser.nombre == "" || miUser.apellidos == "") {
            Toast.makeText(this, "Campos sin rellenar", Toast.LENGTH_LONG).show()
        } else {


            var miColeccionUsuarios: HashMap<String, User> = leer()
            //Si esta vacio mi hashmap
            if (miColeccionUsuarios.size == 0) {
                miColeccionUsuarios.put(miUser.usuario, miUser)
                pintar(miColeccionUsuarios)
                Toast.makeText(this, "Usuario añadido correctamente", Toast.LENGTH_LONG).show()


            } else {
                val usuarioBuscado = miUser.usuario
                var isContenida = false

                for (cadaUsuario in miColeccionUsuarios.values) {
                    if (cadaUsuario.usuario == usuarioBuscado) {
                        isContenida = true

                    }
                }

                if (isContenida) {
                    //Lo modificamos
                    var userRepe = miColeccionUsuarios.get(miUser.usuario)
                    if (userRepe != null) {
                        userRepe.password = miUser.password
                        userRepe.nombre = miUser.nombre
                        userRepe.apellidos = miUser.apellidos
                    }
                    pintar(miColeccionUsuarios)

                } else {
                    //Lo pintamos
                    miColeccionUsuarios.put(miUser.usuario, miUser)
                    pintar(miColeccionUsuarios)
                    Toast.makeText(this, "Usuario añadido correctamente", Toast.LENGTH_LONG).show()
                }

            }

        }

        var intentMain = Intent(this, MainActivity::class.java)
        startActivity(intentMain)


    }


    fun onCancelar(view: View) {
        finish()
    }

    fun leer(): HashMap<String, User> {

        var miColeccionUsuarios: HashMap<String, User> = HashMap<String, User>()

        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(miFichero)))
        var textoLeido = bufferedReader.readLine()
        bufferedReader.close()

        if (textoLeido != null) {
            miColeccionUsuarios =
                gson.fromJson(textoLeido, object : TypeToken<HashMap<String, User>>() {}.type)
        }

        return miColeccionUsuarios

    }

    fun pintar(misUsuarios: HashMap<String, User>) {

        val JSON: String = gson.toJson(misUsuarios)
        var fileOutput = openFileOutput(miFichero, Context.MODE_PRIVATE)
        fileOutput.write(JSON.toByteArray())
        fileOutput.close()

    }

}
