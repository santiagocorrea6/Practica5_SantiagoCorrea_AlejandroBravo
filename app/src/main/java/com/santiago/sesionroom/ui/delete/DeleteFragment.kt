package com.santiago.sesionroom.ui.delete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.santiago.sesionroom.R
import com.santiago.sesionroom.SesionROOM
import kotlinx.android.synthetic.main.fragment_delete.*

class DeleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_eliminar.setOnClickListener {
            val nombre = et_nombre.text.toString()

            val deudorDAO = SesionROOM.database.DeudorDAO()
            val deudor = deudorDAO.buscarDeudor(nombre)

            if(deudor != null)
                deudorDAO.borrarDeudor(deudor)
            else
                Toast.makeText(context, "Deudor no encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}