package com.example.p_recyclerview_checkbox_save_json.bankcardcheckbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.p_recyclerview_checkbox_save_json.KVUtils;
import com.example.p_recyclerview_checkbox_save_json.SharedPreferencesUtil;

class SpUtils {
    private static SpUtils instance;
    private Context context;
    private SharedPreferences sharedPreferences;

    private String ACCOUNT_TOKEN = "Account";
    private String PASSWORD_TOKEN = "Password";
    private String FROM_BANK_CARD ="FROM_BANK_CARD1";
    private String JSON_TOKEN ="JSON1";

    public static SpUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SpUtils(context);
        }
        return instance;
    }

    public SpUtils(Context context) {
        this.context = context;
        //用這個可以無限多個spgetSharedPreferences("name",Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("name",Context.MODE_PRIVATE);
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setBankCardToken(String bankCardToken){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FROM_BANK_CARD, bankCardToken);
        editor.commit();
        Log.v(KVUtils.TAG_H, "setBankCardToken():" + sharedPreferences.getString(FROM_BANK_CARD, "fail"));
    }

    public String getBankCardToken(){
        return sharedPreferences.getString(FROM_BANK_CARD, "fail");
    }


    public void removeBankCardToken() {
        SharedPreferences.Editor reMoveBankToken = sharedPreferences.edit();
        reMoveBankToken.remove(FROM_BANK_CARD);
        reMoveBankToken.clear();
        reMoveBankToken.commit();
    }

    public void setAccount(String account) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCOUNT_TOKEN, account);
        editor.commit();
        Log.v(KVUtils.TAG_H, "setAccount():" + sharedPreferences.getString(ACCOUNT_TOKEN, "Account失敗"));
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD_TOKEN, password);
        editor.commit();

        Log.v(KVUtils.TAG_H, "setPassword():" + sharedPreferences.getString("Password", "Password失敗"));
    }

    public String getAccount() {
//        Log.v(KVUtils.TAG_H, "getAccount:" + sharedPreferences.getString(ACCOUNT_TOKEN, "Account失敗"));
        return sharedPreferences.getString(ACCOUNT_TOKEN, "fail");
    }

    public String getPassword() {
        Log.v(KVUtils.TAG_H, "getPassword:" + sharedPreferences.getString(PASSWORD_TOKEN, "Password失敗"));
        return sharedPreferences.getString(PASSWORD_TOKEN, "");
    }


    public void setJson(String json) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JSON_TOKEN, json);
        editor.commit();
        Log.v(KVUtils.TAG_H, "setJson():" + sharedPreferences.getString(JSON_TOKEN, "Password失敗"));
    }


    public String getJson() {
        Log.v(KVUtils.TAG_H, "getJson:" + sharedPreferences.getString(JSON_TOKEN, "Password失敗"));
        return sharedPreferences.getString(JSON_TOKEN, "");
    }
    public void removeAccountPwd() {
        //刪除帳號儲存

        SharedPreferences.Editor editorAccount = sharedPreferences.edit();
        editorAccount.remove(ACCOUNT_TOKEN);
        editorAccount.clear();
        editorAccount.commit();


        //刪除密碼儲存

        SharedPreferences.Editor editorAccPwd = sharedPreferences.edit();
        editorAccPwd.remove(PASSWORD_TOKEN);
        editorAccPwd.clear();
        editorAccPwd.commit();

        Log.v(KVUtils.TAG_H, "刪除帳號密碼儲存 =>" + "/n" + "帳號為空:" + sharedPreferences.getString("Account", "帳號為空失敗") + "/n 密碼為空:" + sharedPreferences.getString("Pwd", "密碼為空失敗"));

    }


    public void removeAccount() {
        //刪除帳號儲存

        SharedPreferences.Editor editorAccount = sharedPreferences.edit();
        editorAccount.remove(ACCOUNT_TOKEN);
        editorAccount.clear();
        editorAccount.commit();

    }
}
