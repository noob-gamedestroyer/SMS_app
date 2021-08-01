package com.gamdestroyerr.smsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.gamdestroyerr.smsapp.databinding.ActivityMainBinding

class SmsBroadcastReceiver(
    private var _binding: ActivityMainBinding,
    private val mainActivityContext: Context
    ): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val builder = NotificationCompat.Builder(mainActivityContext, MainActivity.CHANNEL_ID)
        val notificationManager = NotificationManagerCompat.from(mainActivityContext)
        val intentMain = Intent(mainActivityContext, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(mainActivityContext).run {
            addNextIntentWithParentStack(intentMain)
            getPendingIntent(101, 0)
        }

        message.iterator().forEach{

            val notification = builder.setContentTitle(it.displayOriginatingAddress)
                .setContentText(it.displayMessageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_round_message_24)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(0, notification)

            _binding.apply {
                smsPhoneNo.text = it.displayOriginatingAddress
                smsBody.text = it.displayMessageBody
            }
        }
    }
}