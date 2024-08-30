import java.util.Scanner;
import java.util.List;

public class Receipt {
    private Cart cartSnapshot;
    private boolean promotionApplied;
    private String spinPromotion; // เพิ่มตัวแปรเพื่อเก็บโปรโมชั่นจาก Spin
    private String deliveryOption;
    private String address;
    private String phoneNumber;
    private int deliveryCharge;

    public Receipt(Cart cart, boolean promotionApplied, String spinPromotion) {
        this.cartSnapshot = new Cart(new Scanner(System.in));

        if (cart.getItems() != null && cart.getPrices() != null && cart.getQuantities() != null) {
            this.cartSnapshot.getItems().addAll(cart.getItems());
            this.cartSnapshot.getPrices().addAll(cart.getPrices());
            this.cartSnapshot.getQuantities().addAll(cart.getQuantities());
        }

        this.promotionApplied = promotionApplied;
        this.spinPromotion = spinPromotion; // กำหนดโปรโมชั่นจาก Spin
        this.deliveryCharge = 0;
    }

    public void setDeliveryDetails(String deliveryOption, String address, String phoneNumber, int deliveryCharge) {
        this.deliveryOption = deliveryOption;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.deliveryCharge = deliveryCharge;
    }

    public void displayReceipt() {
        List<String> items = cartSnapshot.getItems();
        List<Integer> prices = cartSnapshot.getPrices();
        List<Integer> quantities = cartSnapshot.getQuantities();

        if (items.isEmpty()) {
            System.out.println("===================================");
            System.out.println("            RECEIPT");
            System.out.println("===================================");
            System.out.println("No items in the cart.");
            System.out.println("===================================");
            return;
        }

        System.out.println("===================================");
        System.out.println("            RECEIPT");
        System.out.println("===================================");
        int subtotal = 0;
        for (int i = 0; i < items.size(); i++) {
            int itemTotal = prices.get(i) * quantities.get(i);
            System.out.println(quantities.get(i) + " x " + items.get(i) + " - $" + itemTotal);
            subtotal += itemTotal;
        }
        System.out.println("===================================");
        System.out.println("Subtotal: $" + subtotal);

        double discount = 0;
        if (promotionApplied && subtotal > 200) {
            discount = subtotal * 0.10;
            System.out.println("Discount (10%): -$" + discount);
        }

        // คำนวณส่วนลดจาก Spin Promotion
        double spinDiscount = 0;
        if (spinPromotion != null && !spinPromotion.equals("Nothing")) {
            spinDiscount = new Promotion().applySpinDiscount(subtotal, spinPromotion);
            System.out.println("Spin Promotion Discount: -$" + spinDiscount);
        }

        double finalTotal = subtotal - discount - spinDiscount + deliveryCharge;
        System.out.println("Delivery Fee: $" + deliveryCharge);
        System.out.println("Total Price: $" + finalTotal);
        System.out.println("===================================");

        if (deliveryOption != null) {
            System.out.println("Delivery Option: " + deliveryOption);
            System.out.println("Address: " + address);
            System.out.println("Phone Number: " + phoneNumber);
        }

        int pointsEarned = cartSnapshot.calculateTotalPoints();
        System.out.println("Earn Points: " + pointsEarned);
        System.out.println("===================================");
    }
}
