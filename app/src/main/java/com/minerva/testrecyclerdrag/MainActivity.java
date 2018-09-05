package com.minerva.testrecyclerdrag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minerva.testrecyclerdrag.Interface.OnItemLongClickListener;
import com.minerva.testrecyclerdrag.Interface.itemClickListener;
import com.minerva.testrecyclerdrag.adapter.DragAdapter;
import com.minerva.testrecyclerdrag.database.Database;
import com.minerva.testrecyclerdrag.model.DragTest;
import com.minerva.testrecyclerdrag.model.Order;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnDragListener ,View.OnLongClickListener {


    private RecyclerView dragRecycler;
    private FirebaseRecyclerAdapter<DragTest,DragAdapter> adapter;
    private FirebaseDatabase database;
    private DatabaseReference dragData;
    private FloatingActionButton cartFab;

    private Database localDB;

    private String productId="";
    private String productName="";
    private String producrImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
        dragData=database.getReference("DragData");

        localDB=new Database(this);

        findViewById(R.id.main_cart).setOnDragListener(MainActivity.this);
        cartFab=findViewById(R.id.main_cart);

        cartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        setDragRecycler();
        loadData();
    }

    private void setDragRecycler()
    {
        dragRecycler=findViewById(R.id.drag_recycler);
        dragRecycler.setHasFixedSize(true);
        dragRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

    }

    private void loadData()
    {
        adapter=new FirebaseRecyclerAdapter<DragTest, DragAdapter>(
               DragTest.class,
                R.layout.recycler_item,
                DragAdapter.class,
                dragData
        ) {
            @Override
            protected void populateViewHolder(DragAdapter viewHolder, final DragTest model, int position) {
                viewHolder.dragText.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.dragImage);

                productId=adapter.getRef(position).getKey();
                productName=model.getName();
                producrImage=model.getImage();
                viewHolder.dragImage.setTag("ANDROID ICON");
                viewHolder.dragImage.setOnLongClickListener(MainActivity.this);

                viewHolder.dragLinear.setTag("ANDROID LINEAR");
                viewHolder.dragLinear.setOnLongClickListener(MainActivity.this);

                viewHolder.setClickListener(new itemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(MainActivity.this,""+model.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };
        dragRecycler.setAdapter(adapter);

    }
    @Override
    public boolean onLongClick(View view) {

        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(view);
        view.startDrag(data, dragshadow, view, 0);

        return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();

        switch (action)
        {
            case DragEvent.ACTION_DRAG_STARTED:
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                {
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                view.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;


            case DragEvent.ACTION_DRAG_EXITED:

                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;

            case DragEvent.ACTION_DROP:

                ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                String dragData = item.getText().toString();
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                view.getBackground().clearColorFilter();
                view.invalidate();

                View vw = (View) dragEvent.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw);
                FloatingActionButton container = (FloatingActionButton) view;

              //  LinearLayout container2 = (LinearLayout) view;
              // container2.addView(vw);
                owner.addView(vw);
                vw.setVisibility(View.VISIBLE);
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                view.getBackground().clearColorFilter();
                view.invalidate();

                if (dragEvent.getResult())
                {
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                    addToCart();
                }

                else
                {
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();

                }
                return true;

            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }

        return true;
    }

    private void addToCart()
    {
        new Database(getBaseContext()).addToCart(new Order(productId,productName,producrImage));
    }

}
