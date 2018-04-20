package govinmaxim.githubclient.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private final static String FILE_NAME = "preferences";
    private final static String PREF_LOGIN = "login";
    private final static String PREF_PASSWORD = "password";

    private SharedPreferences preferences;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, 0);
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    public void setLogin(String login) {
        getEditor().putString(PREF_LOGIN, login).commit();
    }

    public String getLogin() {
        return preferences.getString(PREF_LOGIN, "");
    }

    public void setPassword(String password) {
        getEditor().putString(PREF_PASSWORD, password).commit();
    }

    public String getPassword() {
        return preferences.getString(PREF_PASSWORD, "");
    }
}
