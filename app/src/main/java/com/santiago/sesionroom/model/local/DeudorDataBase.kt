package com.santiago.sesionroom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santiago.sesionroom.model.local.Deudor
import com.santiago.sesionroom.model.local.DeudorDAO

@Database(entities = arrayOf(Deudor::class), version = 1)
abstract class DeudorDataBase : RoomDatabase(){

    abstract fun DeudorDAO() : DeudorDAO

}