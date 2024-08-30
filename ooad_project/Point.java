public class Point {
    private int points;

    public Point(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void deductPoints(int points) {
        this.points -= points;
    }
}
