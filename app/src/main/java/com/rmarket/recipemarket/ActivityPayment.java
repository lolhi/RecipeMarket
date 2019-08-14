package com.rmarket.recipemarket;

import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityPayment extends AppCompatActivity {
    CardView payment_Btn;
    EditText editCustumer;
    LinearLayout paymentLinear;
    SoftKeyboard softKeyboard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payment_Btn = findViewById(R.id.paymentBtn);
        editCustumer = findViewById(R.id.payment_Custumer);

        paymentLinear = (LinearLayout)findViewById(R.id.paymentLinear);

        InputMethodManager controlManager = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        softKeyboard = new SoftKeyboard(paymentLinear, controlManager);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged()
        {
            @Override
            public void onSoftKeyboardHide()
            {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @Override
                    public void run()
                    {
//키보드 내려왔을때
                       payment_Btn.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onSoftKeyboardShow()
            {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @Override
                    public void run()
                    {
//키보드 올라왔을때
                        payment_Btn.setVisibility(View.GONE);
                    }
                });
            }
        });




    }
}
