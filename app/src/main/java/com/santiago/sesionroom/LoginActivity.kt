package com.santiago.sesionroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.santiago.sesionroom.model.UsuarioDAO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_ingresar.setOnClickListener {
            val correo = et_email.text.toString()
            val contrasena = et_pass.text.toString()

            val usuarioDAO: UsuarioDAO = SesionROOM.database2.UsuarioDAO()
            val usuario = usuarioDAO.buscarUsuario(correo)

            if(usuario != null){

                //Contraseña Vacia
                if (contrasena == null) {
                    et_pass.error = "Ingrese la contraseña"
                }

                //Usuario validado
                else if (contrasena == usuario.contrasena){
                    Toast.makeText(getApplicationContext(), "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                //Contraseña invalida
                else  {
                    et_pass.error = "La contraseña es incorrecta"
                }

            } else {
                et_email.error = "Usuario no registrado"
            }
        }

        bt_registro.setOnClickListener {
            val intent: Intent = Intent(this, RegistroActivity::class.java)
            startActivityForResult(intent, 1234)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK) {

            et_email.setText(data?.extras?.getString("correo"))
            et_pass.setText(data?.extras?.getString("contrasena"))
        }
    }
}
