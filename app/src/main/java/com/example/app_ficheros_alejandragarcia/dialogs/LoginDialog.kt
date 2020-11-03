package com.example.app_ficheros_alejandragarcia.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.app_ficheros_alejandragarcia.MainActivity
import com.example.app_ficheros_alejandragarcia.R
import com.example.app_ficheros_alejandragarcia.activities.FormActivity
import com.example.app_ficheros_alejandragarcia.modelo.User
import kotlinx.android.synthetic.main.activity_login_dialog.*


class LoginDialog(private val miColeccionUsuarios: HashMap<String, User>) : DialogFragment() {
    var miUser = User()
    lateinit var mainActivity: MainActivity
    var miFormulario: FormActivity = FormActivity()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var viewDialog = inflater.inflate(R.layout.activity_login_dialog, container, false)
        var botonLogin = viewDialog.findViewById<Button>(R.id.btnLoggeo)
        botonLogin.setOnClickListener { view -> login(view) }

        return viewDialog

    }


    fun login(view: View) {

        var et_user = dialog!!.findViewById<EditText>(R.id.editTextUserName)
        var et_password = dialog!!.findViewById<EditText>(R.id.editTextPassword)

        var user = et_user.text.toString()
        var pass = et_password.text.toString()

        if (user == "" || pass == "") {
            Toast.makeText(this.activity, "Campos sin rellenar", Toast.LENGTH_LONG).show()
        } else {

            var intentMain = Intent(this.context, MainActivity::class.java)
                for (cadaUsuario in miColeccionUsuarios.values) {
                    if (cadaUsuario.usuario == user && cadaUsuario.password == pass ) {
                        intentMain.putExtra("isLogin",  true)
                        intentMain.putExtra("miUser",  user)

                    } else{
                        //textViewLogin.text = "Login incorrecto "
                        //Toast.makeText(this.activity, "Usuario/Contraseña incorrectos", Toast.LENGTH_LONG).show()

                    }
                 }

            startActivity(intentMain)

        }

    }
}
