package com.minerva.testrecyclerdrag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.minerva.testrecyclerdrag.adapter.CartAdapter;
import com.minerva.testrecyclerdrag.database.Database;
import com.minerva.testrecyclerdrag.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private List<Order> cart = new ArrayList();
    private CartAdapter adapter;
    private RecyclerView cartRecycler;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler=findViewById(R.id.cart_recycler);
        cartRecycler.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        cartRecycler.setLayoutManager(layoutManager);
        loadProductList();
    }

    private void loadProductList() {
        cart=new Database(this).getCarts();

        adapter=new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        cartRecycler.setAdapter(adapter);



    }
}
