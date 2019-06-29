package design.design_pattern.structural;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * In this demo, we create a UI which is a grid of cells, where each cell can change to another random color after a
 * pause time. For this purpose, we can use a thread to sleep during pause time and repaint cell to the new color
 * in alternative intervals
 *
 * The straightforward way is creating a thread per cell. The problem is we need to create a lot of threads for a big
 * grid. It's a lot of overhead of context switching.
 */
public class FlyweightThreadProblem {
    static class ColorBox extends Canvas implements Runnable {
        private int    pause;
        private Color curColor = getColor();
        private static Color[]  colors = {Color.black, Color.blue, Color.cyan,
                Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.red,
                Color.magenta, Color.orange, Color.pink, Color.white, Color.yellow};

        ColorBox(int p) {
            pause = p;
            new Thread(this).start();
        }

        private static Color getColor() {
            return colors[(int)(Math.random() * 1000) % colors.length];
        }

        @Override
        public void run() {
            while (true) {
                curColor = getColor();
                repaint();
                try {
                    Thread.sleep(pause);
                } catch(InterruptedException ignored) { }
            }
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(curColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main( String[] args ) {
        int size = 8;
        int pause = 100;

        Frame frame = new Frame( "ColorBoxes - 1 thread per ColorBox" );
        frame.setLayout( new GridLayout( size, size ) );
        for (int i=0; i < size*size; i++) {
            frame.add( new ColorBox(pause));
        }
        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println(Thread.activeCount());
                System.exit(0);
            }
        });
    }
}
