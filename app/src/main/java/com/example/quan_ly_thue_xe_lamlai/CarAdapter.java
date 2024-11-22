package com.example.quan_ly_thue_xe_lamlai;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private Context context;
    private List<Car> carList;

    public CarAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.nameTextView.setText(car.getName());
        holder.priceTextView.setText(car.getPrice());
        holder.descriptionTextView.setText(car.getDescription());
        // Load ảnh bằng Glide
        Glide.with(context)
                .load(car.getImgPath())
                .into(holder.imageView);
        holder.btnRentCar.setOnClickListener(v -> {
            // Khi nhấn nút Đặt xe, chuyển đến Activity khác để ghi thông tin đặt xes
            Intent intent = new Intent(context, RentCarActivity.class);

            intent.putExtra("carName", car.getName());
            intent.putExtra("carPrice", car.getPrice());
            intent.putExtra("carDescription", car.getDescription());
            intent.putExtra("carImg", car.getImgPath());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView , descriptionTextView;
        ImageView imageView;
        Button btnRentCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.itemName);
            priceTextView = itemView.findViewById(R.id.itemPrice);
            descriptionTextView = itemView.findViewById(R.id.itemDescription);
            imageView = itemView.findViewById(R.id.itemImage);
            btnRentCar = itemView.findViewById(R.id.btnRentCar);

        }
    }
}
