import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Map<String, String[]> categories;
    private Map<String, int[]> prices;
    private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner; // รับ Scanner จาก Main
        categories = new HashMap<>();
        prices = new HashMap<>();

        // หมวดหมู่ต่างๆ และราคาสินค้า
        categories.put("Hot Coffee", new String[] { "Hot Americano", "Hot Latte", "Hot Cappuccino", "Hot Mocha" });
        prices.put("Hot Coffee", new int[] { 40, 45, 45, 45 });

        categories.put("Iced Coffee",
                new String[] { "Iced Espresso", "Iced Americano", "Iced Latte", "Iced Cappuccino", "Iced Mocha" });
        prices.put("Iced Coffee", new int[] { 50, 50, 50, 50, 50 });

        categories.put("Hot Tea",
                new String[] { "Hot Green Tea", "Hot Matcha Latte", "Hot Thai Tea", "Hot Peach Tea", "Hot Black Tea" });
        prices.put("Hot Tea", new int[] { 45, 50, 45, 45, 45 });

        categories.put("Iced Tea", new String[] { "Iced Green Tea", "Iced Matcha Latte", "Iced Thai Tea",
                "Iced Peach Tea", "Iced Black Tea" });
        prices.put("Iced Tea", new int[] { 50, 55, 50, 50, 50 });

        categories.put("Hot Non-Coffee", new String[] { "Hot Milk", "Hot Cocoa", "Hot Honey Lemon" });
        prices.put("Hot Non-Coffee", new int[] { 45, 45, 45 });

        categories.put("Iced Non-Coffee",
                new String[] { "Iced Milk", "Iced Cocoa", "Iced Honey Lemon", "Iced Italian Soda", "Iced Cocoa Dark" });
        prices.put("Iced Non-Coffee", new int[] { 50, 50, 50, 50, 50 });

        categories.put("Special", new String[] { "Iced Black x Yuzu", "Iced Black Stone", "Iced Brick Stone",
                "Iced Peach Lover", "Iced Yuzu Lover" });
        prices.put("Special", new int[] { 65, 60, 60, 65, 65 });
    }

    public void displayCategories() {
        System.out.println("===================================");
        System.out.println("           MENU CATEGORIES");
        System.out.println("===================================");
        int index = 1;
        String[] sortedCategories = { "Hot Coffee", "Iced Coffee", "Hot Tea", "Iced Tea", "Hot Non-Coffee",
                "Iced Non-Coffee", "Special" };
        for (String category : sortedCategories) {
            System.out.println(index + ". " + category);
            index++;
        }
        System.out.println("8. Back");
        System.out.println("===================================");
    }

    public void displayMenuByCategory(String category) {
        if (categories.containsKey(category)) {
            String[] items = categories.get(category);
            int[] itemPrices = prices.get(category);
            System.out.println("===================================");
            System.out.println("            " + category.toUpperCase());
            System.out.println("===================================");
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i] + " - $" + itemPrices[i]);
            }
            System.out.println("9. Back");
            System.out.println("===================================");
        } else {
            System.out.println("Invalid category.");
        }
    }

    public void selectCategory(Cart cart, Member member, Promotion promotion) {
        boolean selectingCategory = true;

        while (selectingCategory) {
            try {
                displayCategories();
                System.out.print("Select a category by number: ");
                int choice = scanner.nextInt();

                if (choice == 8) {
                    selectingCategory = false; // ย้อนกลับไปที่เมนูหลัก
                } else if (choice > 0 && choice <= 7) {
                    String[] sortedCategories = { "Hot Coffee", "Iced Coffee", "Hot Tea", "Iced Tea", "Hot Non-Coffee",
                            "Iced Non-Coffee", "Special" };
                    String selectedCategory = sortedCategories[choice - 1];
                    boolean selectingItem = true;

                    while (selectingItem) {
                        displayMenuByCategory(selectedCategory);
                        System.out.print("Select item number to add to cart (or 9 to go back): ");
                        int itemChoice = scanner.nextInt();

                        if (itemChoice == 9) {
                            selectingItem = false; // ย้อนกลับไปที่หมวดหมู่เมนู
                        } else if (itemChoice > 0 && itemChoice <= categories.get(selectedCategory).length) {
                            selectItem(selectedCategory, itemChoice, cart, member, promotion);
                            selectingItem = false; // ออกจากลูปเพื่อกลับไปยังเมนูหลัก
                            selectingCategory = false; // ออกจากลูปเพื่อกลับไปยังเมนูหลัก
                        } else {
                            System.out.println("Invalid choice. Please select again.");
                        }
                    }

                } else {
                    System.out.println("Invalid choice. Please select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public void selectItem(String category, int itemChoice, Cart cart, Member member, Promotion promotion) {
        try {
            String[] items = categories.get(category);
            int[] itemPrices = prices.get(category);

            String selectedItem = items[itemChoice - 1];
            int itemPrice = itemPrices[itemChoice - 1];

            System.out.print("Enter the number of cups: ");
            int quantity = scanner.nextInt();

            String sweetness = selectSweetnessLevel();

            if (sweetness != null) {
                boolean selecting = true;
                boolean promotionApplied = false; // ใช้ตัวแปรนี้ในการติดตามการใช้โปรโมชั่น
                String spinPromotion = null; // เพิ่มตัวแปรนี้เพื่อเก็บโปรโมชั่นจาก Spin

                while (selecting) {
                    System.out.println("===================================");
                    System.out.println("1. Add to cart");
                    System.out.println("2. Buy now");
                    System.out.println("3. Delivery");
                    System.out.println("4. Use Promotion");
                    System.out.println("===================================");
                    System.out.print("Select an option: ");
                    int option = scanner.nextInt();

                    switch (option) {
                        case 1:
                            // Add item to cart
                            cart.addItem(selectedItem + " with " + sweetness, itemPrice, quantity);
                            member.addPoints(quantity);
                            System.out.println(quantity + " x " + selectedItem + " with " + sweetness
                                    + " has been added to the cart.");
                            selecting = false;
                            break;
                        case 2:
                            // Add item to cart and order now
                            cart.addItem(selectedItem + " with " + sweetness, itemPrice, quantity);
                            member.addPoints(quantity);
                            System.out.println(quantity + " x " + selectedItem + " with " + sweetness + " ordered.");

                            Receipt receipt = new Receipt(cart, promotionApplied, spinPromotion); // อัปเดต Receipt
                                                                                                  // พร้อมโปรโมชั่น
                            receipt.displayReceipt();
                            cart.clearCart();

                            return;

                        case 3:
                            // Delivery
                            cart.addItem(selectedItem + " with " + sweetness, itemPrice, quantity);
                            System.out.println("===================================");
                            System.out.println("Select Delivery Option:");
                            System.out.println("1. Standard Delivery - $15");
                            System.out.println("2. Express Delivery - $20");
                            System.out.print("Select Delivery: ");
                            int deliveryChoice = scanner.nextInt();
                            String deliveryOption = deliveryChoice == 1 ? "Standard Delivery" : "Express Delivery";
                            int deliveryFee = deliveryChoice == 1 ? 15 : 20;

                            System.out.print("Enter delivery address: ");
                            scanner.nextLine(); // Clear the buffer
                            String address = scanner.nextLine();

                            System.out.print("Enter your phone number: ");
                            String phoneNumber = scanner.nextLine();

                            receipt = new Receipt(cart, promotionApplied, spinPromotion);
                            receipt.setDeliveryDetails(deliveryOption, address, phoneNumber, deliveryFee);
                            receipt.displayReceipt();
                            cart.clearCart();

                            return;

                        case 4:
                            // Use Promotion
                            promotion.displayPromotion();
                            System.out.print("Select a promotion: ");
                            int promotionOption = scanner.nextInt();

                            if (promotionOption > 0 && promotionOption <= promotion.getAvailablePromotions().size()) {
                                System.out.println("Promotion applied.");
                                promotionApplied = true; // อัปเดตสถานะการใช้โปรโมชั่น
                                spinPromotion = promotion.getAvailablePromotions().get(promotionOption - 1); // กำหนดโปรโมชั่นที่เลือก

                                // ตรวจสอบว่าเป็นโปรโมชั่น "1 Free Drink" หรือไม่
                                if (spinPromotion.equals("1 Free Drink")) {
                                    // ให้ผู้ใช้เลือกหมวดหมู่เครื่องดื่มก่อน
                                    boolean selectingFreeDrinkCategory = true;
                                    while (selectingFreeDrinkCategory) {
                                        System.out.println("You have earned a free drink! Select a category:");
                                        String[] sortedCategories = { "Hot Coffee", "Iced Coffee", "Hot Tea",
                                                "Iced Tea", "Hot Non-Coffee", "Iced Non-Coffee", "Special" };
                                        for (int i = 0; i < sortedCategories.length; i++) {
                                            System.out.println((i + 1) + ". " + sortedCategories[i]);
                                        }
                                        System.out.print("Select a category by number: ");
                                        int categoryChoice = scanner.nextInt();

                                        if (categoryChoice >= 1 && categoryChoice <= sortedCategories.length) {
                                            String selectedCategory = sortedCategories[categoryChoice - 1];

                                            // แสดงเมนูของหมวดหมู่ที่เลือก
                                            displayMenuByCategory(selectedCategory);

                                            System.out.print("Select a drink number: ");
                                            int freeDrinkChoice = scanner.nextInt();

                                            if (freeDrinkChoice == 9) {
                                                selectingFreeDrinkCategory = false; // กลับไปเลือกหมวดหมู่ใหม่
                                            } else if (freeDrinkChoice > 0
                                                    && freeDrinkChoice <= categories.get(selectedCategory).length) {
                                                String freeDrink = categories.get(selectedCategory)[freeDrinkChoice
                                                        - 1];
                                                int freeDrinkPrice = prices.get(selectedCategory)[freeDrinkChoice - 1];

                                                // เพิ่มเครื่องดื่มฟรีลงในตะกร้าและตั้งราคาของเครื่องดื่มฟรีเป็น $0
                                                cart.addItem(freeDrink + " (Free Drink)", 0, 1);
                                                System.out.println(
                                                        "You have selected " + freeDrink + " as your free drink.");
                                                selectingFreeDrinkCategory = false; // ออกจากลูปการเลือกเครื่องดื่มฟรี
                                            } else {
                                                System.out.println("Invalid choice. Please select again.");
                                            }
                                        } else {
                                            System.out.println("Invalid category selection. Please select again.");
                                        }
                                    }
                                }

                                promotion.removePromotion(promotionOption - 1); // ลบโปรโมชั่นที่ใช้ไปแล้วออกจากรายการ
                            } else {
                                System.out.println("Invalid promotion selection.");
                            }
                            break;

                        default:
                            System.out.println("Invalid option. Please select again.");
                            break;
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
        }
    }

    private String selectSweetnessLevel() {
        System.out.println("===================================");
        System.out.println("        SELECT SWEETNESS LEVEL");
        System.out.println("===================================");
        System.out.println("1. Less Sweet");
        System.out.println("2. Normal Sweetness");
        System.out.println("3. Extra Sweet");
        System.out.println("===================================");
        System.out.print("Select sweetness level: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return "Less Sweet";
            case 2:
                return "Normal Sweetness";
            case 3:
                return "Extra Sweet";
            default:
                System.out.println("Invalid choice. Defaulting to Normal Sweetness.");
                return "Normal Sweetness";
        }
    }
}
