package com.example.hp.prixisfashion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.OrderModel;
import com.example.hp.prixisfashion.R;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {
    private List<OrderModel> modelList;
    private Context context;

    public OrderAdapter(List<OrderModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_list_item, viewGroup, false);

        view.setOnClickListener(mOnClickListener);

        OrderItemViewHolder viewHolder = new OrderItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder orderItemViewHolder, int i) {
        orderItemViewHolder.bind(modelList.get(i));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTime, tvItemsOrder, tvStatus, tvTotalAmount, tvPayMethod;
        ImageView check;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDateMonthOrder);
            tvTime = itemView.findViewById(R.id.tvTimeOrder);
            tvItemsOrder = itemView.findViewById(R.id.tvTotalItemsOrder);
            tvStatus = itemView.findViewById(R.id.tvOrderStatusOrder);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmountOrder);
        }

        void bind(OrderModel model) {
            setDateAndTime(model.getDateTimeOrder());

            String[] items = getArrayFromString(model.getOrderedProductIds());
            String itemsOrder = String.valueOf(items.length) + " items ordered";
            tvItemsOrder.setText(itemsOrder);

            tvStatus.setText(model.getOrderStatus());
            String totalAmount = "Rs: " + model.getTotalPrice() + ".0/-";
            tvTotalAmount.setText(totalAmount);

        }

        private String[] getArrayFromString(String orderedProductIds) {
            String[] productsIds = orderedProductIds.split(" ");

            return productsIds;
        }


        private void setDateAndTime(String dateTimeOrder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

            try {
                Date date = dateFormat.parse(dateTimeOrder);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                String dd = String.valueOf(calendar.get(Calendar.DATE));
                // Toast.makeText(context, "Date is: "+String.valueOf(dd), Toast.LENGTH_SHORT).show();
                String month = getMonthForInt(calendar.get(Calendar.MONTH));
                String dateMonth = dd + " " + month;
                tvDate.setText(dateMonth);

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String time = timeFormat.format(date);
                tvTime.setText(time);


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        public String getMonthForInt(int num) {
            String month = "wrong";
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();
            if (num >= 0 && num <= 11) {
                month = months[num];
            }
            return month.toUpperCase().substring(0, 3);
        }

    }
}
