package com.example.app_ficheros_alejandragarcia.modelo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_ficheros_alejandragarcia.R

class User {
    var usuario:String = ""
    var password:String = ""
    var nombre:String = ""
    var apellidos:String = ""

    fun getBundle():Bundle{
        var bundle = Bundle()
        bundle.putCharSequence("usuario", usuario)
        bundle.putCharSequence("password", password)
        bundle.putCharSequence("nombre", nombre)
        bundle.putCharSequence("apellidos", apellidos)

        return bundle
    }

    fun setBundle(bundle:Bundle){
        usuario = bundle.getString("usuario", "")
        password = bundle.getString("password", "")
        nombre = bundle.getString("nombre", "")
        apellidos = bundle.getString("apellidos", "")
    }


}