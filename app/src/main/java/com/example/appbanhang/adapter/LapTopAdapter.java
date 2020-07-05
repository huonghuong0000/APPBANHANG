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

public class LapTopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayLapTop;

    public LapTopAdapter(Context context, ArrayList<SanPham> arrayLapTop) {
        this.context = context;
        this.arrayLapTop = arrayLapTop;
    }

    @Override
    public int getCount() {
        return arrayLapTop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayLapTop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //
    public class ViewHolder{
        public TextView txtname_lt, txt_sprice_lt, txt_detail_lt;
        public ImageView image_lt;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if( view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_laptop,null);
            viewHolder.txtname_lt = (TextView) view.findViewById(R.id.txt_namelaptop);
            viewHolder.txt_sprice_lt = (TextView) view.findViewById(R.id.txt_pricelaptop);
            viewHolder.txt_detail_lt = (TextView) view.findViewById(R.id.txt_detaillaptop);
            viewHolder.image_lt = (ImageView) view.findViewById(R.id.image_laptop);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtname_lt.setText(sanPham.getNamesp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_sprice_lt.setText("Giá: " + decimalFormat.format(sanPham.getPricesp())+ "đ");
        viewHolder.txt_detail_lt.setMaxLines(2);
        viewHolder.txt_detail_lt.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txt_detail_lt.setText(sanPham.getDetailsp());
        Picasso.with(context).load(sanPham.getImagesp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.image_lt);
        return view;
    }
}
