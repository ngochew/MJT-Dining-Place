package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class MJTDiningPlace implements Restaurant {

    private Chef[] chefs;
    private List<Order> orders = new ArrayList<>();
    private List<Order> vipOrders = new ArrayList<>();
    private int totalOrdersCount;
    private boolean isRestaurantClosed;


    MJTDiningPlace(int countOfChefs) {
        chefs = new Chef[countOfChefs];
        for (int i = 0; i < countOfChefs; i++) {
            chefs[i] = new Chef(i, this);
            chefs[i].start();
        }
        totalOrdersCount = 0;
        isRestaurantClosed = false;
    }

    @Override
    public synchronized void submitOrder(Order order) {
        if (order.customer().hasVipCard()) {
            vipOrders.add(order);
            totalOrdersCount++;
            notifyAll();
        } else {
            orders.add(order);
            totalOrdersCount++;
            notifyAll();
        }
    }

    @Override
    public synchronized Order nextOrder() {
        if (vipOrders.isEmpty() && orders.isEmpty()) {
            return null;
        }
        orders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Integer.compare(o2.meal().getCookingTime(), o1.meal().getCookingTime());
            }
        });
        Order order;
        if (vipOrders.isEmpty()) {
            order = orders.get(0);
            orders.remove(orders.get(0));
            return order;
        }
        vipOrders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Integer.compare(o2.meal().getCookingTime(), o1.meal().getCookingTime());
            }
        });
        order = vipOrders.get(0);
        vipOrders.remove(vipOrders.get(0));
        return order;
    }

    @Override
    public int getOrdersCount() {
        return totalOrdersCount;
    }

    @Override
    public Chef[] getChefs() {
        return chefs;
    }

    @Override
    public void close() {
        isRestaurantClosed = true;
    }

    public boolean isRestaurantClosed() {
        return isRestaurantClosed;
    }

    public synchronized boolean areTherePendingOrders() {
        return !(vipOrders.isEmpty() && orders.isEmpty());
    }
}
