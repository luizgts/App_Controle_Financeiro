package dev.celleptech.controlefinanceiro.repository

import android.content.Context
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import dev.celleptech.controlefinanceiro.categoriasReceitas
import dev.celleptech.controlefinanceiro.model.Receita

class ReceitaRepository(context: Context) {

    // Obtendo uma instância do banco de dados
    private val database = AppDataBase.getIntance(context).receitaDao()

    // Inserir dados no banco de dados
    fun insert(receita: Receita) {
        return database.insert(receita)
    }

    // Retornar dados no banco de dados
    fun selectAll(): List<Receita> {
        return database.selectAll()
    }

    // Retorna total de Receitas
    fun totalReceitas(): Double {
        return database.totalReceitas()
    }

    // Retorna o total de Receitas por categoria
    fun totalReceitasCategorias(): List<DataEntry> {

        val data = mutableListOf<DataEntry>()

        // Retornando uma lista com categorias e totais
        database.totalReceitasCategorias().forEach {
            // Jogando os valores de uma lista para outra
            data.add(
                ValueDataEntry(
                    categoriasReceitas[it.categoria],
                    it.total
                )
            )

        }

        return data

    }

    // Atualiza um registro no banco de dados
    fun update(receita: Receita) {
        return database.update(receita)
    }

    // Deletar um registro no banco de dados
    fun delete(receita: Receita) {
        return database.delete(receita)
    }

}