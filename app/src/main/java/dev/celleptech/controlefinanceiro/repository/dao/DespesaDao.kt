package dev.celleptech.controlefinanceiro.repository.dao

import androidx.room.*
import dev.celleptech.controlefinanceiro.model.Despesa
import dev.celleptech.controlefinanceiro.model.TotalCategoria

@Dao
interface DespesaDao {

    // Inserir dado na tabela
    @Insert
    fun insert(despesa: Despesa)

    // Retornar todos os registros na tabela
    @Query("SELECT * FROM despesa")
    fun selectAll(): List<Despesa>

    // Retornar valor total das receitas
    @Query("SELECT SUM(valor) FROM despesa WHERE status=1")
    fun totalDespesas(): Double

    // Retornar valor total de receitas por categoria
    @Query("SELECT categoria, SUM(valor) AS total FROM despesa WHERE status=1 GROUP BY categoria")
    fun totalDespesasCategorias(): List<TotalCategoria>

    // Atualiza um registro
    @Update
    fun update(despesa: Despesa)

    // Delete um registro
    @Delete
    fun delete(despesa: Despesa)
}