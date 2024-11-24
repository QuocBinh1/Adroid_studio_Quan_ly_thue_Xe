//RentCarActivity.java
package com.example.quan_ly_thue_xe_lamlai;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RentCarActivity extends AppCompatActivity {
    // Khai báo biến
    private EditText edtCustomerName, edtPhoneNumber, edtPickupDate , edtAddress;
    private Button btnConfirmRent;
    private TextView txtCarName, txtCarPrice, txtCarDescription;
    private ImageView btnback;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rent_car);
        // Ánh xạ
        txtCarName = findViewById(R.id.txtCarName);
        txtCarPrice = findViewById(R.id.txtCarPrice);
        txtCarDescription = findViewById(R.id.txtCarDescription);
        btnback = findViewById(R.id.btnback);
        edtAddress = findViewById(R.id.edtAddress);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPickupDate = findViewById(R.id.edtPickupDate);
        btnConfirmRent = findViewById(R.id.btnConfirmRent);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Nhận dữ liệu từ Intent
        String carName = getIntent().getStringExtra("carName");
        String carPrice = getIntent().getStringExtra("carPrice");
        String carDescription = getIntent().getStringExtra("carDescription");

        // Hiển thị thông tin xe
        txtCarName.setText("Tên Xe: "+carName);
        txtCarPrice.setText("Giá Thuê: "+carPrice+"k/ngày");
        txtCarDescription.setText("Mô Tả: \n"+carDescription);
        // Hiển thị ảnh

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        // Xử lý sự kiện "Xác nhận đặt xe"
        btnConfirmRent.setOnClickListener(v -> {
            String customerName = edtCustomerName.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String address = edtAddress.getText().toString();
            String pickupDate = edtPickupDate.getText().toString().trim();

            if (customerName.isEmpty() || phoneNumber.isEmpty() || pickupDate.isEmpty() || address.isEmpty()) {
                Toast.makeText(RentCarActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Tạo database
                mydatabase = openOrCreateDatabase("quanlythuexe.sqlite", MODE_PRIVATE, null);
                String createOrderTableQuery = "CREATE TABLE IF NOT EXISTS tborder (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "carName TEXT, " +
                        "carPrice REAL, " +
                        "customerName TEXT, " +
                        "phoneNumber TEXT, " +
                        "address TEXT, " +
                        "pickupDate TEXT)";
                mydatabase.execSQL(createOrderTableQuery);

                // Thêm dữ liệu vào bảng tborder
                String insertOrderQuery = "INSERT INTO tborder (carName, carPrice, customerName, phoneNumber, address, pickupDate) VALUES (?, ?, ?, ?, ?, ?)";
                SQLiteStatement statement = mydatabase.compileStatement(insertOrderQuery);
                statement.bindString(1, txtCarName.getText().toString());
                statement.bindString(2, txtCarPrice.getText().toString());
                statement.bindString(3, customerName);
                statement.bindString(4, phoneNumber);
                statement.bindString(5, address);
                statement.bindString(6, pickupDate);
                statement.executeInsert();

                Toast.makeText(RentCarActivity.this, "Đặt xe thành công!", Toast.LENGTH_SHORT).show();



                //chuyển qua trang cảm ơn đặt xe
                Intent intent = new Intent(RentCarActivity.this, Thanks.class);
                startActivity(intent);
                finish();

            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}