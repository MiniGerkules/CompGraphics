package Lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaintField extends JPanel {
    private int centerX = 0;
    private int centerY = 0;

    private final BezierCurve curve = new BezierCurve();
    private final List<Point> setPoints;

    public PaintField() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerX = e.getComponent().getWidth() / 2;
                centerY = e.getComponent().getHeight() / 2;
            }
        });

        setPoints = Stream.generate(() -> new Point(0, 0)).limit(Application.numOfPoints).collect(Collectors.toList());
    }

    public void setPointsForCurve(List<Point> points) {
        Collections.copy(setPoints, points);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawLine(centerX, 0, centerX, getHeight());
        g.drawLine(0, centerY, getWidth(), centerY);

        if (setPoints != null) {
            g.setColor(Color.BLACK);
            paintPolyline(g, setPoints);

            List<Point> points = curve.getBezierCurvePoints(setPoints);
            g.setColor(Color.RED);
            paintPolyline(g, points);
        }
    }

    private void paintPolyline(Graphics g, List<Point> points) {
        for (int i = 1; i < points.size(); ++i) {
            Point prev = points.get(i - 1);
            Point current = points.get(i);
            g.drawLine(centerX + prev.getX(), centerY - prev.getY(),
                    centerX + current.getX(), centerY - current.getY());
        }
    }
}
