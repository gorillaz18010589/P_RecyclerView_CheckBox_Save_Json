package com.example.p_recyclerview_checkbox_save_json;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Card> cards = new ArrayList<>();
    private List<BankCard> bankCards = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankCards.add(new BankCard("測試0",0));
        bankCards.add(new BankCard("測試1",1));
        bankCards.add(new BankCard("測試2",2));
        bankCards.add(new BankCard("測試3",3));
        bankCards.add(new BankCard("測試4",4));
        bankCards.add(new BankCard("測試5",5));
        Gson gson = new Gson();
        String bankCardJson = gson.toJson(bankCards);
        SharedPreferencesUtil.getInstance(this).setJson(bankCardJson);

        setRecyclerView();


    }

    private void setRecyclerView() {
        cards.add(new Card("測試1",true));
        cards.add(new Card("測試1",true));
        cards.add(new Card("測試1",true));
        cards.add(new Card("測試1",true));
        cards.add(new Card("測試1",true));
        cards.add(new Card("測試1",true));
        CheckBoxRecyclerViewAdapter checkBoxRecyclerViewAdapter = new CheckBoxRecyclerViewAdapter(this,cards);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(checkBoxRecyclerViewAdapter);
    }



    /*
    * ViewGroup.LayoutParams lp = rv.getLayoutParams();
if (list.size() > 4) {
    lp.height = DensityUtil.dip2px(mContext,32 * 4);
} else {
    lp.height = DensityUtil.dip2px(mContext,34 * list.size());
}
rv.setLayoutParams(lp);
*/
}
