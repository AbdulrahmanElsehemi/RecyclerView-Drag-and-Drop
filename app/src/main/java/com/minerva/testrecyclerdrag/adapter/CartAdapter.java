package com.minerva.testrecyclerdrag.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minerva.testrecyclerdrag.R;
import com.minerva.testrecyclerdrag.model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdulrahman on 9/5/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Order> listData=new ArrayList<>();
    private Context context;


    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView =inflater.inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.txtCartName.setText(listData.get(position).getProductName());
        Picasso.get().load(listData.get(position).getProductImage()).into(holder.cartImage);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener {
        public TextView txtCartName;
        public ImageView cartImage;

        public CartViewHolder(View itemView) {
            super(itemView);

            txtCartName = itemView.findViewById(R.id.cart_item_name);
            cartImage = itemView.findViewById(R.id.cart_item_image);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select action");
            menu.add(0, 0, getAdapterPosition(), "DELETE");

        }

    }
}
