package com.ptithcm.managernhaxe.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPreferences pref;

    public static void init(Context context) {
        if (pref == null) {
            pref = context
                    .getApplicationContext()
                    .getSharedPreferences("parking_pref", Context.MODE_PRIVATE);
        }
    }

    public static void saveToken(String token) {
        if (pref != null) {
            pref.edit().putString("token", token).apply();
        }
    }

    public static String getToken() {
        if (pref == null) {
            return "";
        }
        return pref.getString("token", "");
    }

    public static void saveUser(String name, String email, String role) {
        if (pref != null) {
            pref.edit()
                    .putString("name", name)
                    .putString("email", email)
                    .putString("role", role)
                    .apply();
        }
    }

    public static String getName() {
        if (pref == null) {
            return "";
        }
        return pref.getString("name", "");
    }

    public static String getEmail() {
        if (pref == null) {
            return "";
        }
        return pref.getString("email", "");
    }

    public static String getRole() {
        if (pref == null) {
            return "USER";
        }
        return pref.getString("role", "USER");
    }

    public static void logout() {
        if (pref != null) {
            pref.edit().clear().apply();
        }
    }
}