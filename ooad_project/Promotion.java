import java.util.ArrayList;
import java.util.List;

public class Promotion {
    private String spinPromotion; // ตัวแปรเพื่อเก็บโปรโมชั่นจาก Spin
    private List<String> availablePromotions; // รายการโปรโมชั่นที่สามารถใช้ได้

    public Promotion() {
        this.spinPromotion = null; // กำหนดค่าเริ่มต้นให้เป็น null
        this.availablePromotions = new ArrayList<>();
        this.availablePromotions.add("Buy over $200, get 10% off!"); // เพิ่มโปรโมชั่นเริ่มต้น
    }

    public String getSpinPromotion() {
        return spinPromotion;
    }

    public void setSpinPromotion(String spinPromotion) {
        this.spinPromotion = spinPromotion;
        if (spinPromotion != null && !availablePromotions.contains(spinPromotion)) { // ตรวจสอบว่ามีโปรโมชั่นนี้อยู่แล้วหรือไม่
            this.availablePromotions.add(spinPromotion); // เพิ่มโปรโมชั่นจาก Spin ถ้ายังไม่มี
        }
    }

    public void displayPromotion() {
        System.out.println("===================================");
        System.out.println("          CURRENT PROMOTION");
        System.out.println("===================================");

        for (int i = 0; i < availablePromotions.size(); i++) {
            System.out.println((i + 1) + ". " + availablePromotions.get(i));
        }

        System.out.println("===================================");
    }

    public double applyDiscount(double total) {
        if (total > 200) { // ตรวจสอบว่าถ้ายอดรวมเกิน $200 จะได้รับส่วนลด 10%
            return total * 0.10; // คืนค่า 10% ของยอดรวมเป็นส่วนลด
        }
        return 0; // ถ้าไม่เข้าเงื่อนไข คืนค่า 0 แปลว่าไม่มีส่วนลด
    }

    public void resetSpinPromotion() {
        this.spinPromotion = null;
    }

    // ฟังก์ชันสำหรับการลบโปรโมชั่นที่ใช้ไปแล้ว
    public void removePromotion(int index) {
        if (index >= 0 && index < availablePromotions.size()) {
            availablePromotions.remove(index);
        }
    }

    public List<String> getAvailablePromotions() {
        return availablePromotions;
    }
}
