package com.example.nfcemulatortag

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

class CardEmulationFragment: Fragment() {

    private var accounwt: EditText? = null
    private var sendData: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.main_fragment, container, false)

        accounwt = v.findViewById<View>(R.id.card_account_field) as EditText
        sendData = v.findViewById<View>(R.id.sentData) as Button

        accounwt?.setText(AccountStorage.GetAccount(activity))

        sendData?.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(accounwt?.text)) {
                Toast.makeText(requireContext(), "Вы ничего не ввели", Toast.LENGTH_LONG).show()
            } else {
                val mIntent = Intent(requireActivity(), KHostApduService::class.java)
                mIntent.putExtra("ndefMessage", accounwt?.text.toString())
                requireContext().startService(mIntent)
            }
        })

        accounwt?.doAfterTextChanged {
            val account = it.toString()
            AccountStorage.SetAccount(requireContext(), account)
        }
        return v
    }
}