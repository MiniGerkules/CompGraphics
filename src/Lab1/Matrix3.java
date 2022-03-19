package Lab1;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

class Matrix3 {
    private final List<List<Double>> matrix;
    private int m = 0, n = 0;

    public void setM(int m) {
        this.m = m;
    }
    public void setN(int n) {
        this.n = n;
    }

    public Matrix3() {
        matrix = new ArrayList<>(3);
        double cosA = 1; // cos(0) = 1
        double sinA = 0; // sin(0) = 0

        matrix.add(List.of(cosA, sinA, 0.0));
        matrix.add(List.of(-sinA, cosA, 0.0));
        matrix.add(List.of(-m*(cosA-1) + n*sinA, -n*(cosA-1) - m*sinA, 1.0));
    }

    public void updateMatrix(int angle) {
        double cosA = Math.cos(Math.toRadians(angle));
        double sinA = Math.sin(Math.toRadians(angle));

        matrix.set(0, List.of(cosA, sinA, 0.0));
        matrix.set(1, List.of(-sinA, cosA, 0.0));
        matrix.set(2, List.of(-m*(cosA-1) + n*sinA, -n*(cosA-1) - m*sinA, 1.0));
    }

    public Point getTransformedPoint(Point point) {
        int x = point.getX();
        int y = point.getY();
        double[] result = new double[matrix.size()];

        for (int i = 0; i < matrix.size(); i++) {
            double a = matrix.get(i).get(0);
            double b = matrix.get(i).get(1);
            double c = matrix.get(i).get(2);

            result[i] = x*a + y*b + c;
        }

        return new Point((int)result[0], (int)result[1]);
    }
}
