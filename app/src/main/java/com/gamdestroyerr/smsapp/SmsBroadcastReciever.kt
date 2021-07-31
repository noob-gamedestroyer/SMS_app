package com.gamdestroyerr.smsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.gamdestroyerr.smsapp.databinding.ActivityMainBinding

class SmsBroadcastReceiver(private var _binding: ActivityMainBinding): BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        message.iterator().forEach{
            _binding.apply {
                smsPhoneNo.text = it.displayOriginatingAddress
                smsBody.text = it.displayMessageBody
            }
        }
    }
}