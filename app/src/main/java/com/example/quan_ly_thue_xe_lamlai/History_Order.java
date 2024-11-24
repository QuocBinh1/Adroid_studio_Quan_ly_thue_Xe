package com.example.quan_ly_thue_xe_lamlai;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class History_Order extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    ImageButton imgbackhistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);

        imgbackhistory = findViewById(R.id.imgbackhistory);
        imgbackhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mở cơ sở dữ liệu và tải lịch sử đơn hàng
        SQLiteDatabase db = openOrCreateDatabase("quanlythuexe.sqlite", MODE_PRIVATE, null);
        List<Order> orders = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM tborder", null);
            while (cursor.moveToNext()) {
                Order order = new Order();
                order.setId(cursor.getInt(0)); // ID
                order.setCarName(cursor.getString(1)); // Tên xe
                order.setCarPrice(cursor.getDouble(2)); // Giá xe
                order.setCustomerName(cursor.getString(3)); // Tên khách hàng
                order.setPhoneNumber(cursor.getString(4)); // Số điện thoại
                order.setAddress(cursor.getString(5)); // Địa chỉ
                order.setPickupDate(cursor.getString(6)); // Ngày nhận xe
                orders.add(order);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DATABASE", "Error while accessing the database", e);
        } finally {
            db.close();
        }

        // Đặt adapter cho RecyclerView
        orderAdapter = new OrderAdapter(this, orders, false); // Tham số false để không hiển thị nút
        recyclerView.setAdapter(orderAdapter);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}