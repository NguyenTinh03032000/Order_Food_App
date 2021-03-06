package com.example.order_food_master.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food_master.DAO.FoodTypeDAO;
import com.example.order_food_master.DTO.FoodTypeDTO;
import com.example.order_food_master.MenuFood;
import com.example.order_food_master.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class AdapterFoodTypeWithImage extends BaseAdapter {

    Context context;
    int resources;
    List<FoodTypeDTO> objects;
    ViewHolderFoodType holderFoodType;
    FoodTypeDAO foodTypeDAO;

    public AdapterFoodTypeWithImage(Context context, int resources, List<FoodTypeDTO> objects){
        this.context = context;
        this.resources = resources;
        this.objects = objects;
        foodTypeDAO = new FoodTypeDAO(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return objects.get(i).getId();
    }

    public class ViewHolderFoodType{
        ImageView imgFoodType;
        TextView tvName;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            holderFoodType = new ViewHolderFoodType();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resources,viewGroup,false);
            holderFoodType.imgFoodType = (ImageView) view.findViewById(R.id.img_showFood);
            holderFoodType.tvName = (TextView) view.findViewById(R.id.tv_foodName);
            view.setTag(holderFoodType);
        } else{
            holderFoodType = (ViewHolderFoodType) view.getTag();
        }

        FoodTypeDTO foodTypeDTO = objects.get(i);
        int foodTypeID = foodTypeDTO.getId();
        String image = foodTypeDAO.getListImageFoodType(foodTypeID);
        Uri uri = Uri.parse(image);
        //holderFoodType.imgFoodType.setImageURI(uri);//???? fix
        Picasso.get().load(uri).into(holderFoodType.imgFoodType);
        holderFoodType.tvName.setText(foodTypeDTO.getName());

        return view;
    }
}
