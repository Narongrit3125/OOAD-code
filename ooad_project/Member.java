public class Member {
    private String name;
    private Point point;

    public Member(String name, Point point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return point.getPoints(); // ดึงคะแนนจากออบเจ็กต์ Point
    }

    public void deductPoints(int points) {
        point.deductPoints(points); // หักคะแนนจากออบเจ็กต์ Point
    }

    public void addPoints(int points) {
        point.addPoints(points); // เพิ่มคะแนนให้กับสมาชิก
    }

    public void displayMember() {
        System.out.println("===================================");
        System.out.println("           MEMBER INFO");
        System.out.println("===================================");
        System.out.println("Name: " + name);
        System.out.println("Points: " + point.getPoints()); // แสดงคะแนนของสมาชิก
        System.out.println("===================================");
    }
}
