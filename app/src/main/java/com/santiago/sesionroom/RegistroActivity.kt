package com.santiago.sesionroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.santiago.sesionroom.model.Usuario
import com.santiago.sesionroom.model.UsuarioDAO
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_registro.et_email
import kotlinx.android.synthetic.main.activity_registro.et_pass
import kotlinx.android.synthetic.main.fragment_create.bt_guardar
import java.sql.Types

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        bt_guardar.setOnClickListener {
            val nombre= et_name.text.toString()
            val correo = et_email.text.toString()
            val contrasena = et_pass.text.toString()
            val contrasena2 = et_pass2.text.toString()

            if (correo.isEmpty()){
                et_email.error = "Ingrese su correo"
            } else {
                val usuarioDAO: UsuarioDAO = SesionROOM.database2.UsuarioDAO()
                val usuario = usuarioDAO.buscarUsuario(correo)

                if(usuario != null){
                    et_email.error = "Usuario ya registrado"
                }

                else{
                    if (nombre.isEmpty()){
                        et_name.error = "Ingrese su nombre"
                    }

                    else if (contrasena.isEmpty()){
                        et_pass.error = "Ingrese la contraseña"
                    }

                    else if (contrasena2.isEmpty()){
                        et_pass2.error = "Repita la contraseña"
                    }

                    else if (contrasena != contrasena2){
                        et_pass2.error = "La contraseña no coincide"
                    }

                    else {
                        val usuario = Usuario(Types.NULL, nombre, correo, contrasena)
                        val usuarioDAO : UsuarioDAO = SesionROOM.database2.UsuarioDAO()
                        usuarioDAO.crearUsuario(usuario)

                        Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()

                        val intent = Intent()
                        intent.putExtra("correo", et_email.text.toString())
                        intent.putExtra("contrasena", et_pass.text.toString())
                        setResult(Activity.RESULT_OK, intent)
                        finish()

                        //startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
            }
        }
    }
}