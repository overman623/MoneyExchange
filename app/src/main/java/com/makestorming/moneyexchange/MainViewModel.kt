package com.makestorming.moneyexchange

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(Application()) {

    var digit: MutableLiveData<String> = MutableLiveData()
    var before: MutableLiveData<String> = MutableLiveData()
    var after: MutableLiveData<String> = MutableLiveData()

    val text: ObservableField<String> = ObservableField()

    private var beforeAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
        application,
        R.array.spinner, android.R.layout.simple_spinner_dropdown_item
    )

    private var afterAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
        application,
        R.array.spinner, android.R.layout.simple_spinner_dropdown_item
    )

    fun getBeforeAdapter(): ArrayAdapter<CharSequence> {
        return beforeAdapter
    }

    fun getAfterAdapter(): ArrayAdapter<CharSequence> {
        return afterAdapter
    }

    fun startExchange() {
        digit.value?.apply {
            val start = getEngString(before.value!!)
            val end = getEngString(after.value!!)
            val task = JsoupAsyncTask(start, end, this)
            text.set(task.execute().get()?.toString())
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
    ) { //  스피너의 선택 내용이 바뀌면 호출된다
        when (parent.id) {
            R.id.spinnerBefore -> {
                before.value = (beforeAdapter.getItem(position) as String)
            }
            R.id.spinnerAfter -> {
                after.value = (afterAdapter.getItem(position) as String)
            }
        }
    }


}


/*
<div id="ctl00_M_pnlResult" rendertonothing="True">
            <div class="col-xs-6 result-cur1">
              <dl>
                <dt>
                  <strong>
                   <span id="ctl00_M_lblFromAmount">1.0000</span>
                   <span id="ctl00_M_lblFromIsoCode">KRW</span>
                  </strong>
                </dt>
                <dd>
                  <span id="ctl00_M_lblFromCurrencyName">대한민국 원 (KRW)</span>
                </dd>
              </dl>
              <small class="conversion-wide-note">
                <span id="ctl00_M_lblConversion">1 KRW = 0.006376 GTQ</span>
              </small>
              <b class="equal"></b>
            </div>
            <div class="col-xs-6 result-cur2">
              <dl>
                <dt>
                  <strong>
                    <span id="ctl00_M_lblToAmount">0.006376</span>
                    <span id="ctl00_M_lblToIsoCode">GTQ</span>
                  </strong>
                </dt>
                <dd>
                  <span id="ctl00_M_lblToCurrencyName">과테말라 케트살 (GTQ)</span>
                </dd>
              </dl>
              <small class="conversion-wide-note">
                <span id="ctl00_M_lblInverseConvertion">1 GTQ = 156.83 KRW</span>
              </small>

              <small class="conversion-narrow-note hidden">
                <span id="ctl00_M_lblConversion2">1 KRW = 0.006376 GTQ</span>
                <span id="ctl00_M_lblInverseConvertion2">1 GTQ = 156.83 KRW</span>
              </small>
            </div>
        </div>
*/
