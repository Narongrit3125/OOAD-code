import java.util.Scanner;
import java.util.Random;

public class Spin {
    private String spinPromotion;

    public void spinPromotion(Member member, Scanner scanner) {
        System.out.println("\n=====================================");
        System.out.println("          SPIN PROMOTION");
        System.out.println("=====================================");
        System.out.println("You have " + member.getPoints() + " points");
        System.out.println("=====================================");

        if (member.getPoints() < 10) {
            System.out.println("You do not have enough points to play Spin.");
            System.out.println("=====================================");
            return;
        }

        System.out.println("Available rewards:");
        System.out.println("1. 10 Baht Discount");
        System.out.println("2. 20 Baht Discount");
        System.out.println("3. 30 Baht Discount");
        System.out.println("4. 1 Free Drink");
        System.out.println("5. 1 Free Spin");
        System.out.println("6. Nothing");
        System.out.println("=====================================");

        System.out.println("Do you want to play Spin ?");
        System.out.println("1. Play Spin");
        System.out.println("2. Back to Welcome");
        System.out.print("Please choose an option: ");
        
        int choice = scanner.nextInt();
        System.out.println("=====================================");

        boolean keepSpinning = true;

        while (keepSpinning && choice == 1) {
            member.deductPoints(10);

            String[] promotions = {"10 Baht Discount", "20 Baht Discount", "30 Baht Discount", "1 Free Drink", "1 Free Spin", "Nothing"};

            Random rand = new Random();
            int randomIndex = rand.nextInt(promotions.length);
            spinPromotion = promotions[randomIndex];

            System.out.println("\nSpinning the wheel...");
            System.out.println("You won: " + spinPromotion);
            System.out.println("=====================================");

            if (spinPromotion.equals("1 Free Spin")) {
                System.out.println("You have earned a free spin!");
                System.out.println("=====================================");
            } else {
                keepSpinning = false;
            }
        }

        if (choice != 1) {
            System.out.println("Returning to Welcome...");
            System.out.println("=====================================");
        }
    }

    public String getSpinPromotion() {
        return spinPromotion;
    }

    public void resetSpinPromotion() {
        spinPromotion = null;
    }

    // ฟังก์ชันสำหรับการคำนวณโปรโมชั่นที่ได้รับ
    public int calculateDiscount(int total) {
        if (spinPromotion == null) {
            return 0;
        }

        switch (spinPromotion) {
            case "10 Baht Discount":
                return 10;
            case "20 Baht Discount":
                return 20;
            case "30 Baht Discount":
                return 30;
            default:
                return 0;
        }
    }

    public boolean hasFreeDrink() {
        return "1 Free Drink".equals(spinPromotion);
    }

    public void applyFreeDrink(Cart cart) {
        if (hasFreeDrink()) {
            cart.addItem("Free Drink", 0, 1);
            System.out.println("A free drink has been added to your cart!");
        }
    }
}
