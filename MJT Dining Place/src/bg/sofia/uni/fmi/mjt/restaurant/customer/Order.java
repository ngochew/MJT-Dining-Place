package bg.sofia.uni.fmi.mjt.restaurant.customer;

import bg.sofia.uni.fmi.mjt.restaurant.Meal;

public record Order(Meal meal, AbstractCustomer customer) {

}
