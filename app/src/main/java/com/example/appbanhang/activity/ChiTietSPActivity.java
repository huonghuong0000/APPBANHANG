package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSPActivity extends AppCompatActivity {

    Toolbar toolbarChitietSP;
    ImageView imageViewCTSP;
    TextView txtname_CTSP, txtprice_CTSP, txtdetail_CTSP;
    Spinner spinnerCTSP;
    Button buttonCTSP;
    int id=0;
    String name_CTSP = "";
    int price_CTSP=0;
    String image_CTSP="";
    String detail_CTSP="";
    int id_lsp =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        anhxa();
        Actiontoolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void EventButton() {
        buttonCTSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update giỏ hàng
                if(MainActivity.arr_Giohang.size() > 0){
                    int sl = Integer.parseInt(spinnerCTSP.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i=0; i<MainActivity.arr_Giohang.size();i++){
                        if(MainActivity.arr_Giohang.get(i).getId_sp() == id){
                            MainActivity.arr_Giohang.get(i).setSoluong_sp(MainActivity.arr_Giohang.get(i).getSoluong_sp() + sl);
                            if (MainActivity.arr_Giohang.get(i).getSoluong_sp() >=10) {
                                MainActivity.arr_Giohang.get(i).setSoluong_sp(10);
                            }
                            MainActivity.arr_Giohang.get(i).setPrice_sp(price_CTSP * MainActivity.arr_Giohang.get(i).getSoluong_sp());
                            exists = true;
                        }
                        //ko update/ khỏi tạo - đưa gtri mới vào arr
                    }
                    if ( exists == false){
                        int soluong = Integer.parseInt(spinnerCTSP.getSelectedItem().toString());
                        long price_moi = soluong * price_CTSP;
                        MainActivity.arr_Giohang.add(new GioHang(id, name_CTSP, price_moi,image_CTSP,soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinnerCTSP.getSelectedItem().toString());
                    long price_moi = soluong * price_CTSP;
                    MainActivity.arr_Giohang.add(new GioHang(id, name_CTSP, price_moi,image_CTSP,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinnerCTSP.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinSP");
        id = sanPham.getId();
        name_CTSP = sanPham.getNamesp();
        price_CTSP = sanPham.getPricesp();
        image_CTSP = sanPham.getImagesp();
        detail_CTSP = sanPham.getDetailsp();
        id_lsp = sanPham.getId_lsp();
        txtname_CTSP.setText(name_CTSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtprice_CTSP.setText("Giá: " + decimalFormat.format(price_CTSP) + "đ");
        txtdetail_CTSP.setText(detail_CTSP);
        Picasso.with(getApplicationContext()).load(image_CTSP)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageViewCTSP);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbarChitietSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbarChitietSP = (Toolbar) findViewById(R.id.toolbarCTSP);
        imageViewCTSP = (ImageView) findViewById(R.id.imageCTSP);
        txtname_CTSP = (TextView) findViewById(R.id.txt_nameCTSP);
        txtprice_CTSP = (TextView) findViewById(R.id.txt_priceCTSP);
        txtdetail_CTSP = (TextView) findViewById(R.id.txt_detailCTSP);
        spinnerCTSP = (Spinner) findViewById(R.id.spinerCTSP);
        buttonCTSP = (Button) findViewById(R.id.btn_CTSP);
    }
}
