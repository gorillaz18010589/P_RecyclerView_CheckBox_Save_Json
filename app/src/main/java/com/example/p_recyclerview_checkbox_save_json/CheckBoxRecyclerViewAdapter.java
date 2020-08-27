package com.example.p_recyclerview_checkbox_save_json;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shape.DisplayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meetsl.scardview.SCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CheckBoxRecyclerViewAdapter extends RecyclerView.Adapter<CheckBoxRecyclerViewAdapter.CheckBoxViewHolder> {
    private Context context;
    private List<Card> mData;
    private int row_index = -1;
    private Gson gson = new Gson();


    public CheckBoxRecyclerViewAdapter(Context context, List<Card> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CheckBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        CheckBoxViewHolder checkBoxViewHolder = new CheckBoxViewHolder(view);
        return checkBoxViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckBoxViewHolder holder, final int position) {
        holder.tvTitle.setText(mData.get(position).title);


        //設定checkbox樣式
        Drawable drawable = context.getResources().getDrawable(R.drawable.new_check_box);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 100, 100); //右下設置寬高
        //设置CheckBox对象的位置，对应为左、上、右、下
        holder.checkBox.setCompoundDrawables(drawable, null, null, null);


        //＊＊這邊開始處理點選checkbox記住帳號,可以切換,不能複選
        Log.v("hank", "外部開頭->" + "位置=" + position + "帳號 ＝" + SharedPreferencesUtil.getInstance(context).getAccount());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            //按下去記住帳號,還有row_index位置,notifyDataSetChanged->會再重新跑一次
            @Override
            public void onClick(View v) {
                //1.當點下去將位置傳給row_index
                row_index = position;
                SharedPreferencesUtil.getInstance(context).removeAccount();
                SharedPreferencesUtil.getInstance(context).setAccount(String.valueOf(row_index));
                Log.v("hank", "onClick:" + row_index + "/" + position);
                //2.notifyDataSetChanged();重新讓RecyclerView重跑
                notifyDataSetChanged();
            }
        });


        //3.如果點案的位置等於現在的位置按鈕打勾記住帳號
        if (row_index == position) {
            Log.v("hank", "row_index==position->" + "位置是 =" + position + "帳號:" + SharedPreferencesUtil.getInstance(context).getAccount());
            holder.checkBox.setChecked(true);
            setRadiusBg(holder.checkBox);
        }

        //如果初始畫的位置相等於記住帳號的位置的話,位置打勾
        if (position == Integer.parseInt(SharedPreferencesUtil.getInstance(context).getAccount())) {
            Log.v("hank", "測試->" + "位置 =" + position + "/記住帳號 =" + SharedPreferencesUtil.getInstance(context).getAccount());
            holder.checkBox.setChecked(true);
            //反之如果初始畫的位置相等於記住帳號的位置的話,除了打勾的那個之外都不打勾
        } else if (position != Integer.parseInt(SharedPreferencesUtil.getInstance(context).getAccount())) {
            holder.checkBox.setChecked(false);
            Log.v("hank", "測試false->" + "位置 =" + position + "/記住帳號 =" + SharedPreferencesUtil.getInstance(context).getAccount());

        }
        Log.v("hank", "外部結尾->" + "位置=" + position + "帳號 ＝" + SharedPreferencesUtil.getInstance(context).getAccount());

    }

    //在checkBox旁邊加上一張自訂圖案
    private void setCheckBox(@NonNull CheckBoxViewHolder holder) {
        // 创建Drawable对象
        Drawable drawable = context.getResources().getDrawable(R.drawable.checkbox);
        //设置drawable的位置,宽高
        drawable.setBounds(0, 0, 50, 50);
        //创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(drawable);
        //创建SpannableStringBuilder对象
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(".");
        //将imageSpan放入spannableStringBuilder中
        spannableStringBuilder.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置cb的文本,将spannableStringBuilder放入
        holder.checkBox.setText(spannableStringBuilder);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CheckBoxViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private CheckBox checkBox;
        private SCardView sCardView;

        public CheckBoxViewHolder(@NonNull View itemView) {
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
        drawable.setStroke(DisplayUtil.px2dip(context, 10f), Color.BLACK);
//        drawable.setPadding(DisplayUtil.px2dip(context,10),DisplayUtil.px2dip(context,10),DisplayUtil.px2dip(context,),DisplayUtil.px2dip(context,10));
        drawable.setBounds(0, 0, 20, 20);
        drawable.setColor(context.getResources().getColor(R.color.colorAccent));
        view.setBackground(drawable);
    }

}
