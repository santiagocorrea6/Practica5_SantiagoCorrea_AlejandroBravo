package com.santiago.sesionroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.santiago.sesionroom.model.Usuario
import com.santiago.sesionroom.model.UsuarioDAO
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.fragment_create.bt_guardar
import java.sql.Types
import java.util.regex.Pattern


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        bt_guardar.setOnClickListener {
            val nombre= et_name.text.toString()
            val correo = et_email.text.toString()
            val contrasena = et_pass.text.toString()
            val contrasena2 = et_pass2.text.toString()

            val passLength: Int = stringLengthFunc("$contrasena")

            if (correo.isEmpty()){
                et_email.error = "Ingrese su correo"
            } else {

                val usuario = searchUser(correo)

                if(usuario != null){
                    et_email.error = "Usuario ya registrado"
                } else{
                    validateDataRegistry(correo, nombre, contrasena, passLength, contrasena2)
                }
            }
        }
    }

    //Buscar usuario en ROOM
    private fun searchUser(correo: String): Usuario {
        val usuarioDAO: UsuarioDAO = SesionROOM.database2.UsuarioDAO()
        val usuario = usuarioDAO.buscarUsuario(correo)
        return usuario
    }

    //Validar campos de registro
    private fun validateDataRegistry(
        correo: String,
        nombre: String,
        contrasena: String,
        passLength: Int,
        contrasena2: String
    ) {
        if (!validarEmail(correo)) {
            et_email.setError("Email no válido")
        }

        if (nombre.isEmpty()) {
            et_name.error = "Ingrese su nombre"
        } else if (contrasena.isEmpty()) {
            et_pass.error = "Ingrese la contraseña"
        } else if (passLength < 6) { //CONTRASEÑA DE 6 DIGITOS
            et_pass.error = "La contraseña debe tener minimo 6 caracteres"
        } else if (contrasena2.isEmpty()) {
            et_pass2.error = "Repita la contraseña"
        } else if (contrasena != contrasena2) {
            et_pass2.error = "Las contraseñas no coinciden"
        } else if (contrasena == contrasena2 && validarEmail(correo)) {

            createUser(nombre, correo, contrasena)

            registryOk()
        }
    }

    //Registro realizado
    private fun registryOk() {
        Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()

        val intent = Intent()
        intent.putExtra("correo", et_email.text.toString())
        intent.putExtra("contrasena", et_pass.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    //Crear nuevo usuario
    private fun createUser(nombre: String, correo: String, contrasena: String) {
        val usuario = Usuario(Types.NULL, nombre, correo, contrasena)
        val usuarioDAO: UsuarioDAO = SesionROOM.database2.UsuarioDAO()
        usuarioDAO.crearUsuario(usuario)
    }

    //Longitud de string
    val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    //Validar correo
    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}