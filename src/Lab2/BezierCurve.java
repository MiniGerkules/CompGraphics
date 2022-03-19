package Lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class BezierCurve {
    private int tStep = 1;

    public void setTStep(int newTStep) {
        tStep = newTStep > 0 ? newTStep : tStep;
    }

    public List<Point> getBezierCurvePoints(List<Point> points) {
        List<Point> curve = new ArrayList<>();
        List<Point> preparedPoints = preparePoints(points);

        for (int i = 0; i < preparedPoints.size(); i += 4) {
            BiFunction<List<Point>, Integer, List<Point>> handler;

            if (preparedPoints.size() - i >= 4)
                handler = this::Bezie3Degree;
            else if (preparedPoints.size() - i >= 3)
                handler = this::Bezie2Degree;
            else if (preparedPoints.size() - i >= 2)
                handler = this::Bezie1Degree;
            else
                handler = this::Bezie0Degree;

            curve.addAll(handler.apply(preparedPoints, i));
        }

        return curve;
    }

    private List<Point> preparePoints(List<Point> points) {
        int size = points.size();
        if (size < 4)
            throw new IllegalArgumentException("The number of the points is less than 4!");

        Point specialPoint = getPointBetweenTwoLast(points.subList(0, 4));
        List<Point> result = new ArrayList<>(points.subList(0, 3));
        result.add(specialPoint);
        for (int i = 3; i < size; i += 2) {
            result.add(specialPoint);

            if (size - i >= 3) {
                specialPoint = getPointBetweenTwoLast(points.subList(i, i + 3));
                result.addAll(points.subList(i, i + 2));
                result.add(specialPoint);
            } else {
                result.addAll(points.subList(i, points.size()));
            }
        }

        return result;
    }

    private Point getPointBetweenTwoLast(List<Point> points) {
        if (points.size() >= 2) {
            Point last = points.get(points.size() - 1);
            Point preLast = points.get(points.size() - 2);
            return new Point((last.getX()+preLast.getX()) / 2, (last.getY()+preLast.getY()) / 2);
        } else if (points.size() == 1) {
            return points.get(0);
        } else {
            throw new IllegalArgumentException("The number of the points is 0!");
        }
    }

    private List<Point> Bezie3Degree(List<Point> setPoints, Integer currentIndex) {
        List<Point> result = new ArrayList<>(100 / tStep + 1);

        for (int t = 0; t <= 100; t += tStep) {
            double curT = t / 100d;
            Point newPoint = Helpers.pointMulDouble(setPoints.get(currentIndex), Math.pow(1 - curT, 3));
            newPoint = Helpers.pointPlusPoint(newPoint,
                    Helpers.pointMulDouble(setPoints.get(currentIndex + 1), 3*Math.pow(1 - curT, 2)*curT));
            newPoint = Helpers.pointPlusPoint(newPoint,
                    Helpers.pointMulDouble(setPoints.get(currentIndex + 2), 3*(1 - curT)*Math.pow(curT, 2)));
            newPoint = Helpers.pointPlusPoint(newPoint,
                    Helpers.pointMulDouble(setPoints.get(currentIndex + 3), Math.pow(curT, 3)));

            result.add(newPoint);
        }

        return result;
    }

    private List<Point> Bezie2Degree(List<Point> setPoints, Integer currentIndex) {
        List<Point> result = new ArrayList<>(100 / tStep + 1);

        for (int t = 0; t <= 100; t += tStep) {
            double curT = t / 100d;
            Point newPoint = Helpers.pointMulDouble(setPoints.get(currentIndex), Math.pow(1 - curT, 2));
            newPoint = Helpers.pointPlusPoint(newPoint,
                    Helpers.pointMulDouble(setPoints.get(currentIndex + 1), 2*curT*(1 - curT)));
            newPoint = Helpers.pointPlusPoint(newPoint,
                    Helpers.pointMulDouble(setPoints.get(currentIndex + 2), Math.pow(curT, 2)));

            result.add(newPoint);
        }

        return result;
    }

    private List<Point> Bezie1Degree(List<Point> setPoints, Integer currentIndex) {
        List<Point> newList = new ArrayList<>(setPoints);
        newList.add(setPoints.get(setPoints.size() - 1));

        return Bezie2Degree(newList, currentIndex);
//        return List.of(setPoints.get(currentIndex), setPoints.get(currentIndex + 1));
    }

    private List<Point> Bezie0Degree(List<Point> setPoints, Integer currentIndex) {
        return List.of(setPoints.get(currentIndex));
    }
}
