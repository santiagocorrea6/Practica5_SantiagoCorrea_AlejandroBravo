package com.santiago.sesionroom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //AQUI EMPIEZA FIREBASE
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        bt_registrar.setOnClickListener {

            val email = et_email.text.toString()
            val password = et_pass.text.toString()


            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // crearUsuarioEnBaseDeDatos()
                        Toast.makeText(
                            this, "Registro exitoso",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()
                    } else {

                        Toast.makeText(
                            this, "Registro fallido",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.w("TAG", "signInWithEmail:failure", task.getException())
                    }
                }
        }
    }


        /*bt_guardar.setOnClickListener {
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
    }*/

    private fun crearUsuarioEnBaseDeDatos() {
        TODO("Not yet implemented")
    }
}