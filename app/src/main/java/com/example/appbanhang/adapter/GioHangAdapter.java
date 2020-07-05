package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.GioHangActivity;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arr_cart;

    public GioHangAdapter(Context context, ArrayList<GioHang> arr_cart) {
        this.context = context;
        this.arr_cart = arr_cart;
    }

    @Override
    public int getCount() {
        return arr_cart.size();
    }

    @Override
    public Object getItem(int i) {
        return arr_cart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtname_cart, txtprice_cart;
        public ImageView image_cart;
        public Button btn_minus, btn_values, btn_plus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.txtname_cart = (TextView) view.findViewById(R.id.txtnamecart);
            viewHolder.txtprice_cart = (TextView) view.findViewById(R.id.txtpricecart);
            viewHolder.image_cart = (ImageView) view.findViewById(R.id.imagegiohang);
            viewHolder.btn_minus = (Button) view.findViewById(R.id.btnminus);
            viewHolder.btn_values = (Button) view.findViewById(R.id.btnvalues);
            viewHolder.btn_plus = (Button) view.findViewById(R.id.btnplus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang giohang = (GioHang) getItem(i);
        viewHolder.txtname_cart.setText(giohang.getName_sp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtprice_cart.setText(decimalFormat.format(giohang.getPrice_sp()) + "D");
        Picasso.with(context).load(giohang.getImage_sp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.image_cart);
        viewHolder.btn_values.setText(giohang.getSoluong_sp() + "");

        //xử lý 2 nút +/-
        int sl = Integer.parseInt(viewHolder.btn_values.getText().toString());
        if (sl >= 10){
            viewHolder.btn_plus.setVisibility(View.INVISIBLE);
            viewHolder.btn_minus.setVisibility(View.VISIBLE);
        } else if (sl <=1 ) {
            viewHolder.btn_minus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1){
            viewHolder.btn_minus.setVisibility(View.VISIBLE);
            viewHolder.btn_plus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slnew = Integer.parseInt(finalViewHolder.btn_values.getText().toString()) + 1;
                int slhientai = MainActivity.arr_Giohang.get(i).getSoluong_sp();
                long price_ht = MainActivity.arr_Giohang.get(i).getPrice_sp();
                MainActivity.arr_Giohang.get(i).setSoluong_sp(slnew);
                long price_new = (price_ht * slnew) / slhientai;
                MainActivity.arr_Giohang.get(i).setPrice_sp(price_new);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtprice_cart.setText(decimalFormat.format(price_new) + "D");
                GioHangActivity.EvenUltil();
                if (slnew > 9) {
                    finalViewHolder.btn_plus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btn_minus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_values.setText(String.valueOf(slnew));
                } else {
                    finalViewHolder.btn_minus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_values.setText(String.valueOf(slnew));
                }
            }
        });
        viewHolder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew = Integer.parseInt(finalViewHolder.btn_values.getText().toString()) - 1;
                int slhientai = MainActivity.arr_Giohang.get(i).getSoluong_sp();
                long price_ht = MainActivity.arr_Giohang.get(i).getPrice_sp();
                MainActivity.arr_Giohang.get(i).setSoluong_sp(slnew);
                long price_new = (price_ht * slnew) / slhientai;
                MainActivity.arr_Giohang.get(i).setPrice_sp(price_new);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtprice_cart.setText(decimalFormat.format(price_new) + "D");
                GioHangActivity.EvenUltil();
                if (slnew < 2) {
                    finalViewHolder.btn_minus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_values.setText(String.valueOf(slnew));
                } else {
                    finalViewHolder.btn_minus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_values.setText(String.valueOf(slnew));
                }
            }
        });
        return view;
    }
}
