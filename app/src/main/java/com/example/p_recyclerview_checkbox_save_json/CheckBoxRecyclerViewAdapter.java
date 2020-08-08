package com.example.p_recyclerview_checkbox_save_json;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CheckBoxRecyclerViewAdapter extends RecyclerView.Adapter<CheckBoxRecyclerViewAdapter.CheckBoxViewHolder> {
    private Context context;
    private List<Card> mData;
    private int row_index = -1;
    private String json;
    private Gson gson = new Gson();
    private List<BankCard> bankCards;
    private boolean isChecked;
    private int checkIndex = -1;
    private String accounts;
    private int isNotfi = -2;
    public CheckBoxRecyclerViewAdapter(Context context, List<Card> mData) {
        this.context = context;
        this.mData = mData;
//        json = SharedPreferencesUtil.getInstance(context).getJson();
//        this.bankCards =  gson.fromJson(json,new TypeToken<List<BankCard>>(){}.getType());
//        this.account = SharedPreferencesUtil.getInstance(context).getAccount();
    }

    @NonNull
    @Override
    public CheckBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        CheckBoxViewHolder checkBoxViewHolder = new CheckBoxViewHolder(view);
        return checkBoxViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckBoxViewHolder holder, final int position) {
            holder.tvTitle.setText(mData.get(position).title);


            holder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //1.當點下去將位置傳給row_index
                     row_index = position;
                     Log.v("hank","onClick:" + row_index +"/" + position);
                     //2.notifyDataSetChanged();重新讓RecyclerView重跑

                    notifyDataSetChanged();
                }
            });

        //3.如果點案的位置等於現在的位置按鈕打勾,否則不打勾
        if (row_index == position) {
             holder.checkBox.setChecked(true);
             SharedPreferencesUtil.getInstance(context).removeAccount();
             SharedPreferencesUtil.getInstance(context).setAccount(String.valueOf(row_index));
             isChecked = true;
            Log.v("hank","很相等:" + row_index +"/" + position + "isCheckd:" + isChecked +"account:" + SharedPreferencesUtil.getInstance(context).getAccount());
        }
        else {
            holder.checkBox.setChecked(false);
            Log.v("hank","不相等:" + row_index +"/" + position);

        }


        if (isChecked) {
        } else {
            //抓到記住帳號的位置相等於這個item要處理的事情
            if (SharedPreferencesUtil.getInstance(context).getAccount() != null && holder.getLayoutPosition() == Integer.parseInt(SharedPreferencesUtil.getInstance(context).getAccount())) {
                    Log.v("hank", "位置相同 account:：" + SharedPreferencesUtil.getInstance(context).getAccount() +"adapterPosition:" +holder.getLayoutPosition());
                    holder.checkBox.setChecked(true);


            } else {
                //其他item
                holder.checkBox.setChecked(false);
                Log.v("hank", "位置不相同 account：" + accounts +"adapterPosition:" +holder.getAdapterPosition());
            }

        }


    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CheckBoxViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private CheckBox checkBox;
        public CheckBoxViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            checkBox = itemView.findViewById(R.id.cb_checked);
        }
    }
}
