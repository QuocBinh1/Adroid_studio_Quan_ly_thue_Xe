// CRUD_Oder.java
package com.example.quan_ly_thue_xe_lamlai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class Order_CRUD extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private Button btnCrudCarOrder , btntrangchuorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_oder);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnCrudCarOrder = findViewById(R.id.btnCrudCarOrder);
        btntrangchuorder = findViewById(R.id.btntrangchuorder);

        btntrangchuorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Order_CRUD.this, trangchu.class);
                startActivity(intent);
            }
        });
        btnCrudCarOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Order_CRUD.this, Car_CRUD.class);
                startActivity(intent);
            }
        });
        // Mở hoặc tạo cơ sở dữ liệu
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openOrCreateDatabase("quanlythuexe.sqlite", MODE_PRIVATE, null);


            cursor = db.rawQuery("SELECT * FROM tborder", null);
            List<Order> orders = new ArrayList<>();
            while (cursor.moveToNext()) {
                Order order = new Order();
                order.setId(cursor.getInt(0));
                order.setCarName(cursor.getString(1));
                order.setCarPrice(cursor.getDouble(2));
                order.setCustomerName(cursor.getString(3));
                order.setPhoneNumber(cursor.getString(4));
                order.setAddress(cursor.getString(5));
                order.setPickupDate(cursor.getString(6));
                orders.add(order);
            }
            // Đặt adapter cho RecyclerView
            orderAdapter = new OrderAdapter(this, orders, true);
            recyclerView.setAdapter(orderAdapter);


           // orderAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e("DATABASE", "Error while accessing the database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        // Xử lý window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}