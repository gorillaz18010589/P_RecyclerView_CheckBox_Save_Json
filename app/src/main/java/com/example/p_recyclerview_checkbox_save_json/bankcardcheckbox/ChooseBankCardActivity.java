package com.example.p_recyclerview_checkbox_save_json.bankcardcheckbox;
//1.目的：原本有預設的checkbox,到第二頁checkbox只有兩夜間切換,一但離開後又記住最開始
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.p_recyclerview_checkbox_save_json.BankCard;
import com.example.p_recyclerview_checkbox_save_json.Card;
import com.example.p_recyclerview_checkbox_save_json.R;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ChooseBankCardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ChooserBankCard> bankCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_card);

        Log.v("hank","ChooseBankCardActivity ->onCreate");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bankCards.add(new ChooserBankCard("測試0", 0));
        bankCards.add(new ChooserBankCard("測試1", 1));
        bankCards.add(new ChooserBankCard("測試2", 2));
        bankCards.add(new ChooserBankCard("測試3", 3));
        bankCards.add(new ChooserBankCard("測試4", 4));
        bankCards.add(new ChooserBankCard("測試5", 5));


        ChooseBankCardAdapter chooseBankCardAdapter = new ChooseBankCardAdapter(bankCards,this,this);
        recyclerView.setAdapter(chooseBankCardAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("hank","ChooseBankCardActivity ->onDestroy");
    }
}