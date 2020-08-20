package com.example.p_recyclerview_checkbox_save_json;
/*新增當自太多時,超過多少字...
*           android:singleLine="true" //單行顯示
            android:maxEms="8" //最多8個字元
            android:ellipsize="end" //點點的位置從哪邊開始
*
* */
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Card> cards = new ArrayList<>();
    private List<BankCard> bankCards = new ArrayList<>();
    private String msg = "童仲彥在擔任台北市議會第11屆議員期間，假裝聘用蔡博安為公費助理，蔡提供銀行帳戶存摺、提款卡及身分證影本供童使用，童指示不知情的康姓助理製作並提出2011年10月31日、12月20日聘用蔡博安為公費助理的聘書和台北市議會議員自聘公費助理遴聘（異動）表，將蔡掛名在他的自聘公費助理名單中。\n" +
            "\n" +
            "台北市議會的職員被蒙在鼓裡，也無實質審查權限，將童仲彥「聘用」蔡博安一事記在2011年11月起至2012年2月自聘公費助理酬金清冊、自聘公費助理春節慰勞金清冊，並匯款12萬3842元到蔡的戶頭。這些錢在入帳後，童再指示康領出，當作私聘陳姓女助理每月1萬8千元的薪資支出，4個月下來共詐領了5萬1842元（12萬3842元-1萬8000元×4＝5萬1842元）。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankCards.add(new BankCard(msg, 0));
        bankCards.add(new BankCard("測試1", 1));
        bankCards.add(new BankCard("測試2", 2));
        bankCards.add(new BankCard("測試3", 3));
        bankCards.add(new BankCard("測試4", 4));
        bankCards.add(new BankCard("測試5", 5));
        Gson gson = new Gson();
        String bankCardJson = gson.toJson(bankCards);
        SharedPreferencesUtil.getInstance(this).setJson(bankCardJson);

        setRecyclerView();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setRecyclerView() {
        cards.add(new Card(msg, true));
        cards.add(new Card("測試1", true));
        cards.add(new Card("測試1", true));
        cards.add(new Card("測試1", true));
        cards.add(new Card("測試1", true));
        cards.add(new Card("測試1", true));
        final CheckBoxRecyclerViewAdapter checkBoxRecyclerViewAdapter = new CheckBoxRecyclerViewAdapter(this, cards);

        recyclerView = findViewById(R.id.recyclerView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int firstCompletelyVisibleItemPosition =
                        linearLayoutManager.findFirstCompletelyVisibleItemPosition(); //取得第一個完全顯示在螢幕內的view item位置
                int findFirstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition(); //取得第一個不完全顯示在螢幕內的view item位置
                int findLastCompletelyVisibleItemPosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition(); //取得最後一個完全顯示在螢幕內的view item位置
                int findLastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition(); //取得最後一個不完全顯示在螢幕內的view item位置
//                Log.v("hank",
//                        "firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition + "\n" +
//                                "findFirstVisibleItemPosition:" + findFirstVisibleItemPosition + "\n" +
//                                "findLastCompletelyVisibleItemPosition:" + findLastCompletelyVisibleItemPosition + "\n" +
//                                "findLastVisibleItemPosition:" + findLastVisibleItemPosition
//                ) ;

                //如果看到第五個item
                if (linearLayoutManager.findLastVisibleItemPosition() == 5) {

//                    ViewGroup.LayoutParams layoutParams = recyclerView.getChildAt(0).getLayoutParams();
//                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.linear_item,null));
//                    layoutParams.width = (ScreenUtils.getScreenWidth(MainActivity.this) - DpPxUtils.dp2Px(MainActivity.this, 70)) / 3;//

                    dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0)); //強制停止滑動
                    Log.v("hank", "已出現超過5");
                    recyclerView.setNestedScrollingEnabled(false);
                    checkBoxRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    Log.v("hank", "沒出現");

                }

            }

            ;
        });

        recyclerView.setAdapter(checkBoxRecyclerViewAdapter);

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
}