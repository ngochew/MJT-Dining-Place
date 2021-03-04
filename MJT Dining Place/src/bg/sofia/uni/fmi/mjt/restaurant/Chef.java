package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.Order;

public class Chef extends Thread {

    private final int id;
    private final Restaurant restaurant;
    private int cookedMealsCount;

    public Chef(int id, Restaurant restaurant) {
        this.id = id;
        this.cookedMealsCount = 0;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (!((MJTDiningPlace) restaurant).isRestaurantClosed() ||
                ((MJTDiningPlace) restaurant).areTherePendingOrders()) {
            int orderCookingTime = 0;
            try {
                synchronized (this) {
                    if (!((MJTDiningPlace) restaurant).areTherePendingOrders()) {
                        final int MAX_TIME_TO_WAIT = 10;
                        wait(MAX_TIME_TO_WAIT);
                    }
                    if (((MJTDiningPlace) restaurant).areTherePendingOrders()) {
                        Order order = restaurant.nextOrder();
                        orderCookingTime = order.meal().getCookingTime();
                        this.cookedMealsCount++;
                    }
                }
                Thread.sleep(orderCookingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }

    public long getId() {
        return this.id;
    }

    public synchronized int getTotalCookedMeals() {
        return cookedMealsCount;
    }
}