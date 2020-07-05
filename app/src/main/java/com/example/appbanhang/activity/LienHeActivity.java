package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;

public class LienHeActivity extends AppCompatActivity {
    TextView txt_diachi;
    Button btn_call;
//    String tel = "0123456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        anhxa();
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = btn_call.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    String dial = "tel:" + phone;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else {
                    Toast.makeText(LienHeActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhxa() {
        txt_diachi = findViewById(R.id.txt_diachi);
        btn_call = findViewById(R.id.btn_call);
    }
}
























//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button mDialButton = (Button) findViewById(R.id.btn_dial);
//        final EditText mPhoneNoEt = (EditText) findViewById(R.id.et_phone_no);
//
//        mDialButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phoneNo = mPhoneNoEt.getText().toString();
//                if(!TextUtils.isEmpty(phoneNo)) {
//                    String dial = "tel:" + phoneNo;
//                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
//                }else {
//                    Toast.makeText(MainActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//}
