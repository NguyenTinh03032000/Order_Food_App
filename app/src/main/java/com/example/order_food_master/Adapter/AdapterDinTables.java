package com.example.order_food_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.order_food_master.DAO.DinTableDAO;
import com.example.order_food_master.DTO.DinTableDTO;
import com.example.order_food_master.MainActivity;
import com.example.order_food_master.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class AdapterDinTables extends BaseAdapter implements View.OnClickListener {

    Context context;
    int resource;
    DinTableDAO dinTableDAO;
    List<DinTableDTO> objects;
    ViewHolderDinTable holderDinTable;
    FragmentManager fragmentManager;

    public AdapterDinTables(Context context, int resource, List<DinTableDTO> objects){
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        dinTableDAO = new DinTableDAO(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int i) {
        return objects.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holderDinTable = new ViewHolderDinTable();
            // attach custom layout to view
            view = inflater.inflate(R.layout.custom_layout_dintables,viewGroup,false);
            holderDinTable.imgTable = (ImageView) view.findViewById(R.id.img_table);
            holderDinTable.imgOrder = (ImageView) view.findViewById(R.id.img_table_order);
            holderDinTable.imgPayment = (ImageView) view.findViewById(R.id.img_payment);
            holderDinTable.imgHideButton = (ImageView) view.findViewById(R.id.img_hideButton);
            holderDinTable.tvTableName = (TextView) view.findViewById(R.id.tv_table_name);

            view.setTag(holderDinTable);
        } else {
            holderDinTable = (ViewHolderDinTable) view.getTag();
        }


        DinTableDTO dto = objects.get(position);
        String statusTable = dinTableDAO.getStatusTableById(dto.getId());
        if(statusTable.equals("true")){
            holderDinTable.imgTable.setImageResource(R.drawable.tabletrue);
        } else{
            holderDinTable.imgTable.setImageResource(R.drawable.table);
        }
        holderDinTable.tvTableName.setText(dto.getName());
        holderDinTable.imgTable.setTag(position);

        holderDinTable.imgTable.setOnClickListener(this);
        holderDinTable.imgOrder.setOnClickListener(this);
        holderDinTable.imgPayment.setOnClickListener(this);
        holderDinTable.imgHideButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolderDinTable{
        ImageView imgTable, imgOrder, imgPayment, imgHideButton;
        TextView tvTableName;
    }
}
