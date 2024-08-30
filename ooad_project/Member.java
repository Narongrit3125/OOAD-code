public class Member {
    private String name;
    private Point point;

    // Constructor กำหนดชื่อและกำหนดค่า point หากยังไม่ได้ถูกกำหนดค่า
    public Member(String name, Point point) {
        this.name = name;
        this.point = (point != null) ? point : new Point(0); // กำหนดค่า point เป็น 0 หากไม่มีค่า
    }

    // Method รับชื่อ
    public String getName() {
        return name;
    }

    // Method รับคะแนน
    public int getPoints() {
        return (point != null) ? point.getPoints() : 0; // คืนค่า 0 หาก point ยังเป็น null
    }

    // Method หักคะแนน
    public void deductPoints(int points) {
        if (point == null) {
            point = new Point(0); // สร้างใหม่ถ้ายังไม่ถูกกำหนดค่า
        }
        point.deductPoints(points);
    }

    // Method เพิ่มคะแนน
    public void addPoints(int points) {
        if (point == null) {
            point = new Point(0); // สร้างใหม่ถ้ายังไม่ถูกกำหนดค่า
        }
        point.addPoints(points);
    }

    // Method แสดงข้อมูลสมาชิก
    public void displayMember() {
        System.out.println("===================================");
        System.out.println("           MEMBER INFO");
        System.out.println("===================================");
        System.out.println("Name: " + name);
        System.out.println("Points: " + getPoints()); // ใช้ getPoints() เพื่อดึงค่าแบบปลอดภัย
        System.out.println("===================================");
    }
}