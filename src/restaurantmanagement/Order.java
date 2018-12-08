package restaurantmanagement;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String foodName;
    private int quantity;
    private float price;
    private long timestamp;

    public Order(String foodName, int quantity, float price, long timestamp) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
