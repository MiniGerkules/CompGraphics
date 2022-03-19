package Lab1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class PaintField extends JPanel {
    private final Star star;
    private final Matrix3 matrix;
    private Point rotatePoint;
    private Point startPoint;

    public void setPoint(int x, int y) {
        rotatePoint = new Point(x, y);
    }

    public PaintField(Matrix3 matrix, Star star, Point rotatePointDefault) {
        this.star = star;
        this.matrix = matrix;
        rotatePoint = rotatePointDefault;

        setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);

        startPoint = new Point(getWidth() / 2, getHeight() / 2);

        g.drawLine(0, startPoint.getY(), getWidth(), startPoint.getY()); // 0X
        g.drawLine(startPoint.getX(), 0, startPoint.getX(), getHeight()); // OY
        g.fillOval(startPoint.getX() + rotatePoint.getX() - 7/2,
                startPoint.getY() - rotatePoint.getY() - 7/2, 7, 7);

        matrix.setM(rotatePoint.getX());
        matrix.setN(rotatePoint.getY());
        List<Point> points = star.transformStar(matrix).stream().map(this::makeRealCoords).toList();
        int[] starX = new int[points.size()];
        int[] starY = new int[points.size()];

        for (int i = 0; i < points.size(); ++i) {
            Point current = points.get(i);
            starX[i] = current.getX();
            starY[i] = current.getY();
        }

        g.drawPolyline(starX, starY, Star.numOfPoints);
    }

    private Point makeRealCoords(Point point) {
        return new Point(point.getX() + startPoint.getX() + rotatePoint.getX(),
                        point.getY() + startPoint.getY() - rotatePoint.getY());
    }
}
