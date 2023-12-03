package swing;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import org.apache.batik.swing.JSVGCanvas;

public class IconTest extends JPanel {

    public IconTest() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        this.setBackground(new Color(255, 255, 255));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // create icon body
        Ellipse2D iconBody = new Ellipse2D.Double(0, 0, 100, 100);
        g2d.setPaint(Color.YELLOW);
        g2d.draw(iconBody);
        g2d.fill(iconBody);

        // icon content
        JSVGCanvas svg = new JSVGCanvas();
        //svg.setURI("file:/Users/maxx/Documents/Code/Java-Algo/src/main/resources/swing/close.svg");
        svg.setURI("http://www.w3.org/2000/svg");
        add(svg);
    }

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("IconTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
        IconTest i = new IconTest();
        frame.add(i);


    }
}
