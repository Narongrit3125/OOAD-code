import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {
    private List<String> items; // รายการสินค้าในตะกร้า
    private List<Integer> prices; // ราคาสินค้าในตะกร้า
    private List<Integer> quantities; // จำนวนสินค้าที่ซื้อในตะกร้า
    private Scanner scanner; // สแกนเนอร์สำหรับรับข้อมูลจากผู้ใช้

    public Cart(Scanner scanner) {
        this.scanner = scanner;
        items = new ArrayList<>();
        prices = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    // เมธอดสำหรับเข้าถึงรายการสินค้า
    public List<String> getItems() {
        return items;
    }

    // เมธอดสำหรับเข้าถึงรายการราคา
    public List<Integer> getPrices() {
        return prices;
    }

    // เมธอดสำหรับเข้าถึงจำนวนสินค้า
    public List<Integer> getQuantities() {
        return quantities;
    }

    // เมธอดสำหรับเพิ่มสินค้าลงในตะกร้า
    public void addItem(String item, int price, int quantity) {
        items.add(item);
        prices.add(price);
        quantities.add(quantity);
    }

    // เมธอดสำหรับเคลียร์ข้อมูลในตะกร้า
    public void clearCart() {
        items.clear();
        prices.clear();
        quantities.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // เมธอดสำหรับแสดงข้อมูลในตะกร้า
    public void displayCart(Member member) {
        if (items.isEmpty()) { // ตรวจสอบว่าตะกร้าสินค้าว่างหรือไม่
            System.out.println("===================================");
            System.out.println("            CART");
            System.out.println("===================================");
            System.out.println("You have no product in your cart.");
            System.out.println("===================================");
            return; // ออกจากฟังก์ชันทันทีถ้าตะกร้าว่าง
        }

        System.out.println("===================================");
        System.out.println("            CART GATE CAFE");
        System.out.println("===================================");
        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            int itemTotal = prices.get(i) * quantities.get(i); // คำนวณราคาสินค้ารวม
            System.out.println(quantities.get(i) + " x " + items.get(i) + " - $" + itemTotal);
            total += itemTotal; // รวมราคาทั้งหมด
        }
        System.out.println("===================================");
        System.out.println("Subtotal: $" + total);

        double discount = 0; // ตั้งค่าส่วนลดเริ่มต้น
        double finalTotal = total; // ตั้งค่าราคาสุทธิเริ่มต้น

        int pointsEarned = calculateTotalPoints(); // คำนวณคะแนนที่ได้รับ
        System.out.println("Points Earned: " + pointsEarned); // แสดงคะแนนที่ได้รับ

        // เพิ่มตัวเลือกในการใช้โปรโมชั่น
        System.out.println("1. Confirm Order");
        System.out.println("2. Edit Order");
        System.out.println("3. Cancel");
        System.out.println("4. Use Promotion");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                confirmOrder(member, total, pointsEarned);
                return; // ออกจากฟังก์ชันทันทีหลังจากยืนยันคำสั่งซื้อ
            case 2:
                editOrder(); // เรียกใช้ฟังก์ชันสำหรับแก้ไขคำสั่งซื้อ
                break;
            case 3:
                System.out.println("Order canceled. Returning to main menu.");
                Main.orderConfirmed = false; // ตั้งค่า orderConfirmed เป็น false เมื่อยกเลิกคำสั่งซื้อ
                return; // ออกจากฟังก์ชันทันทีหลังจากยกเลิกคำสั่งซื้อ
            case 4:
                applyPromotion(total, pointsEarned);
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }
    }

    private void applyPromotion(int total, int pointsEarned) {
        System.out.println("===================================");
        System.out.println("          CURRENT PROMOTION");
        System.out.println("===================================");
        System.out.println("1. Buy over $200, get 10% off!");
        System.out.println("2. No promotion");
        System.out.print("Select a promotion: ");
        int promoChoice = scanner.nextInt();

        double discount = 0;
        if (promoChoice == 1 && total > 200) {
            discount = total * 0.10; // คำนวณส่วนลด 10%
            System.out.println("Discount (10%): -$" + discount);
            Main.promotionApplied = true;
        } else {
            Main.promotionApplied = false;
        }

        double finalTotal = total - discount;
        System.out.println("Total after discount: $" + finalTotal);

        // เมื่อเลือกโปรโมชั่นแล้วจะกลับไปที่การยืนยันคำสั่งซื้อ
        confirmOrder(new Member(null, null), total, pointsEarned); // แทนที่ Member() ด้วยสมาชิกที่กำลังใช้งาน
    }

    private void confirmOrder(Member member, int total, int pointsEarned) {
        System.out.println("===================================");
        System.out.println("1. Order now");
        System.out.println("2. Order with promotions");
        System.out.println("===================================");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
    
        double discount = 0.0;
    
        if (choice == 2 && Main.promotionApplied) {
            // ใช้โปรโมชั่น
            discount = total * 0.10;
            System.out.println("Discount (10%): -$" + discount);
        } else {
            Main.promotionApplied = false; // ตั้งค่าสถานะการใช้โปรโมชั่นเป็น false
        }
    
        double finalTotal = total - discount;
        System.out.println("Total after discount: $" + finalTotal);
    
        member.addPoints(pointsEarned); // เพิ่มคะแนนให้กับสมาชิก
        Main.orderConfirmed = true; // ตั้งค่าสถานะการยืนยันคำสั่งซื้อ
        Main.lastReceipt = new Receipt(this, Main.promotionApplied); // บันทึกใบเสร็จ
    
        System.out.println("Order confirmed! Thank you for your purchase.");
    }

    // เมธอดสำหรับแก้ไขคำสั่งซื้อ
    private void editOrder() {
        boolean editing = true;
        while (editing) {
            System.out.println("===================================");
            System.out.println("        EDIT ORDER");
            System.out.println("===================================");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + quantities.get(i) + " x " + items.get(i) + " - $"
                        + (prices.get(i) * quantities.get(i)));
            }
            System.out.println("0. Back to Cart");
            System.out.println("===================================");
            System.out.print("Select an item to edit (or 0 to go back): ");
            int itemChoice = scanner.nextInt();

            if (itemChoice == 0) {
                editing = false; // กลับไปยังการแสดงตะกร้า
            } else if (itemChoice > 0 && itemChoice <= items.size()) {
                System.out.println("1. Change quantity");
                System.out.println("2. Remove item");
                System.out.print("Select an option: ");
                int editChoice = scanner.nextInt();

                if (editChoice == 1) {
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    if (newQuantity > 0) {
                        quantities.set(itemChoice - 1, newQuantity); // เปลี่ยนจำนวนสินค้า
                        System.out.println("Quantity updated.");
                    } else {
                        System.out.println("Invalid quantity.");
                    }
                } else if (editChoice == 2) {
                    items.remove(itemChoice - 1); // ลบสินค้าออกจากรายการ
                    prices.remove(itemChoice - 1); // ลบราคาสินค้าออกจากรายการ
                    quantities.remove(itemChoice - 1); // ลบจำนวนสินค้าที่ซื้อออกจากรายการ
                    System.out.println("Item removed.");
                } else {
                    System.out.println("Invalid option. Please select again.");
                }
            } else {
                System.out.println("Invalid item. Please select again.");
            }
        }
    }

    // เมธอดสำหรับคำนวณคะแนนสะสมจากจำนวนสินค้าที่ซื้อ
    public int calculateTotalPoints() {
        int totalPoints = 0;
        for (int quantity : quantities) {
            totalPoints += quantity;
        }
        return totalPoints;
    }
}
