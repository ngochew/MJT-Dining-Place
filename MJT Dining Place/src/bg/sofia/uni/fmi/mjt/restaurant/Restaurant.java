package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.Order;

public interface Restaurant {

    public void submitOrder(Order order);

    public Order nextOrder();

    public int getOrdersCount();

    public Chef[] getChefs();

    public void close();
}