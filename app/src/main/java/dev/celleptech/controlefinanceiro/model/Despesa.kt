package dev.celleptech.controlefinanceiro.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "despesa")
class Despesa : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "categoria")
    var categoria: Int = 0

    @ColumnInfo(name = "valor")
    var valor: Double = 0.0

    @ColumnInfo(name = "status")
    var status: Boolean = true

}