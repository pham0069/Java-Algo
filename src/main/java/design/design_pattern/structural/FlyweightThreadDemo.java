package design.design_pattern.structural;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * https://sourcemaking.com/design_patterns/flyweight/java/4
 *
 * To resolve the issue raised by FlyweightThreadProblem, we convert to Flyweight pattern:
 * 1. ColorBox becomes Flyweight where color changing and repaint capabilities are shared
 * 2. Threaded behavior is made extrinsic. We can manually write a thread pool to handle the task of changing cell color
 * or make use of cached pool by Executors to schedule task on fixed rate
 *
 *
 */
public class FlyweightThreadDemo {
    static class ColorBox extends Canvas {
        private Color curColor = getColor();
        private static Color[]  colors = { Color.black, Color.blue, Color.cyan,
                Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.red,
                Color.magenta, Color.orange, Color.pink, Color.white, Color.yellow };

        private static Color getColor() {
            return colors[(int)(Math.random() * 1000) % colors.length];
        }

        void changeColor() {
            curColor = getColor();
            repaint();
        }

        public void paint(Graphics g) {
            g.setColor(curColor);
            g.fillRect( 0, 0, getWidth(), getHeight() );
        }
    }

    static class ThreadPool {
        private Vector boxes = new Vector();
        private int pause;

        class HandlerThread extends Thread {
            public void run() {
                while(true) {
                    ((ColorBox)boxes.elementAt(
                            (int)(Math.random()*1000) % boxes.size() )).changeColor();
                    try {
                        Thread.sleep(pause);
                    } catch(InterruptedException ignored) {}
                }
            }
        }

        ThreadPool(int p) {
            pause = p;
        }

        void register(ColorBox r) {
            boxes.addElement(r);
        }

        public void start() {
            int NUM_THREADS = 8;
            for (int i = 0; i < NUM_THREADS; i++) {
                new HandlerThread().start();
            }
        }
    }

    public static void main( String[] args ) {
        int size = 8;
        int pause = 100;

        //testThreadPool(size, pause);
        testExecutorService(size, pause);
    }

    private static void testThreadPool(int size, int pause) {
        Frame frame = new Frame("ColorBoxes - 8 shared HandlerThreads");
        frame.setLayout(new GridLayout(size, size));

        ThreadPool threadPool = new ThreadPool(pause);
        for (int i=0; i < size * size; i++) {
            ColorBox colorBox = new ColorBox();
            frame.add(colorBox);
            threadPool.register(colorBox);
        }

        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        threadPool.start();
    }

    private static void testExecutorService(int size, int pause) {
        Frame frame = new Frame("ColorBoxes - 8 shared HandlerThreads");
        frame.setLayout(new GridLayout(size, size));

        ScheduledExecutorService service = Executors.newScheduledThreadPool(8);
        ColorBox[] colorBoxes = new ColorBox[size*size];
        for (int i=0; i < size * size; i++) {
            colorBoxes[i] = new ColorBox();
            frame.add(colorBoxes[i]);
        }

        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                service.shutdown();
                System.exit(0);
            }
        });

        IntStream.range(0, size*size).boxed()
                .forEach(i -> service.scheduleAtFixedRate(() -> colorBoxes[i].changeColor(), pause, pause, TimeUnit.MILLISECONDS));

    }
}
