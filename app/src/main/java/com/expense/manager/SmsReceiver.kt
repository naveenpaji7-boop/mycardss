package com.expense.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            val bundle: Bundle? = intent.extras
            try {
                val pdus = bundle?.get("pdus") as? Array<*>
                pdus?.forEach { _ ->
                    val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)[0]
                    val address = message.displayOriginatingAddress
                    val body = message.displayMessageBody
                    val timestamp = message.timestampMillis
                    Log.d("SmsReceiver", "SMS from $address: $body at $timestamp")
                    // TODO: Upload this SMS to Firebase
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Exception in SMS receiver", e)
            }
        }
    }
}