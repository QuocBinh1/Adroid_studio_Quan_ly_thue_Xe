package com.example.quan_ly_thue_xe_lamlai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Thanks extends AppCompatActivity {
    //khai bao
    Button btnhistorythank , btntrangchuthank ,btnorderthank;
    ImageView imgbackthank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanks);
        // Ánh xạ
        btnhistorythank = findViewById(R.id.btnhistorythank);
        btntrangchuthank = findViewById(R.id.btntrangchuthank);
        imgbackthank = findViewById(R.id.imgbackthank);
        btnorderthank = findViewById(R.id.btnorderthank);
        // Xử lý sự kiện
        imgbackthank.setOnClickListener(v -> {
            finish();
        });
        btntrangchuthank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thanks.this, trangchu.class);
                startActivity(intent);
            }
        });
        btnhistorythank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thanks.this, History_Order.class);
                startActivity(intent);
            }
        });
        btnorderthank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thanks.this, CRUD_Oder.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Đảm bảo Thanks cũng kết thúc
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}