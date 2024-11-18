package com.example.hiskaart

import android.content.Intent
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import android.os.Vibrator
import android.os.VibrationEffect
import android.content.Context

class MyHostApduService : HostApduService() {

    // Уникальный идентификатор карты
    private val UNIQUE_CARD_ID = "123456789ABCDEF0".toByteArray() + byteArrayOf(0x90.toByte(), 0x00.toByte())


    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        // Логируем полученную команду
        val command = commandApdu?.joinToString(" ") { String.format("%02X", it) } ?: "Нет команды"
        Log.d("HCE", "Получена команда: $command")

        // Проверяем, соответствует ли команда SELECT AID
        if (commandApdu != null && commandApdu.contentEquals(SELECT_AID)) {
            Log.d("HCE", "Команда SELECT AID распознана. Отправляем уникальный идентификатор карты.")

            vibratePhone()
            return UNIQUE_CARD_ID // Возвращаем уникальный идентификатор карты
        }

        // Если команда не распознана
        Log.d("HCE", "Неизвестная команда. Возвращаем ответ UNKNOWN_CMD.")
        return UNKNOWN_CMD
    }

    override fun onDeactivated(reason: Int) {
        // Логируем завершение взаимодействия
        val reasonString = when (reason) {
            DEACTIVATION_LINK_LOSS -> "Потеря связи"
            DEACTIVATION_DESELECTED -> "Деактивация (deselected)"
            else -> "Неизвестная причина ($reason)"
        }
        Log.d("HCE", "Сервис отключён. Причина: $reasonString")
    }

    private fun vibratePhone() {
        try {
            val vibrator = getSystemService(Vibrator::class.java)
            if (vibrator != null && vibrator.hasVibrator()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    // Для API 26 и выше
                    val effect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
                    vibrator.vibrate(effect)
                } else {
                    // Для API ниже 26
                    @Suppress("DEPRECATION") // Подавляем предупреждение
                    vibrator.vibrate(200)
                }
            }
        } catch (e: Exception) {
            Log.e("HCE", "Ошибка вибрации: ${e.message}")
        }
    }

    companion object {
        // Стандартная SELECT AID APDU команда
        private val SELECT_AID = byteArrayOf(
            0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(), 0x07.toByte(),
            0xF0.toByte(), 0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x06.toByte(), 0x00.toByte()
        )

        // Ответ на неизвестные команды
        private val UNKNOWN_CMD = byteArrayOf(0x6F.toByte(), 0x00.toByte())
    }
}