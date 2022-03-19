package Lab2;

public class Helpers {
    public static Point pointMulDouble(Point point, double number) {
        return new Point((int)(point.getX() * number), (int)(point.getY() * number));
    }

    public static Point pointPlusPoint(Point first, Point second) {
        return new Point(first.getX() + second.getX(), first.getY() + second.getY());
    }
}
