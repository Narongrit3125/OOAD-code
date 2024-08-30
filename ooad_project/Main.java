import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static boolean orderConfirmed = false;
    public static boolean promotionApplied = false;
    public static Receipt lastReceipt = null;
    public static String spinPromotion;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // สร้างออบเจ็กต์สำหรับแต่ละคลาส
        Cart cart = new Cart(scanner);
        Member member = new Member("Mr. Jame", new Point(99)); // ตัวอย่างข้อมูลสมาชิก
        Promotion promotion = new Promotion();
        Menu menu = new Menu(scanner);
        Spin spin = new Spin();
        boolean orderPlaced = false; // ตัวแปรใหม่เพื่อเช็คว่ามีการสั่งซื้อหรือไม่

        boolean running = true;
        while (running) {
            try {
                System.out.println("===================================");
                System.out.println(" WELCOME " + member.getName() + " TO THE CAFE ");
                System.out.println("===================================");
                System.out.println("1. View Promotion");
                System.out.println("2. View Member Info");
                System.out.println("3. View Menu");
                if (!orderPlaced) {
                    System.out.println("4. View Cart");
                }
                if (orderConfirmed) {
                    System.out.println("5. View Receipt"); // เพิ่มตัวเลือกนี้ถ้าการสั่งซื้อถูกยืนยันแล้ว
                }
                System.out.println("6. Spin for Promotion");
                System.out.println("7. Exit");
                System.out.println("===================================");
                System.out.print("Select an option: ");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        promotion.displayPromotion();
                        break;
                    case 2:
                        member.displayMember();
                        break;
                    case 3:
                        if (!orderConfirmed) {
                            promotionApplied = false; // Reset promotion applied status before selecting menu
                            menu.selectCategory(cart, member, promotion);
                        } else {
                            System.out.println("Order already confirmed. Please view receipt or make a new order.");
                        }
                        break;
                    case 4:
                        if (!orderConfirmed) {
                            cart.displayCart(member);
                        } else {
                            System.out.println("Order already confirmed.");
                        }
                        break;
                    case 5:
                        if (lastReceipt != null) {
                            lastReceipt.displayReceipt();
                            // รีเซ็ตคำสั่งซื้อสำหรับคำสั่งใหม่
                            resetOrder(cart);
                        } else {
                            System.out.println("No receipt to display.");
                        }
                        break;
                    case 6:
                        spin.spinPromotion(member, scanner);
                        String spinPromotion = spin.getSpinPromotion();
                        if (spinPromotion != null) {
                            promotion.setSpinPromotion(spinPromotion); // กำหนดโปรโมชั่นจาก Spin ให้กับ Promotion
                        }
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please select again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    // ฟังก์ชันรีเซ็ตคำสั่งซื้อสำหรับคำสั่งใหม่
    public static void resetOrder(Cart cart) {
        cart.clearCart(); // ล้างข้อมูลในตะกร้า
        orderConfirmed = false;
        promotionApplied = false;
    }
}
