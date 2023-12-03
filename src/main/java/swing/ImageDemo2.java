package swing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.ImageIcon;

public class ImageDemo2 {

    static BufferedImage bi;
    public static void main(String[] args) throws IOException {

        Image img = new ImageIcon("pop.png").getImage();
        bi = resizeImage(img, 16, 16);

        Frame frame = new Frame("Example Frame");
        frame.setBackground(Color.GRAY);
// Add a component with a custom paint method
        frame.add(new CustomPaintComponent());
// Display the frame
        int frameWidth = 300;
        int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);
    }

    static class CustomPaintComponent extends Component {
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Image img = new ImageIcon("pop.png").getImage();
            g2d.drawImage(img, 0, 0, null);
        }
    }

    static BufferedImage resizeImage(Image originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

}
