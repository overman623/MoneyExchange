package com.makestorming.moneyexchange

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class GetSelectAsyncTask(
    private val language: String
) : AsyncTask<Void, Void,  ArrayList<CharSequence>>() {

    override fun doInBackground(vararg p0: Void?): ArrayList<CharSequence>? {
        val doc: Document
        val array: ArrayList<CharSequence> = ArrayList()
        try {

            doc =   Jsoup.connect("https://${language}exchange-rates.org")
                    .header("User-Agent", "Mozilla/5.0").get()
//            doc = Jsoup.connect("https://ko.exchange-rates.org").header("User-Agent", "Mozilla/5.0").get()


            Log.d("TEST", doc.select("div#ctl00_M_pnlResult").toString()) //test ok
//            Log.d("TEST", doc.select("select#currencyList").toString()) //test ok

            doc.select("select#currencyList").text().split(")").forEach {
                array.add("${it})")
            }

            return array
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}