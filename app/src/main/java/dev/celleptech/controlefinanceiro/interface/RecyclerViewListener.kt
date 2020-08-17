package dev.celleptech.controlefinanceiro.`interface`

import android.view.View

/*
* Esta interface foi criada para nos permitir
* controlar os eventos de clique que ocorrem dentro
* do adaptador.
*
* Como esta interface será utilizada tanto
* no adaptador de Receitas quando o Adaptador de Despesas
* estamos utilizando a técnica de tipos genéricos
* */
interface RecyclerViewListener<T> {

    fun onClick(v: View, dataSet: T)

    fun onLongClick(v: View, dataSet: T)

}