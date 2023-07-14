package swing.scrollablePopupMenu;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScrollableMenuDemo extends JFrame  {
    private ScrollableMenu scrollablePopupMenu = new ScrollableMenu();

    public ScrollableMenuDemo() {
        super();
        this.setSize(274, 109);
        this.setContentPane(getBtnPopup());
        this.setTitle(" Scrollable JPopupMenu ");
    }

    private JButton getBtnPopup() {
        JButton jbnPopup = new JButton();
        jbnPopup.setText("View Scrollable popup menu ");


        jbnPopup.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Component source = (Component) e.getSource();
                scrollablePopupMenu.show(source, e.getX(), e.getY());
            }
        });

        return jbnPopup;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ScrollableMenuDemo thisClass = new ScrollableMenuDemo();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
}

class ScrollableMenu extends JPopupMenu {
    private final JPanel panelMenus = new JPanel();
    private final JScrollPane scroll = new JScrollPane();

    public ScrollableMenu() {
        super();
        this.setLayout(new BorderLayout());
        panelMenus.setLayout(new GridLayout(0, 1));
        panelMenus.setBackground(UIManager.getColor("MenuItem.background"));
        for (int i=0;i<60;i++){
            JCheckBoxMenuItem xx = new JCheckBoxMenuItem(" JMenuItem  " + (i+1)){
                @Override
                protected void processMouseEvent(MouseEvent evt) {
                    if (evt.getID() == MouseEvent.MOUSE_RELEASED && contains(evt.getPoint())) {
                        doClick();
                        //setArmed(true);
                    } else if (evt.getID() == MouseEvent.MOUSE_ENTERED) {
                        this.setBackground(Color.BLUE);
                        this.setForeground(Color.WHITE);
                    } else if (evt.getID() == MouseEvent.MOUSE_EXITED) {
                        this.setBackground(Color.WHITE);
                        this.setForeground(Color.BLACK);
                    } else {
                        super.processMouseEvent(evt);
                    }
                }

            };
            if(i == 2 || i == 7){
                panelMenus.add(new JSeparator());
            }
            panelMenus.add(xx);
        }

        scroll.setViewportView(panelMenus);
        scroll.setBorder(null);
        scroll.setMinimumSize(new Dimension(240, 40));
        scroll.setMaximumSize(new Dimension(240, 200));
        super.add(scroll, BorderLayout.CENTER);
    }

    public void show(Component invoker, int x, int y) {
        this.pack();
        this.setInvoker(invoker);
        this.setPopupSize(200, 200);
        Point invokerOrigin = invoker.getLocationOnScreen();
        this.setLocation((int) invokerOrigin.getX() + x, (int) invokerOrigin.getY() + y);
        this.setVisible(true);
    }

    class ScrollableMenuItem extends JCheckBoxMenuItem {
        ScrollableMenuItem(String text) {
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
//            } else {
//                super.processMouseEvent(evt);
            }

        }
    }
}
