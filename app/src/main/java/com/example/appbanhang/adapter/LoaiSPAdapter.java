package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {

    //truyền tham số
    ArrayList<LoaiSP> arrayListLoaiSP;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> arrayListLoaiSP, Context context) {
        this.arrayListLoaiSP = arrayListLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSP.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaiSP.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txttenloaiSP;
        ImageView imageSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            //view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_loaisp, null);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_loaisp, null);
            viewHolder.txttenloaiSP = (TextView) view.findViewById(R.id.tvloaiSP);
            viewHolder.imageSP = (ImageView) view.findViewById(R.id.imageViewLSP);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(i);
        viewHolder.txttenloaiSP.setText(loaiSP.getTenloaiSP());
        Picasso.with(context).load(loaiSP.getImageSP())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageSP);
        return view;
    }

}
