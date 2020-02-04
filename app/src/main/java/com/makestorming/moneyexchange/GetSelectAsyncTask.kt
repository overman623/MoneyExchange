package com.makestorming.moneyexchange

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class GetSelectAsyncTask(
    private val language: String
) : AsyncTask<Void, Void,  Elements>() {

    override fun doInBackground(vararg p0: Void?): Elements {
        val doc: Document
//        val array: ArrayList<CharSequence> = ArrayList()
        val array = Elements()
        try {
            doc = Jsoup.connect("https://${language}exchange-rates.org")
                    .header("User-Agent", "Mozilla/5.0").get()
            return doc.select("select#currencyList option")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return array
    }

}