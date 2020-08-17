package dev.celleptech.controlefinanceiro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import dev.celleptech.controlefinanceiro.model.Despesa
import dev.celleptech.controlefinanceiro.model.Receita
import dev.celleptech.controlefinanceiro.repository.DespesaRepository
import dev.celleptech.controlefinanceiro.repository.ReceitaRepository
import kotlinx.android.synthetic.main.activity_lancamento.*

class LancamentoActivity : AppCompatActivity() {

    private lateinit var spnAdapter: ArrayAdapter<Any>
    private lateinit var despesaRepository: DespesaRepository
    private lateinit var receitaRepository: ReceitaRepository
    private var id = 0
    private lateinit var tipo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento)

        // Esta tela serve para cadastrar e editar uma receita ou uma despesa
        tipo = intent.getStringExtra("INTENT_TIPO")
        val dados = intent.getSerializableExtra("INTENT_DADOS")

        // Verifica como a tela deve ser utilizada
        if (tipo == "receita") {

            // Instanciar receitaRepository
            receitaRepository = ReceitaRepository(this)

            // Criar o Spinner Adapter
            spnAdapter = ArrayAdapter(
                this, // Contexto
                android.R.layout.simple_spinner_dropdown_item, // Layout
                categoriasReceitas // Dados a exibir
            )

        } else {

            despesaRepository = DespesaRepository(this)

            spnAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categoriasDespesas
            )

        }

        // Plugar o adaptador no Spinner
        spinnerLancamentoCategoria.adapter = spnAdapter

        // Verifica se a tela vai ser utilizada para cadastro ou edição
        if (dados != null) {

            // Variáveis temporárias
            val valor: Double
            val categoria: Int
            val status: Boolean

            if (tipo == "receita") {

                // Deserializando um objeto (Remontando o objeto)
                val receita = dados as Receita
                id = receita.id
                valor = receita.valor
                categoria = receita.categoria
                status = receita.status

            } else {

                val despesa = dados as Despesa
                id = despesa.id
                valor = despesa.valor
                categoria = despesa.categoria
                status = despesa.status

            }

            // Exibir os dados
            val valorFormatado = "${"%.2f".format(valor)}".replace(",", ".")
            spinnerLancamentoCategoria.setSelection(categoria)
            editTextLancamentoValor.setText(valorFormatado)
            checkBoxLancamentoStatus.isChecked = status
            buttonLancamento.text = "Editar"

        } else {

            // Lógica para cadastrar
            buttonLancamento.text = "Cadastrar"

        }

        // Escutando os cliques do botão
        buttonLancamento.setOnClickListener {

            if (id == 0) {
                save()
            } else {
                update()
            }

            finish()

        }

    }

    private fun save() {

        // Obter os dados digitados
        val valor = editTextLancamentoValor.text.toString().toDouble()
        val categoria = spinnerLancamentoCategoria.selectedItemId.toInt()
        val status = checkBoxLancamentoStatus.isChecked

        // Tipo de salvamento (Receita ou Despesa)
        if (tipo == "receita") {

            val receita = Receita()
            receita.valor = valor
            receita.categoria = categoria
            receita.status = status

            // Inserir no banco de dados
            receitaRepository.insert(receita)

        } else {

            val despesa = Despesa()
            despesa.valor = valor
            despesa.categoria = categoria
            despesa.status = status

            // Inserir no banco de dados
            despesaRepository.insert(despesa)

        }

    }

    private fun update() {

        // Obter os dados digitados
        val valor = editTextLancamentoValor.text.toString().toDouble()
        val categoria = spinnerLancamentoCategoria.selectedItemId.toInt()
        val status = checkBoxLancamentoStatus.isChecked

        // Tipo de edição (Receita ou Despesa)
        if (tipo == "receita") {

            val receita = Receita()
            receita.id = id
            receita.valor = valor
            receita.categoria = categoria
            receita.status = status

            // Inserir no banco de dados
            receitaRepository.update(receita)

        } else {

            val despesa = Despesa()
            despesa.id = id
            despesa.valor = valor
            despesa.categoria = categoria
            despesa.status = status

            // Inserir no banco de dados
            despesaRepository.update(despesa)

        }

    }

}