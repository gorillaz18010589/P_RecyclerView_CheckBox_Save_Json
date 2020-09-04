package com.example.p_recyclerview_checkbox_save_json.bankcardcheckbox;
//1.BankCardActivity => 目的：原本有預設的checkbox,到第二頁checkbox只有兩夜間切換,一但離開後又記住最開始

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.p_recyclerview_checkbox_save_json.R;
import com.example.p_recyclerview_checkbox_save_json.SharedPreferencesUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class BankCardActivity extends AppCompatActivity {
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        Log.v("hank","BankCardActivity ->onCreate");
        init();
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title);
        Log.v("hank","bankCardToken:" + SpUtils.getInstance(this).getBankCardToken());
    }

    public void toChooseBankCard(View view) {
        startActivity(new Intent(BankCardActivity.this,ChooseBankCardActivity.class));
    }

    //註冊
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    //接受選擇銀行卡傳回來的卡片
    @Subscribe(threadMode = ThreadMode.MainThread,sticky = true)
    public void getChooseBankCard(ChooserBankCard chooseBankCard){
        tvTitle.setText(chooseBankCard.getTitle());
        Log.v("hank","getChooseBankCard-> chooseBankCard title = " + chooseBankCard.getTitle() +" id = " +chooseBankCard.getId() );
    }

    //暫停時解除註冊
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //*在onDestroy清掉值這樣,重新進入選擇銀行卡頁面又會出現預設的銀行卡
        if(!SpUtils.getInstance(this).getBankCardToken().equals("fail")){
            SpUtils.getInstance(this).removeBankCardToken();
            Log.v("hank","BankCardActivity ->onDestroy" +SpUtils.getInstance(this).getBankCardToken());
        }
    }
}