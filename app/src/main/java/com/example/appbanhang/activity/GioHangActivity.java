package com.example.appbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GioHangAdapter;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.util.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    ListView lv_cart;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btnttmuahang;
    Toolbar toolbar_cart;
    GioHangAdapter gioHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        anhxa();
        ActionToolBar();
        CheckData();
        EvenUltil();
        CacthOnItemLV();
        EvenButton();
    }

    private void EvenButton() {
        btnttmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arr_Giohang.size() >0 ){
                    Intent intent = new Intent(getApplicationContext(), ThongTinKHActivity.class);
                    startActivity(intent);
                } else  {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void CacthOnItemLV() {
        lv_cart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(MainActivity.arr_Giohang.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.arr_Giohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if (MainActivity.arr_Giohang.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            } else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUltil() {
        long tongtien = 0;
        for (int i=0; i<MainActivity.arr_Giohang.size(); i++){
            tongtien += MainActivity.arr_Giohang.get(i).getPrice_sp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "D");

    }

    private void CheckData() {
        if (MainActivity.arr_Giohang.size() <=0){
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lv_cart.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lv_cart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_cart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        lv_cart = (ListView) findViewById(R.id.lvcart);
        txtthongbao = (TextView) findViewById(R.id.txtthongbao);
        txttongtien = (TextView) findViewById(R.id.txttongtien);
        btnthanhtoan = (Button) findViewById(R.id.btnthanhtoan);
        btnttmuahang = (Button) findViewById(R.id.btnttmuahg);
        toolbar_cart = (Toolbar) findViewById(R.id.toolGiohang);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, MainActivity.arr_Giohang);
        lv_cart.setAdapter(gioHangAdapter);
    }
}
