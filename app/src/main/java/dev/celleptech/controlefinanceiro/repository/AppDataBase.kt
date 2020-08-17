package dev.celleptech.controlefinanceiro.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.celleptech.controlefinanceiro.model.Despesa
import dev.celleptech.controlefinanceiro.model.Receita
import dev.celleptech.controlefinanceiro.repository.dao.DespesaDao
import dev.celleptech.controlefinanceiro.repository.dao.ReceitaDao

@Database(entities = [Receita::class, Despesa::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    // Referênciar as classes Dao
    abstract fun receitaDao(): ReceitaDao
    abstract fun despesaDao(): DespesaDao

    companion object {

        // Retornar sempre uma única instância do Room
        private lateinit var instance: AppDataBase

        fun getIntance(context: Context): AppDataBase {

            if (!Companion::instance.isInitialized) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "controle_financeiro.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instance
        }

    }

}