package com.minerva.testrecyclerdrag.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.minerva.testrecyclerdrag.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdulrahman on 9/5/2018.
 */

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME="DragDB.db";
    private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts()
    {
        SQLiteDatabase database=getReadableDatabase();
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        String []sqlSelect={"ProductName","ProductId","ProductImage"};
        String sqlTable="DragDetail";
        queryBuilder.setTables(sqlTable);
        queryBuilder.setTables(sqlTable);

        Cursor c =queryBuilder.query(database,sqlSelect,null,null,null,null,null);

        final List<Order>result=new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("ProductImage"))
                ));
            }while (c.moveToNext());


        }
        return  result;

    }

    public void addToCart(Order order)
    {
        SQLiteDatabase database=getReadableDatabase();
        String query=String.format("INSERT INTO DragDetail(ProductId,ProductName,ProductImage) VALUES('%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getProductImage());
        database.execSQL(query);
    }

    public void cleanCart()
    {
        SQLiteDatabase database=getReadableDatabase();
        String query=String.format("DELETE FROM DragDetail");
        database.execSQL(query);
    }
}
