package com.example.shape;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

public class SelectorCheckBox  extends AppCompatCheckBox {
    public SelectorCheckBox(@NonNull Context context) {
        super(context);
    }

    public SelectorCheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectorCheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
