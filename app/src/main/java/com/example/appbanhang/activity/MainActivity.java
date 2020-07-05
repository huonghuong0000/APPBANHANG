package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaiSPAdapter;
import com.example.appbanhang.adapter.SanPhamAdapter;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.LoaiSP;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView lvtrangchu;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> arrLSP;
    LoaiSPAdapter loaiSPAdapter;
    int id = 0;
    String name_lsp = "";
    String image_lsp = "";
    ArrayList<SanPham> mangSanPham;
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<GioHang> arr_Giohang;

    //timkiem
//    private SearchView searchView;
//    private ListView listViewSearch;
//     String [] COUNTRIES = {"Iphone 6", "Iphone 7", "Iphone 8", "Iphone X",
//                            "Laptop Dell", "Laptop Gaming"};
//     ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COUNTRIES);
////        listViewSearch = findViewById
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaiSP();
            GetDLSPNew();
            CatchOnItemListView();

        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra kết nối");
            finish();
        }

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

    private void CatchOnItemListView() {
        lvtrangchu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kêt nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("id_lsp",arrLSP.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kêt nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("id_lsp",arrLSP.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kêt nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kêt nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

//                    case 4:
//                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
//                            Intent intent = new Intent(MainActivity.this, ThonTinActivity.class);
//                            startActivity(intent);
//                        } else {
//                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kêt nối");
//                        }
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
                }

            }
        });
    }

    private void GetDLSPNew() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanSPnew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String name_sp = "";
                    Integer price_sp = 0;
                    String image_sp = "";
                    String detail_sp = "";
                    int id_lsp = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            name_sp = jsonObject.getString("namesp");
                            price_sp = jsonObject.getInt("pricesp");
                            image_sp = jsonObject.getString("imagesp");
                            detail_sp = jsonObject.getString("detailsp");
                            id_lsp = jsonObject.getInt("id_lsp");
                            mangSanPham.add(new SanPham(ID, name_sp, price_sp, image_sp, detail_sp, id_lsp));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void GetDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i=0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name_lsp = jsonObject.getString("name_lsp");
                            image_lsp= jsonObject.getString("image_lsp");
                            arrLSP.add((new LoaiSP(id,name_lsp,image_lsp)));
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrLSP.add(3, new LoaiSP(0, "Liên hệ","https://i.ibb.co/Gt7WH0j/images.png"));
                    arrLSP.add(4, new LoaiSP(0, "Thông tin", "https://i.ibb.co/stdcSKy/icon-lien-he-xanh.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
      ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://i.ibb.co/9hF0kfq/1.jpg");
        mangquangcao.add("https://i.ibb.co/q5k98s9/2.jpg");
        mangquangcao.add("https://i.ibb.co/9WBfBmz/x.jpg");
        mangquangcao.add("https://i.ibb.co/YT2wKmM/8pl.jpg");
        mangquangcao.add("https://i.ibb.co/LhYrsXm/6s.jpg");
        //gắn đường dẫn vào Flipper
        for (int i=0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext())
                    .load(mangquangcao.get(i))
                   // .error(R.drawable.ic_launcher_background)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        // bắt sự kiện viewlipper tựu chạy
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
    

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarTrangchu);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        lvtrangchu = (ListView) findViewById(R.id.lvtrangchu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //menu-loaisp
        arrLSP = new ArrayList<>();
        arrLSP.add(0, new LoaiSP(0,"Trang chủ", "https://i.ibb.co/JKjgtkG/t-i-xu-ng.png"));
        loaiSPAdapter = new LoaiSPAdapter(arrLSP, getApplicationContext());
        lvtrangchu.setAdapter(loaiSPAdapter);
        //sản phẩm mới nhất
        mangSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mangSanPham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(sanPhamAdapter);
        //ko tạo mảng mới khi chuyển màn hình - giữ nguyên dl giỏ hàng
        if (arr_Giohang != null) {
        } else {
            arr_Giohang = new ArrayList<>();
        }
//        searchView = findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override    public boolean onQueryTextSubmit(String query) {
//                searchAction(searchView.getQuery().toString().trim());
//                return false;    }
//
//            @Override    public boolean onQueryTextChange(String newText) {
//                return false;    }
//        });
            }

}
