package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.SanPhamAdapter;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKHActivity extends AppCompatActivity {

    EditText editname_KH, editphone_Kh, editemail_kh;
    Button btnXacnhan, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_k_h);
        anhxa();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten_kh = editname_KH.getText().toString().trim();
                final String sdt_kh = editphone_Kh.getText().toString().trim();
                final String email_kh = editemail_kh.getText().toString().trim();
                if (ten_kh.length()>0 && sdt_kh.length() >0 && email_kh.length()>0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", madonhang);
                            if (Integer.parseInt(madonhang) >0 ){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.DuongdanCTdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.arr_Giohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn đã đặt hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng !");
                                        } else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Đặt hàng không thành công, vui lòng kiểm tra lại thoogn tin !");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0; i<MainActivity.arr_Giohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("id_dh",madonhang);
                                                jsonObject.put("id_sp",MainActivity.arr_Giohang.get(i).getId_sp());
                                                jsonObject.put("name_product",MainActivity.arr_Giohang.get(i).getName_sp());
                                                jsonObject.put("price_product",MainActivity.arr_Giohang.get(i).getPrice_sp());
                                                jsonObject.put("amount_product",MainActivity.arr_Giohang.get(i).getSoluong_sp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", ten_kh);
                            hashMap.put("sodienthoai", sdt_kh);
                            hashMap.put("email", email_kh);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại thông tin !");
                }
            }
        });
    }

    private void anhxa() {
        editname_KH = findViewById(R.id.editTenKH);
        editphone_Kh = findViewById(R.id.editSDT);
        editemail_kh = findViewById(R.id.editEmailKH);
        btnXacnhan = findViewById(R.id.btnxacnhan);
        btnCancel = findViewById(R.id.btnCancel);
    }
}
