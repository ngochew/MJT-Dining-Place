package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.AbstractCustomer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.Customer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.VipCustomer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MJTDiningPlaceTest {
    static Restaurant mjt;
    static final int chefsCount = 10;
    static final int customersCount = 500;


    @Before
    public void setData() {
        mjt = new MJTDiningPlace(chefsCount);
        AbstractCustomer[] customers = new AbstractCustomer[customersCount];
        for (int i = 0; i < customersCount; i++) {
            if (i % 2 == 0) {
                customers[i] = new Customer(mjt);
            } else {
                customers[i] = new VipCustomer(mjt);
            }
            customers[i].start();
        }
        for (int i = 0; i < customersCount; i++) {
            try {
                customers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
        mjt.close();
    }


    @Test
    public void testAllOrdersAreCooked() {
        int totalCookedMeals = 0;
        for (int i = 0; i < chefsCount; i++) {
            try {
                mjt.getChefs()[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalCookedMeals += mjt.getChefs()[i].getTotalCookedMeals();
        }
        assertEquals(mjt.getOrdersCount(), totalCookedMeals);
    }

    @Test
    public void testDoAllChefsWork() {
        for (int i = 0; i < chefsCount; i++) {
            try {
                mjt.getChefs()[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < chefsCount; i++) {
            assertTrue(mjt.getChefs()[i].getTotalCookedMeals() > 0);
        }
    }

    @Test
    public void testGetOrdersCount() {
        assertEquals(mjt.getOrdersCount(), customersCount);
    }
}