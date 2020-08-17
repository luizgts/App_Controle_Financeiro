package dev.celleptech.controlefinanceiro.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.celleptech.controlefinanceiro.LancamentoActivity
import dev.celleptech.controlefinanceiro.R
import dev.celleptech.controlefinanceiro.`interface`.RecyclerViewListener
import dev.celleptech.controlefinanceiro.adapter.RecyclerViewDespesaAdapter
import dev.celleptech.controlefinanceiro.model.Despesa
import dev.celleptech.controlefinanceiro.repository.DespesaRepository

class DespesasFragment : Fragment() {

    // Declando os atributos da classe
    private lateinit var despesaRepository: DespesaRepository
    private lateinit var recyclerViewAdapter: RecyclerViewDespesaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_despesas, container, false)

        // Referênciando os elementos
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerDespesas)
        val floatActionButton = root.findViewById<FloatingActionButton>(R.id.floatingActionButtonDespesas)

        // Criando instâncias
        despesaRepository = DespesaRepository(root.context)
        recyclerViewAdapter = RecyclerViewDespesaAdapter()

        // Alimentando os adaptador
        recyclerViewAdapter.setData(despesaRepository.selectAll())

        // Escutando os eventos de clique no adaptador
        recyclerViewAdapter.setListener(object : RecyclerViewListener<Despesa> {

            override fun onClick(v: View, dataSet: Despesa) {
                // Editar
                val mIntent = Intent(context, LancamentoActivity::class.java)

                // Definir o tipo de lançamento
                mIntent.putExtra("INTENT_TIPO", "despesa")

                // Passando dados do registro
                mIntent.putExtra("INTENT_DADOS", dataSet)

                startActivity(mIntent)
            }

            override fun onLongClick(v: View, dataSet: Despesa) {
                // Deletar
                val alert = AlertDialog.Builder(context)
                alert.setMessage("Deseja deletar este lançamento?")
                alert.setPositiveButton("Sim") { _, _ ->
                    // Deletar um registro
                    despesaRepository.delete(dataSet)
                    // Atualizar o adaptador
                    recyclerViewAdapter.setData(despesaRepository.selectAll())
                }
                alert.setNeutralButton("Não") { _, _ -> }
                alert.show()
            }

        })

        // Definindo o tipo de layout do recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Plugando o adaptador
        recyclerView.adapter = recyclerViewAdapter

        // Escutando o evento de clique do floatActionButton
        floatActionButton.setOnClickListener {

            val mIntent = Intent(context, LancamentoActivity::class.java)
            mIntent.putExtra("INTENT_TIPO", "despesa")
            startActivity(mIntent)

        }

        return root
    }

    // Corrige o bug que não atualiza o adaptador quando o fragment volta a ter foco
    override fun onResume() {
        super.onResume()
        // Atualizando o adapter
        recyclerViewAdapter.setData(despesaRepository.selectAll())
    }

    // Retorna uma única instânco do fragment
    companion object {
        @JvmStatic
        fun newInstance() =
            DespesasFragment()
    }
}