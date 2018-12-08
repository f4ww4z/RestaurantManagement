package restaurantmanagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RestaurantManagement {

    // Initialize variables
    private static final String SAVE_FILE_NAME = "orders.ser";

    private static List<Order> orders;

    public static void main(String[] args) {

        createSaveFile();

        boolean newCustomerOrder;

        do {
            Order newOrder = getOrderFromInputDialog();

            saveNewOrder(newOrder);

            newCustomerOrder = showOrders();

        } while (newCustomerOrder);
    }

    private static Order getOrderFromInputDialog() {

        // Set up input fields
        JTextField foodNameText = new JTextField();
        JTextField quantityText = new JTextField();
        JTextField priceText = new JTextField();
        Object[] input = {
            "Food name: ", foodNameText,
            "Quantity: ", quantityText,
            "Price per item: ", priceText
        };

        // Display the input dialog box
        while (true) {
            orders = getOrders();

            int option = JOptionPane.showConfirmDialog(null,
                    input,
                    "New Customer Order",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {

                if (foodNameText.getText().isEmpty()
                        || quantityText.getText().isEmpty()
                        || priceText.getText().isEmpty()) {
                    System.out.println("All fields MUST be filled.");
                    continue;
                }

                String foodName = foodNameText.getText();
                int quantity;
                float price;
                try {
                    quantity = Integer.parseInt(quantityText.getText());

                } catch (NumberFormatException e) {
                    System.out.println("Quantity must be a whole number.");
                    continue;
                }

                try {
                    price = Float.parseFloat(priceText.getText());
                } catch (NumberFormatException e) {
                    System.out.println("Price field must be a decimal number.");
                    continue;
                }

                long timestamp = System.currentTimeMillis();

                System.out.println("Order Successful");

                return new Order(foodName, quantity, price, timestamp);

            } else {
                System.out.println("Order canceled");
                break;
            }
        }
        return null;
    }

    private static boolean showOrders() {
        String title = "Current Orders";
        StringBuilder output = new StringBuilder();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            String date = df.format(new Date(order.getTimestamp()));
            float totalPrice = calculateTotalPrice(
                    order.getQuantity(), order.getPrice());

            String orderText = "Order: " + order.getFoodName()
                    + " (x" + order.getQuantity() + ")"
                    + "\nDate ordered: " + date
                    + "\nPrice per item: " + formatPrice(order.getPrice())
                    + "\nTotal price: " + formatPrice(totalPrice)
                    + "\n" + dash(35) + "\n";
            output.append(orderText);
        }

        Object[] options = {"Quit", "New Customer Order"};

        int userChoice = JOptionPane.showOptionDialog(null,
                output.toString(),
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
        return (userChoice == 1);
    }

    private static void createSaveFile() {
        try {
            FileOutputStream fos = new FileOutputStream(SAVE_FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<>());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RestaurantManagement.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantManagement.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private static ArrayList<Order> getOrders() {

        try {
            FileInputStream fis = new FileInputStream(SAVE_FILE_NAME);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (ArrayList<Order>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RestaurantManagement.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private static void saveNewOrder(Order order) {

        orders = getOrders();
        orders.add(order);

        try {
            FileOutputStream fos = new FileOutputStream(SAVE_FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(orders);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RestaurantManagement.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantManagement.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private static float calculateTotalPrice(int quantity, float price) {
        return quantity * price;
    }
    
    private static String formatPrice(float price) {
        return "RM " + String.format("%.2f", price);
    }

    private static String dash(int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append("-");
        }
        return builder.toString();
    }
}
