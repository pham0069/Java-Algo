package swing;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.common.collect.Lists;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class AnimationIconLabel extends JLabel implements ActionListener {

    private final List<Icon> icons;
    private final int intervalMillis;
    private Timer timer;
    private int index;

    public AnimationIconLabel(List<Icon> icons, int intervalMillis) {
        this.icons = icons;
        this.intervalMillis = intervalMillis;
        initTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index = (index+1)%icons.size();
        this.setIcon(icons.get(index));
    }

    private void initTimer() {
        index = icons.size()-1;
        timer = new Timer(intervalMillis, this);
        timer.start();
    }

    public static final List<Icon> ICONS =
            Lists.newArrayList(
                    new FlatSVGIcon(AnimationIconLabel.class.getResource("waiting-1.svg")),
                    new FlatSVGIcon(AnimationIconLabel.class.getResource("waiting-2.svg")),
                    new FlatSVGIcon(AnimationIconLabel.class.getResource("waiting-3.svg"))
            );

    public static void main(String[] args) {
        JFrame frame = new JFrame("IconTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
        AnimationIconLabel label = new AnimationIconLabel(ICONS,500);
        frame.add(label);
    }
}
