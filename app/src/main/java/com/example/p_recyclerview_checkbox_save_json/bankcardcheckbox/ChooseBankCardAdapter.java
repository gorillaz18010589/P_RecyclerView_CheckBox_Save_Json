package com.example.p_recyclerview_checkbox_save_json.bankcardcheckbox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p_recyclerview_checkbox_save_json.CheckBoxRecyclerViewAdapter;
import com.example.p_recyclerview_checkbox_save_json.R;
import com.example.p_recyclerview_checkbox_save_json.SharedPreferencesUtil;
import com.example.shape.DisplayUtil;
import com.meetsl.scardview.SCardView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

////1.目的：原本有預設的checkbox,到第二頁checkbox只有兩夜間切換,一但離開後又記住最開始
public class ChooseBankCardAdapter extends RecyclerView.Adapter<ChooseBankCardAdapter.ChooseBankCardViewHolder> {
    private List<ChooserBankCard> mData;
    private Context mContext;
    private int row_index = -1;
    private Activity mActivity;

    public ChooseBankCardAdapter(List<ChooserBankCard> mData, Context mContext, Activity activity) {
        this.mData = mData;
        this.mContext = mContext;
        this.mActivity = activity;


    }

    @NonNull
    @Override
    public ChooseBankCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_bank_card, null);
        ChooseBankCardViewHolder chooseBankCardViewHolder = new ChooseBankCardViewHolder(view);
        return chooseBankCardViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull ChooseBankCardViewHolder holder, final int position) {
        holder.tvTitle.setText(mData.get(position).getTitle());


        //設定checkbox樣式
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.new_check_box);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 100, 100); //右下設置寬高
        //设置CheckBox对象的位置，对应为左、上、右、下
        holder.checkBox.setCompoundDrawables(drawable, null, null, null);


        //＊＊這邊開始處理點選checkbox記住帳號,可以切換,不能複選
        Log.v("hank", "外部開頭->" + "位置=" + position + "帳號 ＝" + SharedPreferencesUtil.getInstance(mContext).getAccount());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            //按下去記住帳號,還有row_index位置,notifyDataSetChanged->會再重新跑一次
            @Override
            public void onClick(View v) {
                //1.當點下去將位置傳給row_index
                Log.v("hank", "onClick:" + row_index + "/" + position);
                row_index = position;

                //將選擇到的資料用EventBus,傳回到BankCard呈現,然後finish就是把這頁結束就會傳回上一頁
                ChooserBankCard chooserBankCard = new ChooserBankCard();
                chooserBankCard.setTitle(mData.get(position).getTitle());
                chooserBankCard.setId(mData.get(position).getId());
                EventBus.getDefault().postSticky(chooserBankCard);
                //記下來就可以在兩夜間切換看選擇到的卡片
                SpUtils.getInstance(mContext).setBankCardToken(String.valueOf(position));
                //2.notifyDataSetChanged();重新讓RecyclerView重跑
                notifyDataSetChanged();
                mActivity.finish();
            }
        });


        //3.如果點案的位置等於現在的位置按鈕打勾記住帳號
        if (row_index == position) {
            Log.v("hank", "row_index==position->" + "位置是 =" + position + "帳號:" + SharedPreferencesUtil.getInstance(mContext).getAccount());
            holder.checkBox.setChecked(true);
            setRadiusBg(holder.checkBox);
        }

        //如果一開始沒有記下選擇的卡就會跑一次,一開始記得
        if (SpUtils.getInstance(mContext).getBankCardToken().equals("fail")) {
            Log.v("hank","銀行卡token第一次進來fail");
            //如果初始畫的位置相等於記住帳號的位置的話,位置打勾
            if (position == Integer.parseInt(SharedPreferencesUtil.getInstance(mContext).getAccount())) {
                Log.v("hank","錯誤帳號："+SharedPreferencesUtil.getInstance(mContext).getAccount());
                holder.checkBox.setChecked(true);
                //反之如果初始畫的位置相等於記住帳號的位置的話,除了打勾的那個之外都不打勾
            } else if (position != Integer.parseInt(SharedPreferencesUtil.getInstance(mContext).getAccount())) {
                holder.checkBox.setChecked(false);
            }
        } else {
            //這邊是有選擇卡片後記憶位置
            if (position == Integer.parseInt(SpUtils.getInstance(mContext).getBankCardToken())) {
                holder.checkBox.setChecked(true);
            } else if (position != Integer.parseInt(SpUtils.getInstance(mContext).getBankCardToken())) {
                holder.checkBox.setChecked(false);
            }
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ChooseBankCardViewHolder extends RecyclerView.ViewHolder {
         TextView tvTitle;
        private CheckBox checkBox;
        private SCardView sCardView;

        public ChooseBankCardViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            checkBox = itemView.findViewById(R.id.cb_checked);
            sCardView = itemView.findViewById(R.id.sCard);
        }
    }



    //設定圓角背景自帶背景顏色
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setRadiusBg(View view) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(5);
        drawable.setStroke(DisplayUtil.px2dip(mContext, 10f), Color.BLACK);
//        drawable.setPadding(DisplayUtil.px2dip(context,10),DisplayUtil.px2dip(context,10),DisplayUtil.px2dip(context,),DisplayUtil.px2dip(context,10));
        drawable.setBounds(0, 0, 20, 20);
        drawable.setColor(mContext.getResources().getColor(R.color.colorAccent));
        view.setBackground(drawable);
    }
    //在checkBox旁邊加上一張自訂圖案
    private void setCheckBox(ChooseBankCardViewHolder c) {
        // 创建Drawable对象
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.checkbox);
        //设置drawable的位置,宽高
        drawable.setBounds(0, 0, 50, 50);
        //创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(drawable);
        //创建SpannableStringBuilder对象
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(".");
        //将imageSpan放入spannableStringBuilder中
        spannableStringBuilder.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置cb的文本,将spannableStringBuilder放入

        c.checkBox.setText(spannableStringBuilder);
    }


}
