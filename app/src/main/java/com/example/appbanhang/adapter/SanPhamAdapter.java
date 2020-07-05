package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSPActivity;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arrSanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrSanPham) {
        this.context = context;
        this.arrSanPham = arrSanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_spnew, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = arrSanPham.get(position);
        holder.txt_namesp.setText(sanPham.getNamesp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_pricesp.setText("Giá: " + decimalFormat.format(sanPham.getPricesp()) + "đ");
        Picasso.with(context).load(sanPham.getImagesp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imagesp);
    }

    @Override
    public int getItemCount() {

        return arrSanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imagesp;
        public TextView txt_namesp, txt_pricesp;

        public ItemHolder (View itemView) {
            super(itemView);
            imagesp = (ImageView) itemView.findViewById(R.id.imageViewsp);
            txt_namesp = (TextView) itemView.findViewById(R.id.txtname_sp);
            txt_pricesp = (TextView) itemView.findViewById(R.id.txtprice_sp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSPActivity.class);
                    intent.putExtra("thongtinSP", arrSanPham.get(getPosition()));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context,arrSanPham.get(getPosition()).getNamesp());
                    context.startActivity(intent);
                }
            });

        }
    }
}
