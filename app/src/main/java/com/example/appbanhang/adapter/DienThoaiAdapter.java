package com.example.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtname_dt, txt_sprice_dt, txt_detail_dt;
        public ImageView  image_dt;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if( view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_dienthoai,null);
            viewHolder.txtname_dt = (TextView) view.findViewById(R.id.txt_namedt);
            viewHolder.txt_sprice_dt = (TextView) view.findViewById(R.id.txt_pricedt);
            viewHolder.txt_detail_dt = (TextView) view.findViewById(R.id.txt_detaildt);
            viewHolder.image_dt = (ImageView) view.findViewById(R.id.image_dt);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtname_dt.setText(sanPham.getNamesp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_sprice_dt.setText("Giá: " + decimalFormat.format(sanPham.getPricesp())+ "đ");
        viewHolder.txt_detail_dt.setMaxLines(2);
        viewHolder.txt_detail_dt.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txt_detail_dt.setText(sanPham.getDetailsp());
        Picasso.with(context).load(sanPham.getImagesp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.image_dt);
        return view;
    }
}
