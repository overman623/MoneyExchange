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

    private var mAdapter: ArrayAdapter<CharSequence> =
        ArrayAdapter.createFromResource(
            application,
            R.array.spinner,
            android.R.layout.simple_spinner_dropdown_item
        )


    fun getAdapter(): ArrayAdapter<CharSequence> {
        return mAdapter
    }

    fun startExchange() {
        if (digit.value?.toFloat()!! < 0) {
            testToastWithResourceStringId2()
            return
        }

        digit.value?.apply {
            val start = getEngString(before.value!!)
            val end = getEngString(after.value!!)
            Log.d("test", start + end) //test ok
            val dataHelper = GetDataHelper(start, end)
            dataHelper.setCallBack(object : Callback<JsonObject?> {
                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                    testToastWithResourceStringId()
                }

                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                    load.set(true)
                    response.body()?.getAsJsonObject("rates")?.let {
                        val from = it.getAsJsonPrimitive(start)
                        val to = it.getAsJsonPrimitive(end)
                        val result = (to.asFloat * digit.value!!.toFloat()) / from.asFloat

                        fromText1.set(before.value!!)
                        fromText2.set(digit.value!!)
                        fromText3.set("${1} : ${String.format("%,.4f", to.asFloat / from.asFloat)}")

                        toText1.set(after.value!!)
                        toText2.set("$result")
                        toText3.set("${1} : ${String.format("%,.4f", from.asFloat / to.asFloat)}")

                    }

                }
            })
            dataHelper.start()
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

    fun testToastWithResourceStringId2() {
        toastMessage.value = IdResourceString(R.string.minus)
    }

    fun testToastWithString() {
        toastMessage.value = TextResourceString("Hello")
    }

    fun testToastWithResourceStringIdAndParameter() {
        // Hello, %1$s! You have %2$d new messages.
        toastMessage.value = FormatResourceString(R.string.hello_args, arrayOf("Desmond", 5))
    }

}