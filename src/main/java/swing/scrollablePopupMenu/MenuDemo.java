package swing.scrollablePopupMenu;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuDemo extends JFrame {
    JPopupMenu menu = new JPopupMenu();

    public MenuDemo() {
        super();
        this.setSize(274, 109);
        this.setContentPane(getBtnPopup());
        this.setTitle(" JPopupMenu ");
    }

    private JButton getBtnPopup() {
        JButton jbnPopup = new JButton();
        jbnPopup.setText("View popup menu ");

        for (int i = 0; i < 10; i++) {
            menu.add(new MyCheckboxMenuItem("Item " + (i+1)){

            });
        }

        jbnPopup.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Component source = (Component) e.getSource();
                menu.show(source, e.getX(), e.getY());
            }
        });

        return jbnPopup;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuDemo thisClass = new MenuDemo();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    public class MyCheckboxMenuItem extends JCheckBoxMenuItem {
        public final Color MENU_HIGHLIGHT_BG_COLOR = UIManager.getColor("MenuItem.selectionBackground");
        public final Color MENU_HIGHLIGHT_FG_COLOR = UIManager.getColor("MenuItem.selectionForeground");
        public final Color MENUITEM_BG_COLOR = UIManager.getColor("MenuItem.background");
        public final Color MENUITEM_FG_COLOR = UIManager.getColor("MenuItem.foreground");

        MyCheckboxMenuItem(String text) {
            super(text);
        }
        @Override
        protected void processMouseEvent(MouseEvent evt) {
            if (evt.getID() == MouseEvent.MOUSE_RELEASED && contains(evt.getPoint())) {
                doClick();
//            } else if (evt.getID() == MouseEvent.MOUSE_ENTERED && contains(evt.getPoint())) {
//                this.setBackground(MENU_HIGHLIGHT_BG_COLOR);
//                this.setForeground(MENU_HIGHLIGHT_FG_COLOR);
//            } else if (evt.getID() == MouseEvent.MOUSE_EXITED && contains(evt.getPoint())) {
//                this.setBackground(MENUITEM_BG_COLOR);
//                this.setForeground(MENUITEM_FG_COLOR);
            } else {
                super.processMouseEvent(evt);
            }
        }
    }
}
