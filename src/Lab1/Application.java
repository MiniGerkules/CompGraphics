package Lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class Application {
    private final JFrame window = new JFrame();
    private final Container mainContainer = window.getContentPane();
    private final Matrix3 matrixToTransform = new Matrix3();
    private final Star star = new Star(5);
    private final PaintField paintField = new PaintField(matrixToTransform, star, new Point(0, 0));

    private final JButton help = new JButton("Помощь");
    private final JButton paint = new JButton("Построить");

    private final JPanel settings = new JPanel();
    private final Coordinate point1 = new Coordinate(0, 0);
    private final JTextField scale = new JTextField("5");
    private final JSlider angle = new JSlider(0, 360, 0);

    public Application() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int appSizeX = dimension.width / 2;
        int appSizeY = dimension.height / 2;
        int appBoundX = (dimension.width - appSizeX) / 2;
        int appBoundY = (dimension.height - appSizeY) / 2;

        window.setTitle("Lab1");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(appBoundX, appBoundY);

        mainContainer.setLayout(new GridBagLayout());
        mainContainer.setPreferredSize(new Dimension(appSizeX, appSizeY));
        window.pack();
    }

    public void startApplication() {
        prepareForDisplay();
    }

    private void prepareForDisplay() {
        mainContainer.removeAll();

        help.addActionListener(this::helpButtonClicked);
        addWithConstraints(mainContainer, help, 2, 0, 1, 1, 1, 1);

        prepareCoordsFields();
        addWithConstraints(mainContainer, settings, 2, 1, 1, 1, 1, 5);

        angle.setPaintLabels(true);
        angle.setMajorTickSpacing(90);
        addWithConstraints(mainContainer, angle, 2, 2, 1, 1, 1, 1);

        paint.addActionListener(this::paintButtonClicked);
        addWithConstraints(mainContainer, paint, 2, 3, 1, 1, 1, 1);

        addWithConstraints(mainContainer, paintField, 0, 0, 2, 4, 7, 8);

        window.setVisible(true);
    }

    private void paintButtonClicked(ActionEvent event) {
        int x, y, valAngle, scaler;

        try {
            x = point1.getValueX();
            y = point1.getValueY();
            scaler = Integer.parseInt(scale.getText());
            valAngle = angle.getValue();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainContainer, "Ошибка! Координаты или масштаб заданы не" +
                    "целыми числами! Попробуйте еще раз.");
            return;
        }

        paintField.setPoint(x, y);
        star.setScaler(scaler);
        matrixToTransform.updateMatrix(valAngle);
        paintField.repaint();
    }

    private void prepareCoordsFields() {
        settings.removeAll();
        settings.setLayout(new GridBagLayout());

        createPointFields("X1", point1.getX(), 0);
        point1.getX().setToolTipText("Координата X точки вращения");
        createPointFields("Y1", point1.getY(), 1);
        point1.getY().setToolTipText("Координата Y точки вращения");
        createPointFields("Масштаб", scale, 2);
        scale.setToolTipText("Число, задающее масштаб фигуры");
    }

    private void createPointFields(String labelX, JTextField point, int gridY) {
        JLabel label = new JLabel(labelX);
        label.setHorizontalAlignment(JLabel.RIGHT);
        addWithConstraints(settings, label, 0, gridY, 1, 1, 1, 1);
        addWithConstraints(settings, point, 1, gridY, 1, 1, 20, 1);
    }

    private void helpButtonClicked(ActionEvent event) {
        String message = "Для начала работы задайте точки прямой, масштаб фигуры и угол поворота. Когда все будет " +
                "готово, нажмите кнопку \"Построить\". Если вы хотите перестроить изображение,\nвведите желаемые " +
                "координаты прямой, масштаб и угол и опять нажмите на кнопку \"Построить\".";
        JOptionPane.showMessageDialog(mainContainer, message);
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
