package com.mehealth.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mehealth.user.User;
import com.google.gson.Gson;

/**
 * Class to manage the shared preferences.
 * Makes transferring the user class around activities easy.
 * @author Amin Karaoui
 */
public class SharedPref {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedPrefEditor;
    private Gson gson;

    /**
     * Initialize the SharedPref.
     * @param context Context of the application.
     */
    public SharedPref(Context context) {
        sharedPref = context.getSharedPreferences("com.mehealth_preferences", Activity.MODE_PRIVATE);
        sharedPrefEditor = this.sharedPref.edit();
        sharedPrefEditor.apply();
        gson = new Gson();
    }

    /**
     * Gets user from shared preferences.
     * Returns a default value user if there is no user in shared preferences.
     * @return User
     */
    public User getUser() {
        User defaultUser = new User();
        String defaultUserJson = gson.toJson(defaultUser);

        String userJson = sharedPref.getString("user", defaultUserJson);
        return gson.fromJson(userJson, User.class);
    }

    /**
     * Saves user into shared preferences.
     * @param user User class in use
     */
    public void saveUser(User user) {
        String json = gson.toJson(user);
        sharedPrefEditor.putString("user", json);
        sharedPrefEditor.commit();
    }

    /**
     * Gets a string from shared preferences with default value set empty.
     * @param key Key for shared preferences
     * @return Returns the string corresponding to the key
     */
    public String getString(String key) {
        return sharedPref.getString(key, "");
    }

    /**
     * Variation of getString where user decides the default value.
     * @param key          Key for shared preferences
     * @param defaultValue Default value set by user
     * @return String corresponding to the key
     */
    public String getString(String key, String defaultValue) {
        return sharedPref.getString(key, defaultValue);
    }

    /**
     * Puts string into shared preferences.
     * @param key  Key for the text
     * @param text Text to save
     */
    public void putString(String key, String text) {
        sharedPrefEditor.putString(key, text);
        sharedPrefEditor.commit();
    }
}