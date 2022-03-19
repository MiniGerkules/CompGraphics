package Lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

public class Application {
    private final JFrame window = new JFrame();
    private final Container mainContainer = window.getContentPane();
    private final PaintField paintField = new PaintField();

    public static int numOfPoints = 11;
    private final List<PointContainer> points = new ArrayList<>(numOfPoints);

    private final JButton help = new JButton("Помощь");
    private final JButton paint = new JButton("Построить");

    private final List<Integer> xDefault = List.of(-250, -200, -150, -100, -50, 0, 50, 100, 150, 200, 250);
    private final List<Integer> yDefault = List.of(-100, 80, -80, 80, -80, 80, -80, 80, -80, 80, -80);

    public Application() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width / 2;
        int height = screen.height / 2;

        window.setTitle("Lab2");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocation((screen.width - width) / 2, (screen.height - height) / 2);

        mainContainer.setPreferredSize(new Dimension(width, height));
        mainContainer.setLayout(new GridBagLayout());
        window.pack();

        help.addActionListener(this::helpButtonClicked);
        paint.addActionListener(this::paintButtonClicked);
        prepareWindow();
    }

    public void start() {
        window.setVisible(true);
    }

    private void helpButtonClicked(ActionEvent event) {
        String message = "Для начала работы задайте координаты точек. Если координаты вас устраивают, нажмите кнопку " +
                "\"Построить\", чтобы построить кривую Безье.\nЧтобы перерисовать кривую, задайте новые координаты " +
                "точек и снова нажмите кнопку \"Построить\".";
        JOptionPane.showMessageDialog(mainContainer, message);
    }

    private void paintButtonClicked(ActionEvent event) {
        List<Point> curvePoints = new ArrayList<>(numOfPoints);

        for (PointContainer point : points) {
            point.updatePoint();
            curvePoints.add(point.getPoint());
        }

        paintField.setPointsForCurve(curvePoints);
        paintField.repaint();
    }

    private void prepareWindow() {
        addWithConstraints(mainContainer, paintField, 0, 0, 1,
                GridBagConstraints.REMAINDER, 20, 1);
        addWithConstraints(mainContainer, help, 1, 0, GridBagConstraints.REMAINDER,
                1, 1, 1);

        initTextFields();
        addTextFields(mainContainer, 1, 1);
        paintButtonClicked(null);

        addWithConstraints(mainContainer, paint, 1, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER,
                1, 1, 1);
    }

    private void initTextFields() {
        for (int i = 0; i < numOfPoints; ++i)
            points.add(new PointContainer(xDefault.get(i), yDefault.get(i)));
    }

    private void addTextFields(Container container, int xBias, int yBias) {
        for (int i = 0; i < points.size(); ++i) {
            PointContainer point = points.get(i);

            Label xLabel = new Label("x" + (i + 1), Label.CENTER);
            addWithConstraints(container, xLabel, xBias, yBias + i, 1,
                    1, 1, 1);
            addWithConstraints(container, point.getXTextField(), GridBagConstraints.RELATIVE, yBias + i, 1,
                    1, 1, 1);

            Label yLabel = new Label("y" + (i + 1), Label.CENTER);
            addWithConstraints(container, yLabel, GridBagConstraints.RELATIVE, yBias + i, 1,
                    1, 1, 1);
            addWithConstraints(container, point.getYTextField(), GridBagConstraints.RELATIVE, yBias + i, 1,
                    1, 1, 1);
        }
    }

    private void addWithConstraints(Container container, Component toAdd, int gridX, int gridY,
                                    int gridWidth, int gridHeight, int weightX, int weightY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(2, 2, 2, 2);

        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        constraints.weightx = weightX;
        constraints.weighty = weightY;

        container.add(toAdd, constraints);
    }
}
