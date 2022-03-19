package Lab1;

import java.util.ArrayList;
import java.util.List;

class Star {
    public final static int numOfPoints = 6;
    private final int[] x;
    private final int[] y;
    private int scaler;

    public void setScaler(int newScaler) {
        scaler = newScaler > 0 ? newScaler : scaler;
    }

    public Star(int defaultScaler) {
        x = new int[] { -2, 0, 2, -4, 4, -2 };
        y = new int[] { 7, 0, 7, 3, 3, 7 };

        scaler = defaultScaler;
    }

    public List<Point> transformStar(Matrix3 matrix) {
        List<Point> points = new ArrayList<>(numOfPoints);

        for (int i = 0; i < numOfPoints; ++i) {
            Point current = new Point(x[i] * scaler, y[i] * scaler);
            points.add(matrix.getTransformedPoint(current));
        }

        return points;
    }
}
