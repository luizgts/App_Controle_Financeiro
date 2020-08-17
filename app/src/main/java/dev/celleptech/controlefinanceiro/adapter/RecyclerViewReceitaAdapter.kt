package dev.celleptech.controlefinanceiro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.celleptech.controlefinanceiro.R
import dev.celleptech.controlefinanceiro.`interface`.RecyclerViewListener
import dev.celleptech.controlefinanceiro.categoriasDespesas
import dev.celleptech.controlefinanceiro.categoriasReceitas
import dev.celleptech.controlefinanceiro.model.Despesa
import dev.celleptech.controlefinanceiro.model.Receita
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class RecyclerViewReceitaAdapter :
    RecyclerView.Adapter<RecyclerViewReceitaAdapter.ViewHolderReceita>() {

    private lateinit var dataSet: List<Receita>
    private lateinit var listener: RecyclerViewListener<Receita>

    // Representa cada elementa no RecyclerView
    inner class ViewHolderReceita(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dataSet: Receita) {

            // Preenchendo os componentes do CardView
            itemView.textViewItemCategoria.text = categoriasReceitas[dataSet.categoria]
            itemView.textViewItemValor.text = "R$ %.2f".format(dataSet.valor)
            itemView.textViewItemStatus.text = if (dataSet.status) "Lançada" else "Não Lançada"

            // Controlar os clicks no CardView
            itemView.cardViewItem.setOnClickListener {
                listener.onClick(it, dataSet)
            }

            itemView.cardViewItem.setOnLongClickListener {
                listener.onLongClick(it, dataSet)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReceita {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false)

        return ViewHolderReceita(layout)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolderReceita, position: Int) {
        holder.bind(dataSet[position])
    }

    // Alimentando com dados de fora da classe
    fun setData(mDataSet: List<Receita>) {
        dataSet = mDataSet ?: mutableListOf<Receita>()
        notifyDataSetChanged()
    }

    // Controlar os click no CardView fora da classe
    fun setListener(mListenet: RecyclerViewListener<Receita>) {
        listener = mListenet
    }

}