package com.example.sayacprojem

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var gelenVeri : String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("com.example.sayacprojem", MODE_PRIVATE)

        gelenVeri = sharedPreferences.getString("kullanici","")
        textView2.text = "Girilen Veri: ${gelenVeri}"

        if (gelenVeri != null){
            textView2.text = "Girilen Veri: ${gelenVeri}"
        }
    }

    fun baslat(view:View){

        gelenVeri = editText.text.toString()

        if (gelenVeri == ""){

            val uyari2 = AlertDialog.Builder(this@MainActivity)
            uyari2.setTitle("Veri Hatası")
            uyari2.setMessage("Lütfen Geçerli Veri Giriniz!!")
            uyari2.setPositiveButton("TAMAM",DialogInterface.OnClickListener { dialogInterface, i ->  })
            uyari2.show()
        }
        else  {
            object : CountDownTimer(3000,1000){

                override fun onTick(p0: Long) {
                    textView.text = "Kalan Süre ${p0/1000}"
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity,"Veri Kaydedildi",Toast.LENGTH_SHORT).show()
                    gelenVeri = editText.text.toString()

                    val uyari = AlertDialog.Builder(this@MainActivity)
                    uyari.setTitle("VERİ KAYDEDİLDİ")
                    uyari.setMessage("Ne Yapmak İstersin?")
                    uyari.setPositiveButton("Veri Gönder",DialogInterface.OnClickListener { dialogInterface, i ->
                        gelenVeri = editText.text.toString()
                        val intent = Intent(applicationContext,MainActivity2::class.java)
                        val gidenVeri = intent.putExtra("kullanici",gelenVeri)
                        startActivity(intent)
                        Toast.makeText(this@MainActivity,"Veri Gönderildi",Toast.LENGTH_SHORT).show()
                    })
                    uyari.setNegativeButton("BEKLE",DialogInterface.OnClickListener { dialogInterface, i ->  })
                    uyari.setNeutralButton("YENİLE",DialogInterface.OnClickListener { dialogInterface, i ->
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@MainActivity,"Ekran Yenilendi",Toast.LENGTH_SHORT).show()
                    })
                    uyari.setNeutralButton("VERİYİ SİL",DialogInterface.OnClickListener { dialogInterface, i ->
                        sharedPreferences.edit().remove("kullanici").apply()
                        textView2.text = "Girilen Veri: "
                        Toast.makeText(this@MainActivity,"Veri Silindi",Toast.LENGTH_SHORT).show()
                    })
                    uyari.show()

                    sharedPreferences.edit().putString("kullanici",gelenVeri).apply()
                    textView2.text = "Girilen Veri: ${gelenVeri}"
                    }

            }.start()
        }


    }
}