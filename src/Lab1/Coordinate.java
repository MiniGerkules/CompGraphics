package Lab1;

import javax.swing.JTextField;

public class Coordinate {
    private final JTextField x;
    private final JTextField y;

    public JTextField getX() {
        return x;
    }
    public JTextField getY() {
        return y;
    }

    public int getValueX() {
        return Integer.parseInt(x.getText());
    }
    public int getValueY() {
        return Integer.parseInt(y.getText());
    }

    public Coordinate(int defaultX, int defaultY) {
        x = new JTextField();
        y = new JTextField();

        x.setText(String.valueOf(defaultX));
        y.setText(String.valueOf(defaultY));
    }
}
