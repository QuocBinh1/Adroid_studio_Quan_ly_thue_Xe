import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvName.setText(order.getCustomerName());
        holder.tvPhoneNumber.setText(order.getPhoneNumber());
        holder.tvAddress.setText(order.getAddress());
        holder.tvCarName.setText(order.getCarName());
        holder.tvCarPrice.setText(String.valueOf(order.getCarPrice()));
        holder.tvQuantity.setText(String.valueOf(order.getQuantity()));

        // Hiển thị ảnh (Glide)
        Glide.with(context).load(order.getCarImg()).into(holder.imgCar);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvPhoneNumber, tvAddress, tvCarName, tvCarPrice, tvQuantity;
        public ImageView imgCar;

        public OrderViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvCarName = view.findViewById(R.id.tvCarName);
            tvCarPrice = view.findViewById(R.id.tvCarPrice);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            imgCar = view.findViewById(R.id.imgCar);
        }
    }
}
