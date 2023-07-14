package swing;

import javax.swing.*;
import java.awt.*;

public final class JGradientButtonDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("Gradient JButton Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.add(JGradientButton.newInstance());
        frame.setSize(new Dimension(300, 150)); // used for demonstration
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class JGradientButton extends JButton {
        private JGradientButton() {
            //setContentAreaFilled(true);
            //setFocusPainted(false); // used for demonstration
            setText("ABC");
        }

        @Override
        protected void paintComponent(Graphics g) {

            final Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(
                    0, 0,
                    Color.WHITE,
                    getWidth(), getHeight(),
                    Color.PINK.darker()));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.BLACK);
            //super.paintComponent(g);
            g2.drawString(this.getText(),
                    getWidth()/2 - (g2.getFontMetrics().stringWidth(getText())/2),
                    g2.getFontMetrics().getHeight());
        }

        public static JGradientButton newInstance() {
            return new JGradientButton();
        }
    }
}
