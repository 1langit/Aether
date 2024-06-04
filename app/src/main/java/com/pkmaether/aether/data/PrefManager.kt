package com.pkmaether.aether.data

import android.content.Context
import android.content.SharedPreferences

class PrefManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_FILENAME = "preferences"
        private const val KEY_UID = "uid"
        private const val KEY_EMAIL = "email"
        private const val KEY_COMPANY = "companyName"
        private const val KEY_TYPE = "industryType"
        private const val KEY_ADDRESS = "address"
        private const val KEY_PHONE = "phone"
        private const val KEY_NOTIFICATION = "timer"

        @Volatile
        private var instance: PrefManager? = null

        fun getInstance(context: Context) : PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    fun saveUid(uid: String) {
        sharedPreferences.edit().putString(KEY_UID, uid).apply()
    }
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply()
    }
    fun saveCompanyName(name: String) {
        sharedPreferences.edit().putString(KEY_COMPANY, name).apply()
    }
    fun saveIndustryType(type: String) {
        sharedPreferences.edit().putString(KEY_TYPE, type).apply()
    }
    fun saveAddress(address: String) {
        sharedPreferences.edit().putString(KEY_ADDRESS, address).apply()
    }
    fun savePhone(phone: String) {
        sharedPreferences.edit().putString(KEY_PHONE, phone).apply()
    }
    fun setNotificationTimer(index: Int) {
        sharedPreferences.edit().putInt(KEY_NOTIFICATION, index).apply()
    }

    fun getUid(): String {
        return sharedPreferences.getString(KEY_UID, "") ?: ""
    }
    fun getEmail(): String {
        return sharedPreferences.getString(KEY_EMAIL, "") ?: ""
    }
    fun getCompanyName(): String {
        return sharedPreferences.getString(KEY_COMPANY, "") ?: ""
    }
    fun getIndustryType(): String {
        return sharedPreferences.getString(KEY_TYPE, "") ?: ""
    }
    fun getAddress(): String {
        return sharedPreferences.getString(KEY_ADDRESS, "") ?: ""
    }
    fun getPhone(): String {
        return sharedPreferences.getString(KEY_PHONE, "") ?: ""
    }
    fun getNotificationTimer(): Int {
        return sharedPreferences.getInt(KEY_NOTIFICATION, -1)
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}