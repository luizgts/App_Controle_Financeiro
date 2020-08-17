package dev.celleptech.controlefinanceiro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.celleptech.controlefinanceiro.R
import dev.celleptech.controlefinanceiro.`interface`.RecyclerViewListener
import dev.celleptech.controlefinanceiro.categoriasDespesas
import dev.celleptech.controlefinanceiro.model.Despesa
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class RecyclerViewDespesaAdapter :
    RecyclerView.Adapter<RecyclerViewDespesaAdapter.ViewHolderDespesa>() {

    private lateinit var dataSet: List<Despesa>
    private lateinit var listener: RecyclerViewListener<Despesa>

    // Representa cada elementa no RecyclerView
    inner class ViewHolderDespesa(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dataSet: Despesa) {

            // Preenchendo os componentes do CardView
            itemView.textViewItemCategoria.text = categoriasDespesas[dataSet.categoria]
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDespesa {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false)

        return ViewHolderDespesa(layout)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolderDespesa, position: Int) {
        holder.bind(dataSet[position])
    }

    // Alimentando com dados de fora da classe
    fun setData(mDataSet: List<Despesa>) {
        dataSet = mDataSet ?: mutableListOf<Despesa>()
        notifyDataSetChanged()
    }

    // Controlar os click no CardView fora da classe
    fun setListener(mListenet: RecyclerViewListener<Despesa>) {
        listener = mListenet
    }

}