package com.algebra.kviz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
/*
Dopuniti aplikaciju sa funkcionalnoscu spremanja odgovora
Napraviti ljepši GUI
Što god se drugo sjetite
 */
class MainActivity : AppCompatActivity( ) {

    val TAG = "MainActivity"
    val TRENUTNO_PITANJE = "TRENUTNO_PITANJE"

    private var index = 0
    private lateinit var tvPitanje : TextView
    private lateinit var tvOdabraniOdgovor : TextView
    private lateinit var bDA : Button
    private lateinit var bNE : Button
    private lateinit var bPrethodno : Button
    private lateinit var bSljedece : Button
    private lateinit var bProvjera : Button
    private val pitanja:MutableList< Pitanje > = mutableListOf< Pitanje >()

    override fun onCreate( savedInstanceState: Bundle? ) {
        Log.d( TAG, "onCreate" ) // v - verbose; d - debug; i - info; w - warning, e - error
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )
        initWidgets( )
        setupListeners( )
        dohvatiPitanja( )
        if( savedInstanceState!=null && savedInstanceState.containsKey( TRENUTNO_PITANJE ) )
            index = savedInstanceState.getInt( TRENUTNO_PITANJE )
        pokaziPitanje( index )
    }

    private fun initWidgets( ) {
        tvPitanje = findViewById( R.id.tvPitanje )
        tvOdabraniOdgovor = findViewById( R.id.tvOdabraniOdgovor )
        bDA        = findViewById( R.id.bDA )
        bNE        = findViewById( R.id.bNE )
        bPrethodno = findViewById( R.id.bPrethodno )
        bSljedece  = findViewById( R.id.bSljedece )
        bProvjera  = findViewById( R.id.bBrojTocnihOdgovora )
    }

    private fun setupListeners( ) {
        bDA.setOnClickListener {
            val p = pitanja[index]
            p.odgovor = "DA"
            pokaziPitanje( index+1 )
        }
        bNE.setOnClickListener {
            val p = pitanja[index]
            p.odgovor = "NE"
            pokaziPitanje( index+1 )
        }
        bPrethodno.setOnClickListener { pokaziPitanje( index - 1 ) }
        bSljedece.setOnClickListener { pokaziPitanje( index + 1 ) }
        bProvjera.setOnClickListener {
            /*
            var brojTocnih = 0
            for( p in pitanja )
                if( p.odgovor==p.tocanOdgovor )
                    brojTocnih++
            Toast.makeText( this, "$brojTocnih / ${ pitanja.size }", Toast.LENGTH_LONG ).show( )
            */
            val brojTocnih = pitanja.filter { p -> p.odgovor==p.tocanOdgovor }.size
            val startSecondActivity = Intent( this, RezultatActivity::class.java ).apply {
                putExtra( TOCNIH_KLJUC, brojTocnih )
                putExtra( UKUPNO_KLJUC, pitanja.size )
            }
            startActivity( startSecondActivity )
        }
    }

    private fun dohvatiPitanja( ) {
        val p = resources.getStringArray( R.array.pitanja )
        val o = resources.getStringArray( R.array.odgovori )
        for( i in 0..p.size-1 )
            pitanja.add( Pitanje( p[i], o[i], "" ))
    }

    private fun pokaziPitanje( poz:Int ) {
        index = ( ( poz % pitanja.size ) + pitanja.size ) % pitanja.size
        val p = pitanja[ index ]
        tvPitanje.text = p.pitanje
        if( p.odgovor=="" ) {
            tvOdabraniOdgovor.text = "Na ovo pitanje još niste odgovorili"
            tvOdabraniOdgovor.setTextColor( Color.RED )
        } else {
            tvOdabraniOdgovor.text = "Na ovo pitanje odgovorili ste: ${p.odgovor}"
            tvOdabraniOdgovor.setTextColor( Color.GREEN )
        }
    }

    override fun onStart( ) {
        super.onStart( )
        Log.d( TAG, "onStart" )
    }

    override fun onResume( ) {
        super.onResume( )
        Log.d( TAG, "onResume" )
    }

    override fun onPause( ) {
        super.onPause( )
        Log.d( TAG, "onPause" )
    }

    override fun onStop( ) {
        super.onStop( )
        Log.d( TAG, "onStop" )
    }

    override fun onDestroy( ) {
        super.onDestroy( )
        Log.d( TAG, "onDestroy" )
    }

    override fun onSaveInstanceState( outState: Bundle ) {
        super.onSaveInstanceState( outState )
        outState.putInt( TRENUTNO_PITANJE, index )
    }
}

data class Pitanje( val pitanje:String, val tocanOdgovor:String, var odgovor:String )