package com.algebra.kviz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val TOCNIH_KLJUC = "TOCNIH_KLJUC"
const val UKUPNO_KLJUC = "UKUPNO_KLJUC"

class RezultatActivity : AppCompatActivity() {
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_rezultat )

        val tocnih = intent?.getIntExtra( TOCNIH_KLJUC, 0 )
        val ukupno = intent?.getIntExtra( UKUPNO_KLJUC, 0 )

        if( tocnih!=null && ukupno!=null ) {
            findViewById<TextView>(R.id.tvRezultat)
                .setText("$tocnih/$ukupno (" + (tocnih * 100.0 / ukupno) + "%)")
        }
        findViewById< Button >( R.id.bZatvori ).setOnClickListener { finish( ) }
    }
}