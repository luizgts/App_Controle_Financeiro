package dev.celleptech.controlefinanceiro.repository

import android.content.Context
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import dev.celleptech.controlefinanceiro.categoriasDespesas
import dev.celleptech.controlefinanceiro.model.Despesa

class DespesaRepository(context: Context) {

    // Obtendo uma instância do banco de dados
    private val database = AppDataBase.getIntance(context).despesaDao()

    // Inserir dados no banco de dados
    fun insert(despesa: Despesa) {
        return database.insert(despesa)
    }

    // Retornar dados no banco de dados
    fun selectAll(): List<Despesa> {
        return database.selectAll()
    }

    // Retorna total de despesas
    fun totalDespesas(): Double {
        return database.totalDespesas()
    }

    // Retorna o total de despesas por categoria
    fun totalDespesasCategorias(): List<DataEntry> {

        val data = mutableListOf<DataEntry>()

        // Retornando uma lista com categorias e totais
        database.totalDespesasCategorias().forEach {
            // Jogando os valores de uma lista para outra
            data.add(
                ValueDataEntry(
                    categoriasDespesas[it.categoria],
                    it.total
                )
            )

        }

        return data

    }

    // Atualiza um registro no banco de dados
    fun update(despesa: Despesa) {
        return database.update(despesa)
    }

    // Deletar um registro no banco de dados
    fun delete(despesa: Despesa) {
        return database.delete(despesa)
    }

}