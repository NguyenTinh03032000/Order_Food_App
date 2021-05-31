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
import com.example.order_food_master.DTO.OrdersDTO;
import com.example.order_food_master.MenuFood;
import com.example.order_food_master.PaymentActivity;
import com.example.order_food_master.R;
import com.example.order_food_master.DAO.OrdersDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class AdapterDinTables extends BaseAdapter implements View.OnClickListener {

    Context context;
    int resource;
    DinTableDAO dinTableDAO;
    List<DinTableDTO> objects;
    ViewHolderDinTable holderDinTable;
    OrdersDAO ordersDAO;

    public AdapterDinTables(Context context, int resource, List<DinTableDTO> objects){
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        ordersDAO = new OrdersDAO(context);
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

        if(objects.get(position).isChoose()){
            showButton();
        } else{
            hideButton(false);
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

    public class ViewHolderDinTable{
        ImageView imgTable, imgOrder, imgPayment, imgHideButton;
        TextView tvTableName;
    }
    private void showButton(){
        holderDinTable.imgOrder.setVisibility(View.VISIBLE);
        holderDinTable.imgPayment.setVisibility(View.VISIBLE);
        holderDinTable.imgHideButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.effect_show_button);
        holderDinTable.imgOrder.startAnimation(animation);
        holderDinTable.imgPayment.startAnimation(animation);
        holderDinTable.imgHideButton.startAnimation(animation);
    }

    private void hideButton(boolean effect){
        holderDinTable.imgOrder.setVisibility(View.INVISIBLE);
        holderDinTable.imgPayment.setVisibility(View.INVISIBLE);
        holderDinTable.imgHideButton.setVisibility(View.INVISIBLE);

        if(effect){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.effect_hide_button);
            holderDinTable.imgOrder.startAnimation(animation);
            holderDinTable.imgPayment.startAnimation(animation);
            holderDinTable.imgHideButton.startAnimation(animation);
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        // fix click show imageView on position has clicked
        holderDinTable = (ViewHolderDinTable) ((View)view.getParent()).getTag();
        int pos1 = (int) holderDinTable.imgTable.getTag();
        int idTable = objects.get(pos1).getId();

        switch (id){
            case R.id.img_table:
                int position = (int) view.getTag();
                objects.get(position).setChoose(true);
                showButton();
                break;
            case R.id.img_table_order:
                Intent intentMain = ((MainActivity)context).getIntent();
                int idEmploy = intentMain.getIntExtra("T_idEmploy",0);
                String status = dinTableDAO.getStatusTableById(idTable);
                if(status.equals("false")){
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String dateOrder = dateFormat.format(calendar.getTime());
                    OrdersDTO ordersDTO = new OrdersDTO();
                    ordersDTO.setTableID(idTable);
                    ordersDTO.setEmployID(idEmploy);
                    ordersDTO.setDateOrder(dateOrder);
                    ordersDTO.setStatus("false");
                    long rs = ordersDAO.insert(ordersDTO);
                    dinTableDAO.setStatusTableById(idTable,"true");
                    if(rs == 0){
                        Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                }

                Intent intent=new Intent(context, MenuFood.class);
                Bundle bundleData = new Bundle();
                bundleData.putInt("B_data_idTable",idTable);
                intent.putExtra("To_DinTable",bundleData);
                context.startActivity(intent);
                break;
            case R.id.img_payment:
                Intent intent1 = new Intent(context, PaymentActivity.class);
                intent1.putExtra("T_idTable",idTable);
                context.startActivity(intent1);
                break;
            case R.id.img_hideButton:
                hideButton(true);
                break;
        }
    }
}
