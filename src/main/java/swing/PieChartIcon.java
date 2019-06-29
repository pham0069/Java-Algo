package swing;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PieChartIcon implements Icon {
    private static final int TWO_PI = 360;
    private static final Color DEFAULT_FOREGROUND = new JLabel().getForeground();
    private final List<Slice> slices;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            Map<String, BigDecimal> model = new HashMap<>();
            model.put("A", BigDecimal.ONE);
            model.put("B", BigDecimal.ONE);
            model.put("C", BigDecimal.ONE);
            model.put("D", BigDecimal.ONE);
            model.put("E", BigDecimal.ONE);
            model.put("F", BigDecimal.ONE);
            model.put("G", BigDecimal.ONE);
            model.put("H", BigDecimal.ONE);
            model.put("I", BigDecimal.ONE);
            model.put("j", BigDecimal.ONE);
            model.put("k", BigDecimal.ONE);
            model.put("l", BigDecimal.ONE);
            model.put("m", BigDecimal.ONE);
            model.put("n", BigDecimal.ONE);
            model.put("o", BigDecimal.ONE);

            PieChartIcon icon = new PieChartIcon(model);
            JDialog dialog = new JDialog();
            JPanel panel = new JPanel();
            JLabel label = new JLabel(icon);
            panel.add(label);
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setVisible(true);
        });
    }
    public PieChartIcon(Map<String, BigDecimal> model) {
        this (new TreeMap<>(model));
    }

    public PieChartIcon(TreeMap<String, BigDecimal> model) {
        RainbowColorGenerator generator = new RainbowColorGenerator(model.size());
        this.slices = model.values().stream()
                .map(d -> new Slice(d.doubleValue(), generator.nextColor()))
                .collect(Collectors.toList());
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        drawPie((Graphics2D) g, new Rectangle(x, y, getIconWidth(), getIconHeight()));
    }

    private void drawPie(Graphics2D g, Rectangle area) {
        if (slices.isEmpty()) {
            g.setColor(DEFAULT_FOREGROUND);
            g.fillArc(area.x, area.y, area.width, area.height, 0, 360);
            return;
        }

        double total = slices.stream()
                .map(s -> s.value)
                .reduce(0d, (a, b)-> a+b);

        double accumulation = 0d;
        for (Slice slice : slices) {
            int startAngle =  90 - (int)(accumulation * TWO_PI / total);
            int arcAngle = (int) (slice.value * TWO_PI / total);
            g.setColor(slice.color);
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, -arcAngle);
            accumulation += slice.value;
        }
    }

    @Override
    public int getIconWidth() {
        return 500;
    }

    @Override
    public int getIconHeight() {
        return 500;
    }

    class Slice {
        double value;
        Color color;
        Slice(double value, Color color) {
            this.value = value;
            this.color = color;
        }
    }
}
