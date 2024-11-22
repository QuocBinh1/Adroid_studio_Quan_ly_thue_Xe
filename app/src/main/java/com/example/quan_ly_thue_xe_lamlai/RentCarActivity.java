package com.example.quan_ly_thue_xe_lamlai;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class RentCarActivity extends AppCompatActivity {
    // Khai báo biến
    private EditText edtCustomerName, edtPhoneNumber, edtPickupDate;
    private Button btnConfirmRent;
    private TextView txtCarName, txtCarPrice, txtCarDescription;
    private ImageView imgCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rent_car);
        // Ánh xạ
        txtCarName = findViewById(R.id.txtCarName);
        txtCarPrice = findViewById(R.id.txtCarPrice);
        txtCarDescription = findViewById(R.id.txtCarDescription);
        imgCar = findViewById(R.id.imgCar);


        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPickupDate = findViewById(R.id.edtPickupDate);
        btnConfirmRent = findViewById(R.id.btnConfirmRent);

        // Lấy thông tin xe từ Intent
        String carName = getIntent().getStringExtra("carName");
        String carPrice = getIntent().getStringExtra("carPrice");
        String carDescription = getIntent().getStringExtra("carDescription");
        String _carImg = getIntent().getStringExtra("carImg");

        // Hiển thị thông tin xe
        // Sử dụng Glide để tải hình ảnh
        Glide.with(this).load(_carImg).into(imgCar);  // Dùng Glide để tải hình ảnh
        txtCarName.setText(carName);
        txtCarPrice.setText(carPrice);
        txtCarDescription.setText(carDescription);


        // Xử lý sự kiện "Xác nhận đặt xe"
        btnConfirmRent.setOnClickListener(v -> {
            String customerName = edtCustomerName.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String pickupDate = edtPickupDate.getText().toString();

            if (customerName.isEmpty() || phoneNumber.isEmpty() || pickupDate.isEmpty()) {
                Toast.makeText(RentCarActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Lưu thông tin đặt xe vào cơ sở dữ liệu hoặc chuyển đến trang xác nhậns
                Toast.makeText(RentCarActivity.this, "Đặt xe thành công!", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}