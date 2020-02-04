package com.makestorming.moneyexchange

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class JsoupAsyncTask(
    private val language: String,
    private val before: String,
    private val after: String,
    private val digit: String
) : AsyncTask<Void, Void, Elements>() {

    override fun doInBackground(vararg p0: Void?): Elements? {
        val doc: Document
        try {

            doc =   Jsoup.connect("https://${language}exchange-rates.org/converter/${before}/${after}/${digit}/Y")
                    .header("User-Agent", "Mozilla/5.0").get()
//            doc = Jsoup.connect("https://ko.exchange-rates.org").header("User-Agent", "Mozilla/5.0").get()


//            Log.d("TEST", doc.select("div#ctl00_M_pnlResult").toString()) //test ok
//            Log.d("TEST", doc.select("select#currencyList").toString()) //test ok
            return doc.select("div#ctl00_M_pnlResult")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}