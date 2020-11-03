package com.example.app_ficheros_alejandragarcia

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_ficheros_alejandragarcia.activities.FormActivity
import com.example.app_ficheros_alejandragarcia.activities.InfoActivity
import com.example.app_ficheros_alejandragarcia.dialogs.LoginDialog
import com.example.app_ficheros_alejandragarcia.modelo.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login_dialog.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var usuario: User = User()
    var miFichero: String = "miFichero.txt"
    val gson = Gson()
    lateinit var miUserLogeado: String
    var miFormulario: FormActivity = FormActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnRegistro = findViewById<Button>(R.id.btnRegistro)
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var btnInfo = findViewById<Button>(R.id.btnInformacion)
        var txtPestaña = findViewById<TextView>(R.id.textView)


        try {

            var bufferedReader = BufferedReader(InputStreamReader(openFileInput(miFichero)))
            var textoLeido = bufferedReader.readLine()
            bufferedReader.close()
            if (textoLeido != null){
                btnLogin.isEnabled = true
            }
        } catch (e: Exception) {
            var fileOutput = openFileOutput("miFichero.txt", Context.MODE_PRIVATE)
        }


        var isLogin = intent.getBooleanExtra("isLogin", false)
        miUserLogeado = intent.getStringExtra("miUser").toString()

        if (isLogin){
            Toast.makeText(this, "BIENVENIDO " + miUserLogeado, Toast.LENGTH_LONG).show()
            btnInfo.isEnabled = true
        } else if (!isLogin && miUserLogeado == null){
            Toast.makeText(this, "Usuario/Contraseña incorrectos", Toast.LENGTH_LONG).show()

        }

    }

    fun onRegistro(view: View) {
        var intentRegistro = Intent(this, FormActivity::class.java)
        startActivity(intentRegistro)

    }

    fun onLogin(view: View) {
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(miFichero)))
        var textoLeido = bufferedReader.readLine()
        bufferedReader.close()
        var miColeccionUsuarios: HashMap<String, User> = gson.fromJson(textoLeido, object : TypeToken<HashMap<String, User>>() {}.type)
        var loginDialog = LoginDialog(miColeccionUsuarios)
        loginDialog.miUser = usuario
        loginDialog.mainActivity = this
        loginDialog.show(supportFragmentManager, "logiDialog_Tag")

    }

    fun onInformacion(view: View) {
        var intentInfo = Intent(this, InfoActivity::class.java)
        intentInfo.putExtra("miUser", miUserLogeado)

        startActivity(intentInfo)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SALIR DE LA APP_FICHEROS_ALEJANDRA")
        builder.setMessage("¿Está seguro que quiere salir?")

        builder.setPositiveButton("Si") { dialog, _ -> finish() }
        builder.setNegativeButton("No") { dialog, which -> }
        builder.show()

    }
}