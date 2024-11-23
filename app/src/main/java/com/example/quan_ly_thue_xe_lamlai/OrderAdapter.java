//OrderAdapter.java
package com.example.quan_ly_thue_xe_lamlai;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orderList;
    private boolean showActions; // Biến để kiểm tra có hiển thị nút không

    // Constructor
    public OrderAdapter(Context context, List<Order> orderList ,boolean showActions) {
        this.context = context;
        this.orderList = orderList;
        this.showActions = showActions;

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.txtOrderId.setText("Mã Đơn: " + order.getId()); // Hiển thị ID đơn hàng
        holder.txtCustomerName.setText("Tên Khách Hàng: " + order.getCustomerName());
        holder.txtCarName.setText("Tên Xe: " + order.getCarName());
        holder.txtCarPrice.setText("Giá Thuê : " + order.getCarPrice()+"00 VNĐ");
        holder.txtPhoneNumber.setText("Số Điện Thoại: " + order.getPhoneNumber());
        holder.txtAddress.setText("Địa Chỉ Giao Xe: " + order.getAddress());
        holder.txtPickupDate.setText("Ngày Nhận Xe: " + order.getPickupDate());
        // Hiển thị hoặc ẩn các nút
        if (showActions) {
            holder.btnUpdate.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        } else {
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
        // Xử lý nút Update
        holder.btnUpdate.setOnClickListener(v -> {
            // Chuyển đến màn hình Update với thông tin của order
            //Intent intent = new Intent(context, UpdateOrderActivity.class);
            //intent.putExtra("orderId", order.getId());
            //context.startActivity(intent);
        });

        // Xử lý nút Delete
        holder.btnDelete.setOnClickListener(v -> {
            // Xóa đơn hàng khỏi cơ sở dữ liệu
            deleteOrder(order.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId,txtCustomerName, txtPhoneNumber, txtAddress, txtPickupDate, txtCarName, txtCarPrice;
        Button btnUpdate, btnDelete;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPickupDate = itemView.findViewById(R.id.txtPickupDate);
            txtCarName = itemView.findViewById(R.id.txtCarName);
            txtCarPrice = itemView.findViewById(R.id.txtCarPrice);

            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    private void deleteOrder(int orderId, int position) {
        SQLiteDatabase db = context.openOrCreateDatabase("quanlythuexe.sqlite", Context.MODE_PRIVATE, null);
        try {
            db.execSQL("DELETE FROM tborder WHERE id = ?", new Object[]{orderId});
            orderList.remove(position); // Xóa khỏi danh sách
            notifyItemRemoved(position); // Cập nhật RecyclerView
            Toast.makeText(context, "Deleted order ID: " + orderId, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error deleting order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }
}