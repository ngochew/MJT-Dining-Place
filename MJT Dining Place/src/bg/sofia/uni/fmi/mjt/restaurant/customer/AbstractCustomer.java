package bg.sofia.uni.fmi.mjt.restaurant.customer;

import bg.sofia.uni.fmi.mjt.restaurant.Meal;
import bg.sofia.uni.fmi.mjt.restaurant.Restaurant;

import java.util.Random;

public abstract class AbstractCustomer extends Thread {

    private final Restaurant restaurant;
    private static final int MIN_TIME_TO_WAIT = 1;
    private static final int MAX_TIME_TO_WAIT = 4;

    public AbstractCustomer(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(MAX_TIME_TO_WAIT) + MIN_TIME_TO_WAIT);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        restaurant.submitOrder(new Order(Meal.chooseFromMenu(), this));
    }

    public abstract boolean hasVipCard();
}