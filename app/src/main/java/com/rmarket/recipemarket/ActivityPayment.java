package com.rmarket.recipemarket;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityPayment extends AppCompatActivity {
    CardView payment_Btn;
    EditText editCustomerName,editCustomerEmail,editCustomerPhone,editReceiverName,editReceiverAddressBig,editReceiverAdressSmall,editReceiverComment,editReceiverPhone;
    LinearLayout paymentLinear;
    ImageView payment_Back;

    TextView kakaoPay,mutongPay, tvProductName, tvProductPrice, tvDeliveryCost, tvAmountOfPayment;
    SoftKeyboard softKeyboard;
    CheckBox payAgree;
    String payMethod = ""; // 페이방법 선택시 스트링으로 저장

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        payment_Back = findViewById(R.id.payment_back);
        PaymentItem mPaymentItem = (PaymentItem)getIntent().getSerializableExtra("paymentitem");

        payment_Btn = findViewById(R.id.paymentBtn); //결제하기 버튼

        editCustomerName = findViewById(R.id.payment_Custumer); // 소비자 이름
        editCustomerEmail = findViewById(R.id.payment_Email); //소비자 이메일
        editCustomerPhone = findViewById(R.id.payment_PhoneNumber); //소비자 전화번호

        editReceiverName = findViewById(R.id.payment_Receiver_Name); //받는 사람 이름
        editReceiverPhone = findViewById(R.id.payment_Receiver_Phone); //받는 사람 전화번호
        editReceiverAddressBig = findViewById(R.id.payment_Receiver_AddressBig);    //큰주소
        editReceiverAdressSmall = findViewById(R.id.payment_Receiver_AddressSmall); //상세주소
        editReceiverComment = findViewById(R.id.payment_Receiver_Message);  //배송 메시지

        payment_Back = findViewById(R.id.payment_back);
        payment_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        kakaoPay = findViewById(R.id.payment_Kakao); //카카오결제 사용
        mutongPay = findViewById(R.id.payment_Mutong); // 무동장입급 사용

        payAgree = findViewById(R.id.payment_Checkbox); //결제 동의 체크 박스

        tvProductName = findViewById(R.id.tv_product_name);
        tvProductPrice = findViewById(R.id.tv_productprice_num);
        tvDeliveryCost = findViewById(R.id.tv_delivery_cost_num);
        tvAmountOfPayment = findViewById(R.id.tv_amount_of_payment_num);

        tvProductName.setText(mPaymentItem.getsProductName());
        tvProductPrice.setText("" + mPaymentItem.getiProductPrice());
        tvDeliveryCost.setText("" + mPaymentItem.getiDeliveryCost());
        tvAmountOfPayment.setText("" + (mPaymentItem.getiProductPrice() + mPaymentItem.getiDeliveryCost()));

        kakaoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    kakaoPay.setTextColor(getColor(R.color.colorWhite));
                    kakaoPay.setBackgroundColor(getColor(R.color.colorOrange));

                    mutongPay.setTextColor(getColor(R.color.colorModernBlack));
                    mutongPay.setBackgroundColor(getColor(R.color.gray));

                    payMethod = "Kakao";
            }
        });


        mutongPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mutongPay.setTextColor(getColor(R.color.colorWhite));
                mutongPay.setBackgroundColor(getColor(R.color.colorOrange));

                kakaoPay.setTextColor(getColor(R.color.colorModernBlack));
                kakaoPay.setBackgroundColor(getColor(R.color.gray));

                payMethod = "Mutong";

            }
        });



        payment_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(payAgree.isChecked())
                {

                    if(editCustomerName.getText().toString().equals("") || editCustomerEmail.getText().toString().equals("")| editCustomerPhone.getText().toString().equals("")|| editReceiverName.getText().toString().equals("")|| editReceiverPhone.getText().toString().equals("")|| editReceiverAddressBig.getText().toString().equals("")&& editReceiverAdressSmall.getText().toString().equals("")|| editReceiverComment.getText().toString().equals(""))
                    {
                        Toast.makeText(ActivityPayment.this, "공란없이 입력해주세요.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                       if(payMethod.equals(""))
                       {
                           Toast.makeText(ActivityPayment.this, "결제 방법을 선택해 주세요", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           // 서버 연동
                           Toast.makeText(ActivityPayment.this, "결제를 진행합니다.", Toast.LENGTH_SHORT).show();

                           if (payMethod.equals("Kakao")) {
                               UserManagement.getInstance().me(new MeV2ResponseCallback() {
                                   @Override
                                   public void onSessionClosed(ErrorResult errorResult) {

                                   }

                                   @Override
                                   public void onSuccess(MeV2Response result) {
                                       JSONObject jsonObject = new JSONObject();
                                       try {
                                           jsonObject.put("id", result.getId());
                                           jsonObject.put("product_name", mPaymentItem.getsProductName());
                                           jsonObject.put("quantity", mPaymentItem.getiQuantity());
                                           jsonObject.put("total_amount", mPaymentItem.getiProductPrice() + mPaymentItem.getiDeliveryCost());
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                       HttpConnection httpConn = new HttpConnection(ActivityPayment.this, "payment", jsonObject);
                                       httpConn.execute();
                                   }
                               });
                           }
                           else{
                               //무통장

                           }
                       }
                    }
                }
                else
                {
                    Toast.makeText(ActivityPayment.this, "상품, 가격, 배송정보에 동의해 주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });





        paymentLinear = (LinearLayout)findViewById(R.id.paymentLinear); //신경 안써도됨

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
