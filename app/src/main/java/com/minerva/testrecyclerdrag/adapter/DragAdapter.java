package com.minerva.testrecyclerdrag.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minerva.testrecyclerdrag.Interface.OnItemLongClickListener;
import com.minerva.testrecyclerdrag.Interface.itemClickListener;
import com.minerva.testrecyclerdrag.R;

/**
 * Created by Abdulrahman on 9/3/2018.
 */

public class DragAdapter  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView dragImage;
    public TextView dragText;
    public LinearLayout dragLinear;
    private itemClickListener clickListener;
    public DragAdapter(View itemView) {
        super(itemView);

        dragImage=itemView.findViewById(R.id.image_drag_item);
        dragText= itemView.findViewById(R.id.text_drag_item);
        dragLinear=itemView.findViewById(R.id.linear_drag_item);
        itemView.setOnClickListener(this);
    }

    public void setClickListener(itemClickListener clickListener)
    {
        this.clickListener=clickListener;
    }



    @Override
    public void onClick(View view) {
        clickListener.onClick(view,getAdapterPosition(),true);
    }



}
