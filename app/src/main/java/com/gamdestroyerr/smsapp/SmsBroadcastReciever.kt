package com.gamdestroyerr.smsapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.TypedArrayUtils.getString
import com.gamdestroyerr.smsapp.databinding.ActivityMainBinding

class SmsBroadcastReceiver(
    private var _binding: ActivityMainBinding,
    private val mainActivityContext: Context
    ): BroadcastReceiver() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {

        val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val builder = NotificationCompat.Builder(mainActivityContext, MainActivity.CHANNEL_ID)
        val notificationManager = NotificationManagerCompat.from(mainActivityContext)
        val intentMain = Intent(mainActivityContext, MainActivity::class.java)


        message.iterator().forEach{

            val pendingIntent = PendingIntent.getActivity(
                mainActivityContext,
                1,
                intentMain,
                PendingIntent.FLAG_UPDATE_CURRENT,
            )

            val notification = builder.setContentTitle(it.displayOriginatingAddress)
                .setContentText(it.displayMessageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_round_message_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(0, notification)

            _binding.apply {
                smsPhoneNo.text = mainActivityContext.getString(R.string.phone_no, it.displayOriginatingAddress)
                smsBody.text = mainActivityContext.getString(R.string.message, it.displayMessageBody)
            }
        }
    }
}