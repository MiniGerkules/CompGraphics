package Lab2;

import javax.swing.*;

public class PointContainer {
    private final JTextField xTextField = new JTextField();
    private final JTextField yTextField = new JTextField();
    private final Point point;

    public JTextField getXTextField() {
        return xTextField;
    }
    public JTextField getYTextField() {
        return yTextField;
    }
    public Point getPoint() {
        return point;
    }

    public void updatePoint() {
        int x, y, z;

        x = Integer.parseInt(xTextField.getText());
        y = Integer.parseInt(yTextField.getText());

        point.setX(x);
        point.setY(y);
    }

    public PointContainer(int x, int y) {
        point = new Point(x, y);

        xTextField.setText(String.valueOf(x));
        yTextField.setText(String.valueOf(y));
    }
}
