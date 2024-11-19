package com.example.quan_ly_thue_xe_lamlai;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    TextView txtimg;
    EditText editname, editprice, editdescription;
    Button btninsert, btnupdate, btndelete, btnquery, btntrangchu, buttonSelectImage, buttonUploadImage;
    ImageView imageView;
    SQLiteDatabase mydatabase;
    ListView lv;
    ArrayList<String> mylistview;
    ArrayAdapter<String> myadapter;
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //----------------------------------------------------------------------------------------------
        //ánh   xạ
        btntrangchu = findViewById(R.id.btntrangchu);
        editname = findViewById(R.id.editname);
        editprice = findViewById(R.id.editprice);
        editdescription = findViewById(R.id.editdescription);
        btninsert = findViewById(R.id.btninsert);
        btnupdate = findViewById(R.id.btnupdate);
        btndelete = findViewById(R.id.btndelete);
        btnquery = findViewById(R.id.btnquery);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        txtimg = findViewById(R.id.txtimg);

        lv = findViewById(R.id.lv);
        mylistview = new ArrayList<>();
        myadapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, mylistview);
        lv.setAdapter(myadapter);

        // Open or create database
        mydatabase = openOrCreateDatabase("Quanlythuexe.sqlite", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE IF NOT EXISTS tbxe(Id INTEGER primary key, Name TEXT, Price INTEGER, Description TEXT,Img TEXT)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table already exists or other error");
        }
        btninsert.setOnClickListener(v -> insert());
        btnquery.setOnClickListener(v -> query());
        btndelete.setOnClickListener(v -> delete());
        btnupdate.setOnClickListener(v-> update());

        lv.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy dữ liệu từ item được chọn
            String selectedItem = mylistview.get(position);

            // Tách chuỗi để lấy các giá trị từ item
            String[] itemParts = selectedItem.split(" - ");
            if (itemParts.length == 4) {
                String name = itemParts[0];
                String price = itemParts[1];
                String description = itemParts[2];
                String img = itemParts[3];

                // Điền vào các EditText và TextView
                editname.setText(name);
                editprice.setText(price);
                editdescription.setText(description);
                txtimg.setText(img);
            }
        });

        buttonSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
        });
        btntrangchu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, trangchu.class);
            startActivity(intent);
        });

        // Update
        //----------------------------------------------------------------------------------------------
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //----------------------------------------------------------------------------------------------
    //caác hàm
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                txtimg.setText(imageUri.toString()); // Hiển thị đường dẫn ảnh
            }
        }
    }
    private void insert() {
        String name = editname.getText().toString();
        String price = editprice.getText().toString();
        String description = editdescription.getText().toString();
        String img = txtimg.getText().toString();
        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || img.isEmpty()) {
            return;
        }
        try {
            String sql = "INSERT INTO tbxe(Name, Price, Description, Img) VALUES('" + name + "', '" + price + "', '" + description + "', '" + img + "')";
            mydatabase.execSQL(sql);
            mylistview.add(name + " - " + price + " - " + description + " - " + img);
            myadapter.notifyDataSetChanged();
            editname.setText("");
            editprice.setText("");
            editdescription.setText("");
            txtimg.setText("");
        } catch (Exception e) {
            Log.e("Error", "Insert error");
        }
    }
    private void query() {
        mylistview.clear();
        try {
            Cursor cursor = mydatabase.rawQuery("SELECT * FROM tbxe", null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow("Price"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
                    String img = cursor.getString(cursor.getColumnIndexOrThrow("Img"));
                    mylistview.add(name + " - " + price + " - " + description + " - " + img);
                } while (cursor.moveToNext());
            }
            myadapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e) {
            Log.e("Error", "Query error");
        }
    }
    private void delete() {
        String name = editname.getText().toString();
        if (name.isEmpty()) {
            return;
        }
        try {
            String sql = "DELETE FROM tbxe WHERE Name = '" + name + "'";
            mydatabase.execSQL(sql);
            query();
        } catch (Exception e) {
            Log.e("Error", "Delete error");
        }
    }
    private void update() {
        String name = editname.getText().toString();
        String price = editprice.getText().toString();
        String description = editdescription.getText().toString();
        String img = txtimg.getText().toString();

        // Kiểm tra các trường đầu vào không bị rỗng
        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || img.isEmpty()) {
            return; // Nếu có trường nào trống thì không thực hiện update
        }

        try {
            // Câu lệnh SQL cập nhật bản ghi dựa trên name
            String sql = "UPDATE tbxe SET Price = '" + price + "', Description = '" + description + "', Img = '" + img + "' WHERE Name = '" + name + "'";

            // In ra câu lệnh SQL để kiểm tra
            Log.d("SQL Query", sql);

            // Thực thi câu lệnh SQL
            mydatabase.execSQL(sql);

            // Cập nhật lại danh sách sau khi thay đổi
            query();

            // Thông báo thành công
            Log.d("Update", "Record updated successfully");
        } catch (Exception e) {
            // Nếu có lỗi, ghi lại thông tin lỗi
            Log.e("Error", "Update error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
}