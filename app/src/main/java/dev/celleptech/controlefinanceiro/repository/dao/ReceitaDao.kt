package dev.celleptech.controlefinanceiro.repository.dao

import androidx.room.*
import dev.celleptech.controlefinanceiro.model.Receita
import dev.celleptech.controlefinanceiro.model.TotalCategoria

@Dao
interface ReceitaDao {

    // Inserir dado na tabela
    @Insert
    fun insert(receita: Receita)

    // Retornar todos os registros na tabela
    @Query("SELECT * FROM receita")
    fun selectAll(): List<Receita>

    // Retornar valor total das receitas
    @Query("SELECT SUM(valor) FROM receita WHERE status=1")
    fun totalReceitas(): Double

    // Retornar valor total de receitas por categoria
    @Query("SELECT categoria, SUM(valor) AS total FROM receita WHERE status=1 GROUP BY categoria")
    fun totalReceitasCategorias(): List<TotalCategoria>

    // Atualiza um registro
    @Update
    fun update(receita: Receita)

    // Delete um registro
    @Delete
    fun delete(receita: Receita)
}