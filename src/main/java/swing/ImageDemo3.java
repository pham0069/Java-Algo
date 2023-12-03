package swing;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDemo3 {
    public static void main(String[] args) throws IOException {
        JFrame frame = buildFrame();

        final BufferedImage image = ImageIO.read(new File("/Users/maxx/Documents/Code/Java-Algo/src/main/resources/swing/pop.png"));
        final BufferedImage resizedImage = resizeImage(image, 16, 16);

        ImageIcon icon = new ImageIcon(ImageDemo3.class.getResource("pop.png"));
        ImageIcon resizedIcon = resizeIcon(icon, 16, 16);

        ImageIcon improvedIcon = new ImageIcon(resizedImage);

        JPanel pane1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, 20, 20);
                g.drawImage(resizedImage, 2, 2, null);
            }
        };
        pane1.setPreferredSize(new Dimension(20, 20));

        JButton button = new JButton();
        button.setIcon(resizedIcon);

        JButton button2 = new JButton();
        button2.setIcon(improvedIcon);


        JPanel pane = new JPanel();
        pane.add(pane1, BorderLayout.CENTER);
        pane.add(button, BorderLayout.EAST);
        pane.add(button2, BorderLayout.WEST);

        frame.add(pane);
        frame.pack();
    }


    private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
        return frame;
    }

    static BufferedImage resizeImage(Image originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}
