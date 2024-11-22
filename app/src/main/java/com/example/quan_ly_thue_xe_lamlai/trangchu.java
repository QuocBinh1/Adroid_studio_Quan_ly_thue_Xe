package com.example.quan_ly_thue_xe_lamlai;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;  // Thêm import ArrayList
import java.util.List;

public class trangchu extends AppCompatActivity {
    //khai baos
    RecyclerView recyclerView;
    SQLiteDatabase mydatabase;
    List<Car> carList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trangchu);
        // Ánh xạ
        // Khởi tạo ArrayList cho carList
        carList = new ArrayList<>();  // Khởi tạo ArrayList
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Sử dụng LinearLayoutManager


        mydatabase = openOrCreateDatabase("Quanlythuexe.sqlite", MODE_PRIVATE, null);
        // Lấy dữ liệu từ database và thêm vào danh sáchs
        try {
            Cursor cursor = mydatabase.rawQuery("SELECT * FROM tbxe", null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow("Price"));
                    String imgPath = cursor.getString(cursor.getColumnIndexOrThrow("Img"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("Description")); // Nếu có cột này

                    carList.add(new Car(name, price, imgPath, description));
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "Không có dữ liệu trong bảng tbxe", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        CarAdapter carAdapter = new CarAdapter(this, carList);
        recyclerView.setAdapter(carAdapter);







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }



}