package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<Curso> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListAdapter(Context context, List<Curso> listData) {
        this.listData = listData;
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.imgList = (ImageView) convertView.findViewById(R.id.imageViewListView);
            holder.cursoNome = (TextView) convertView.findViewById(R.id.textViewListView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Curso curso = this.listData.get(position);
        holder.cursoNome.setText(curso.getCursoNome());

        int imgId = this.getMipmapResIdByName(curso.getImgName());

        holder.imgList.setImageResource(imgId);

        return convertView;
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView imgList;
        TextView cursoNome;
    }

}
