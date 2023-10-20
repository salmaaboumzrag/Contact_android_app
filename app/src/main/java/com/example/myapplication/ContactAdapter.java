package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<Contact> list;

    public ContactAdapter(Context context, ArrayList<Contact> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size(); //Attention!!!!!!!
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        public TextView tv_user;
        public ImageView img_user;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;
        LayoutInflater mInflater = LayoutInflater.from(context);
        //(1) : Réutilisation du layout
        if (convertView == null) {
            layoutItem = (ConstraintLayout)
                    mInflater.inflate(R.layout.item_layout, parent, false);
        } else { layoutItem = (ConstraintLayout) convertView; }

        ViewHolder viewHolder = (ViewHolder) layoutItem.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            //(2) : Récupération des TextView de notre layout
            viewHolder.tv_user = (TextView) layoutItem.findViewById(R.id.textView);
            viewHolder.img_user = layoutItem.findViewById(R.id.imageView);
            layoutItem.setTag(viewHolder);
        }
        //(3) : Mise à jour des valeurs
        viewHolder.tv_user.setText(list.get(position).getFirstName()+" "+list.get(position).getName().toUpperCase());
        //initier l'icone à celle noire
        int resID = R.mipmap.ic_contact_noir_foreground;

        if ("M".equals(list.get(position).getGender())) {
            resID = context.getResources().getIdentifier("ic_contact_vert_foreground", "mipmap", context.getPackageName());
        } else if ("F".equals(list.get(position).getGender())) {
            resID = context.getResources().getIdentifier("ic_contact_rouge_foreground", "mipmap", context.getPackageName());
        }

        viewHolder.img_user.setImageResource(resID);
        //On retourne l'item créé.
        return layoutItem;
    }

}