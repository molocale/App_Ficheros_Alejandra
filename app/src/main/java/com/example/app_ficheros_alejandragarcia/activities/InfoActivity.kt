package com.example.app_ficheros_alejandragarcia.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.app_ficheros_alejandragarcia.MainActivity
import com.example.app_ficheros_alejandragarcia.R
import com.example.app_ficheros_alejandragarcia.modelo.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_form.*
import java.io.BufferedReader
import java.io.InputStreamReader

class InfoActivity : AppCompatActivity() {
    var miUser = User()
    var miFichero: String = "miFichero.txt"
    val gson = Gson()
    var miUserLogeado: String? = null
    lateinit var miColeccionUsuarios: HashMap<String, User>

    lateinit var etUsuario: EditText
    lateinit var etContra: EditText
    lateinit var etNombre: EditText
    lateinit var etApellidos: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        var btnActualizar = findViewById<Button>(R.id.btnActualizar)
        btnActualizar.visibility = View.VISIBLE
        var btnAceptar = findViewById<Button>(R.id.btnAceptar)
        btnAceptar.visibility = View.INVISIBLE
        var titulo = findViewById<TextView>(R.id.textViewRegistro)
        titulo.setText("Información")



        miUserLogeado = intent.getStringExtra("miUser")
         etUsuario = findViewById<EditText>(R.id.editTextUsuario)
         etContra = findViewById<EditText>(R.id.editTextContrasena)
         etNombre = findViewById<EditText>(R.id.editTextNombre)
         etApellidos = findViewById<EditText>(R.id.editTextApellidos)


        miColeccionUsuarios = leer()

        var usuarioLogin = miColeccionUsuarios.get(miUserLogeado)


        etUsuario.setText(usuarioLogin?.usuario)
        etContra.setText(usuarioLogin?.password)
        etNombre.setText(usuarioLogin?.nombre)
        etApellidos.setText(usuarioLogin?.apellidos)


    }

    fun onActualizar(view: View) {
        //actualiza los datos del usuario con los que se hallan introducido
        var miUserModificado = etUsuario.text.toString()
        var miPasswordModificado = etContra.text.toString()
        var miNombreModificado = etNombre.text.toString()
        var miApellidosModificado = etApellidos.text.toString()

        var nuevoUsuario: User = User()
        nuevoUsuario.usuario = miUserModificado
        nuevoUsuario.password = miPasswordModificado
        nuevoUsuario.nombre = miNombreModificado
        nuevoUsuario.apellidos = miApellidosModificado

        miColeccionUsuarios.remove(miUserLogeado)
        miColeccionUsuarios.put(miUserModificado , nuevoUsuario)

        pintar(miColeccionUsuarios)
        Toast.makeText(this, "Usuario modificado correctamente, vuelva a logearse", Toast.LENGTH_LONG).show()



        var intentMainActualizar = Intent(this, MainActivity::class.java)
        intentMainActualizar.putExtra("miUser", miUserModificado)
        startActivity(intentMainActualizar)


    }

    fun onCancelar(view: View) {

        var intentMain = Intent(this, MainActivity::class.java)
        intentMain.putExtra("isLogin", true)
        intentMain.putExtra("miUser", miUserLogeado)
        startActivity(intentMain)


    }

    fun leer(): HashMap<String, User> {

        var miColeccionUsuarios: HashMap<String, User> = HashMap<String, User>()
        // Lectura interna
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
        //FICHERO
        val JSON: String = gson.toJson(misUsuarios)
        var fileOutput = openFileOutput(miFichero, Context.MODE_PRIVATE)
        fileOutput.write(JSON.toByteArray())
        fileOutput.close()

    }

}