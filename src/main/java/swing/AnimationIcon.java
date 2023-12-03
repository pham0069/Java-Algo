package swing;

import javax.swing.Icon;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AnimationIcon implements Icon, ActionListener {

    private final List<Icon> frames;
    private final int intervalMillis;
    private final int width;
    private final int height;
    private Timer timer;
    private int index;

    public AnimationIcon(List<Icon> frames, int intervalMillis) {
        this.frames = frames;
        this.intervalMillis = intervalMillis;
        this.width = frames.isEmpty() ? 0 : frames.get(0).getIconWidth();
        this.height = frames.isEmpty() ? 0 : frames.get(0).getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (timer == null) {
            initTimer();
        }
        frames.get(index).paintIcon(c, g, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index = (index+1)%frames.size();
        //repaint();
    }

    private void initTimer() {
        index = 0;
        timer = new Timer(intervalMillis, this);
        timer.start();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
