package dev.celleptech.controlefinanceiro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import dev.celleptech.controlefinanceiro.R
import dev.celleptech.controlefinanceiro.repository.DespesaRepository
import dev.celleptech.controlefinanceiro.repository.ReceitaRepository

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Instânciando as classes de repositório
        val despesaRepository = DespesaRepository(root.context)
        val receitaRepository = ReceitaRepository(root.context)

        // Referênciando os elemento no layout
        val textViewReceita = root.findViewById<TextView>(R.id.textViewHomeReceita)
        val textViewDespesa = root.findViewById<TextView>(R.id.textViewHomeDespesa)
        val textViewSaldo = root.findViewById<TextView>(R.id.textViewHomeSaldo)

        // Exibindo informações nos widgets
        val totalReceitas = receitaRepository.totalReceitas()
        val totalDespesas = despesaRepository.totalDespesas()
        val saldo = totalReceitas - totalDespesas

        textViewReceita.text = "R$ %.2f".format(totalReceitas)
        textViewDespesa.text = "R$ %.2f".format(totalDespesas)
        textViewSaldo.text = "R$ %.2f".format(saldo)

        // Referênciando o gráfico
        val despesaChart = root.findViewById<AnyChartView>(R.id.anyChartHome)

        // Instânciando um gráfico de torta
        val pie = AnyChart.pie()

        // Definindo o título do gráfico
        pie.title("Despesas")

        // Alimentando o gráfico
        pie.data(despesaRepository.totalDespesasCategorias())

        // Exibindo o gráfico
        despesaChart.setChart(pie)

        return root
    }

    // Retorna uma única instânco do fragment
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}