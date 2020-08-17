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
import dev.celleptech.controlefinanceiro.adapter.RecyclerViewReceitaAdapter
import dev.celleptech.controlefinanceiro.model.Receita
import dev.celleptech.controlefinanceiro.repository.ReceitaRepository

class ReceitasFragment : Fragment() {

    // Atributos da classe
    private lateinit var receitaRepository: ReceitaRepository
    private lateinit var recyclerViewAdapter: RecyclerViewReceitaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_receitas, container, false)

        // Referênciando a recyclerView
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerReceitas)

        // Referênciando o floatActionButton
        val floatActionButton = root.findViewById<FloatingActionButton>(R.id.floatingActionButtonReceitas)

        // Instânciando a classe repository
        receitaRepository = ReceitaRepository(root.context)

        // Instânciando o Adaptador
        recyclerViewAdapter = RecyclerViewReceitaAdapter()

        // Alimentar o adaptador com dados
        recyclerViewAdapter.setData(receitaRepository.selectAll())

        // Escutando os eventos de clique no adaptador
        recyclerViewAdapter.setListener(object : RecyclerViewListener<Receita> {

            // Editar
            override fun onClick(v: View, dataSet: Receita) {
                val mIntent = Intent(context, LancamentoActivity::class.java)

                // Indicando o tipo de lançamento
                mIntent.putExtra("INTENT_TIPO", "receita")

                // Passando dados do lançamento para LancamentoActivity
                mIntent.putExtra("INTENT_DADOS", dataSet)

                // Abrindo lancamentoActivity
                startActivity(mIntent)

            }

            // Deletar
            override fun onLongClick(v: View, dataSet: Receita) {

                val alert = AlertDialog.Builder(context)
                alert.setMessage("Deseja deletar este lançamento?")
                alert.setPositiveButton("Sim") { _, _ ->
                    // Deletando o registro
                    receitaRepository.delete(dataSet)

                    // Atualizar o adaptador
                    recyclerViewAdapter.setData(receitaRepository.selectAll())
                }
                alert.setNegativeButton("Não") { _, _ -> }
                alert.show()

            }

        })

        // Definir o tipo de layout do RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Plugar o adaptador
        recyclerView.adapter = recyclerViewAdapter

        // Escutando o evento de clique do floatActionButton
        floatActionButton.setOnClickListener {

            val mIntent = Intent(context, LancamentoActivity::class.java)

            // Indicando o tipo de lançamento
            mIntent.putExtra("INTENT_TIPO", "receita")

            // Abrindo a tela LancamentoActivity
            startActivity(mIntent)

        }

        return root
    }

    // Corrige o bug que não atualiza o adaptador quando o fragment volta a ter foco
    override fun onResume() {
        super.onResume()
        // Atualizando o adaptador
        recyclerViewAdapter.setData(receitaRepository.selectAll())
    }

    // Retorna uma única instânco do fragment
    companion object {
        @JvmStatic
        fun newInstance() =
            ReceitasFragment()
    }
}