package com.example.hp.prixisfashion.Model.AdminModels.CustomerModel;

public class OrderModel {
    private String orderId;
    private String dateTimeOrder;
    private String orderedProductIds;
    private String orderStatus;
    private String totalPrice;
    private String paymentMethod;
    private String quantities;
    private String orderBy;
    private String deliveryAddress;

    public OrderModel(String orderId, String dateTimeOrder, String orderedProductIds,String deliveryAddress,
                      String orderStatus, String totalPrice, String quantities, String orderBy) {
        this.orderId = orderId;
        this.dateTimeOrder = dateTimeOrder;
        this.orderedProductIds = orderedProductIds;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.quantities = quantities;
        this.deliveryAddress = deliveryAddress;
        this.orderBy = orderBy;
    }

    public OrderModel() {
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDateTimeOrder() {
        return dateTimeOrder;
    }

    public void setDateTimeOrder(String dateTimeOrder) {
        this.dateTimeOrder = dateTimeOrder;
    }

    public String getOrderedProductIds() {
        return orderedProductIds;
    }

    public void setOrderedProductIds(String orderedProductIds) {
        this.orderedProductIds = orderedProductIds;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
