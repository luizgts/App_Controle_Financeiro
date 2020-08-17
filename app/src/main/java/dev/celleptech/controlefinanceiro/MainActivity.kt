package dev.celleptech.controlefinanceiro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.celleptech.controlefinanceiro.view.DespesasFragment
import dev.celleptech.controlefinanceiro.view.HomeFragment
import dev.celleptech.controlefinanceiro.view.ReceitasFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationMain.setOnNavigationItemSelectedListener { item ->

            when(item.itemId) {

                R.id.nav_home -> {
                    val fragment = HomeFragment.newInstance()
                    openFragment(fragment)
                }

                R.id.nav_receitas -> {
                    val fragment = ReceitasFragment.newInstance()
                    openFragment(fragment)
                }

                R.id.nav_despesas -> {
                    val fragment = DespesasFragment.newInstance()
                    openFragment(fragment)
                }

            }

            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentMain, fragment)
        fragmentManager.commit()
    }
}