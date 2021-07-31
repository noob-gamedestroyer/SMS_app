package com.gamdestroyerr.smsapp

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gamdestroyerr.smsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: SmsBroadcastReceiver

    private var appPermission = arrayOf(
        Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECEIVE_SMS
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, appPermission, SMS_REQUEST_CODE)
        }

        receiver = SmsBroadcastReceiver(binding)
        IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION).also {
            registerReceiver(receiver, it)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Companion.SMS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("TAG", "Permission has been Granted by user")
                } else {
                    Log.d("TAG", "Permission has been denied by user")
                }
                return
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    companion object {
        private const val SMS_REQUEST_CODE = 100
    }
}