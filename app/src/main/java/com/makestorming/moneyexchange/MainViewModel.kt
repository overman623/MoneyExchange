package com.makestorming.moneyexchange

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.makestorming.moneyexchange.data.GetDataHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(Application()) {

    internal val toastMessage = SingleLiveEvent<ResourceString>()
    var adMob: MutableLiveData<Boolean> = MutableLiveData(false)
    var digit: MutableLiveData<String> = MutableLiveData()
    var before: MutableLiveData<String> = MutableLiveData()
    var after: MutableLiveData<String> = MutableLiveData()
    val fromText1: ObservableField<String> = ObservableField()
    val fromText2: ObservableField<String> = ObservableField()
    val fromText3: ObservableField<String> = ObservableField()
    val toText1: ObservableField<String> = ObservableField()
    val toText2: ObservableField<String> = ObservableField()
    val toText3: ObservableField<String> = ObservableField()
    val load: ObservableField<Boolean> = ObservableField(false)
    var language: String = ""

    private lateinit var mAdapter: ArrayAdapter<CharSequence>

    init {
        val dataHelper = GetDataHelper()
        dataHelper.setCallBack(object : Callback<JsonObject?> {
            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
            }

            override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                Log.d("test", response.body().toString()) //test ok
//                data.set(response.body()!!)
//                view.setImage(response.body()?.getAsJsonArray("profile_images"))
            }
        })
        dataHelper.start()
        /*list.forEach {
            if (it == Locale.getDefault().language) {
                language = Locale.getDefault().language + "."
                return@forEach
            }
        }
        val languages:ArrayList<CharSequence> = ArrayList()
        val task = GetSelectAsyncTask(language)
        task.execute().get()?.let {
            it.forEach{e ->
                languages.add(e.text())
            }
            mAdapter = ArrayAdapter(application, android.R.layout.simple_spinner_dropdown_item, languages)
        }?: run {
            testToastWithResourceStringId()
        }*/
        val languages:ArrayList<CharSequence> = ArrayList()
        mAdapter = ArrayAdapter(application, android.R.layout.simple_spinner_dropdown_item, languages)
    }

    fun getAdapter(): ArrayAdapter<CharSequence> {
        return mAdapter
    }

    fun startExchange() {
        digit.value?.apply {
            val start = getEngString(before.value!!)
            val end = getEngString(after.value!!)
//            Log.d("test", start + end + this)
            adMob.value = true
            val task = JsoupAsyncTask(language, start, end, this)
            task.execute().get()?.let {
                load.set(true)
                val fromAmount = it.select("span#ctl00_M_lblFromAmount")
                    .text() // <span id="ctl00_M_lblFromAmount">1.0000</span> //처음 입력한값
                val fromCode = it.select("span#ctl00_M_lblFromIsoCode")
                    .text() // <span id="ctl00_M_lblFromIsoCode">KRW</span>
                fromText1.set("$fromAmount $fromCode")

                val fromCurrencyName = it.select("span#ctl00_M_lblFromCurrencyName")
                    .text() // <span id="ctl00_M_lblFromCurrencyName">대한민국 원 (KRW)</span>
                fromText2.set(fromCurrencyName)
                val fromCurrencyRate = it.select("span#ctl00_M_lblConversion")
                    .text() // <span id="ctl00_M_lblConversion">1 KRW = 0.006376 GTQ</span> //결과 환전값
                fromText3.set(fromCurrencyRate)

                val toAmount = it.select("span#ctl00_M_lblToAmount")
                    .text() // <span id="ctl00_M_lblToAmount">0.006376</span> //나온 액수
                val toCode = it.select("span#ctl00_M_lblToIsoCode")
                    .text() // <span id="ctl00_M_lblToIsoCode">GTQ</span>
                toText1.set("$toAmount $toCode")

                val toCurrencyName = it.select("span#ctl00_M_lblToCurrencyName")
                    .text() // <span id="ctl00_M_lblToCurrencyName">대한민국 원 (KRW)</span>
                toText2.set(toCurrencyName)
                val toCurrencyRate = it.select("span#ctl00_M_lblInverseConvertion")
                    .text() // <span id="ctl00_M_lblInverseConvertion">1 GTQ = 156.83 KRW</span> //역환전
                toText3.set(toCurrencyRate)

            } ?: run {
                testToastWithResourceStringId()
            }
        }

    }

    private fun getEngString(value: String): String {
        val start = value.indexOf("(")
        val end = value.indexOf(")")
        return value.substring(start + 1, end)
    }

    fun onLanguageSpinnerItemSelected(
        parent: AdapterView<*>,
        view: View?,
        position: Int,
        id: Long
    ) {
        when (parent.id) {
            R.id.spinnerBefore -> {
                before.value = (mAdapter.getItem(position) as String)
            }
            R.id.spinnerAfter -> {
                after.value = (mAdapter.getItem(position) as String)
            }
        }
    }

    fun testToastWithResourceStringId() {
        toastMessage.value = IdResourceString(R.string.error)
    }

    fun testToastWithString() {
        toastMessage.value = TextResourceString("Hello")
    }

    fun testToastWithResourceStringIdAndParameter() {
        // Hello, %1$s! You have %2$d new messages.
        toastMessage.value = FormatResourceString(R.string.hello_args, arrayOf("Desmond", 5))
    }

}