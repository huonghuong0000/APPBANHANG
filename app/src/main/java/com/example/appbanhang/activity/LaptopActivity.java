package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DienThoaiAdapter;
import com.example.appbanhang.adapter.LapTopAdapter;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarLaptop;
    ListView listViewLaptop;
    LapTopAdapter lapTopAdapter;
    ArrayList<SanPham> arr_laptop;
    int id_laptop = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    LaptopActivity.mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_top);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLoaiSP();
            ActiontoolBar();
            GetData(page);
            LoadMoreData();
            Sapxep();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void Sapxep() {
        String arr[] = {"practice.geeksforgeeks.org",
                "quiz.geeksforgeeks.org",
                "code.geeksforgeeks.org"
        };
        Arrays.sort(arr);
        System.out.printf("Modified arr[] : \n%s\n\n",
                Arrays.toString(arr));

        // Sorts arr[] in descending order
        Arrays.sort(arr, Collections.reverseOrder());

        System.out.printf("Modified arr[] : \n%s\n\n",
                Arrays.toString(arr));
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


    public  class  mHandler extends Handler {
        public  void handleMessage (Message msg){
            switch (msg.what){
                case 0:
                    listViewLaptop.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends  Thread{
        public void run(){
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    private void LoadMoreData() {
        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSPActivity.class);
                intent.putExtra("thongtinSP", arr_laptop.get(i));
                startActivity(intent);
            }
        });
        listViewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItem) {
                if(firstVisibleItem + visibleItemCount == totalItem && totalItem !=0 && isLoading == false && limitData == false){
                    isLoading = true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String duongdan = Server.DuongdanDienThoai+String.valueOf(Page);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int id = 0;
                    String name_lt="";
                    int price_lt= 0;
                    String image_lt= "";
                    String detail_lt= "";
                    int id_lsp = 0;
                    if(response != null && response.length() != 2){
                        listViewLaptop.removeFooterView(footerView);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                name_lt = jsonObject.getString("namesp");
                                price_lt = jsonObject.getInt("pricesp");
                                image_lt = jsonObject.getString("imagesp");
                                detail_lt = jsonObject.getString("detailsp");
                                id_lsp = jsonObject.getInt("id_lsp");
                                arr_laptop.add(new SanPham(id,name_lt,price_lt,image_lt,detail_lt,id_lsp));
                                lapTopAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        limitData = true;
                        listViewLaptop.removeFooterView(footerView);
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> param = new HashMap<String, String>();
                    param.put("id_lsp", String.valueOf(id_laptop));
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }

    private void ActiontoolBar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaiSP() {
        id_laptop = getIntent().getIntExtra("id_lsp",-1);
        Log.d("giatriloasap", id_laptop+"");
    }

    private void AnhXa() {
        toolbarLaptop = (Toolbar) findViewById(R.id.toolbarLT);
        listViewLaptop = (ListView) findViewById(R.id.lvLaptop);
        arr_laptop = new ArrayList<>();
        lapTopAdapter = new LapTopAdapter(getApplicationContext(), arr_laptop);
        listViewLaptop.setAdapter(lapTopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new LaptopActivity.mHandler();
    }
}
