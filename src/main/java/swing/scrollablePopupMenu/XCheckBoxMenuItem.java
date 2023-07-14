package swing.scrollablePopupMenu;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class XCheckBoxMenuItem extends JButton {
    /**
     * These colors are required in order to simulate the JMenuItem's L&F
     */
    public static final Color MENU_HIGHLIGHT_BG_COLOR = UIManager.getColor("MenuItem.selectionBackground");
    public static final Color MENU_HIGHLIGHT_FG_COLOR = UIManager.getColor("MenuItem.selectionForeground");
    public static final Color MENUITEM_BG_COLOR = UIManager.getColor("MenuItem.background");
    public static final Color MENUITEM_FG_COLOR = UIManager.getColor("MenuItem.foreground");

    //  This property if set to false, will result in the checked Icon not being displayed
    // when the button is selected
    private boolean	 displayCheck			= true;

    public XCheckBoxMenuItem(String text) {
        super(text);
        init();
    }

    /**
     * Initialize component LAF and add Listeners
     */
    private void init() {
        MouseAdapter mouseAdapter = getMouseAdapter();

        //	Basically JGoodies LAF UI for JButton does not allow Background color to be set.
        // So we need to set the default UI,
        ComponentUI ui = BasicButtonUI.createUI(this);
        this.setUI(ui);
        setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 2));
        setMenuItemDefaultColors();
        //        setContentAreaFilled(false);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setHorizontalAlignment(SwingConstants.LEFT);
        //        setModel(new JToggleButton.ToggleButtonModel());
        setModel(new XCheckedButtonModel());
        setSelected(false);
        this.addMouseListener(mouseAdapter);

    }

    private void setMenuItemDefaultColors() {
        this.setBackground(MENUITEM_BG_COLOR);
        this.setForeground(MENUITEM_FG_COLOR);
    }

    /**
     * @return
     */
    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            // For static menuitems, the background color remains the highlighted color, if this is not overridden
            public void mousePressed(MouseEvent e) {
                setMenuItemDefaultColors();
            }

            public void mouseEntered(MouseEvent e) {
                setBackground(MENU_HIGHLIGHT_BG_COLOR);
                setForeground(MENU_HIGHLIGHT_FG_COLOR);
            }

            public void mouseExited(MouseEvent e) {
                setMenuItemDefaultColors();
            }

        };
    }

    private class XCheckedButtonModel extends JToggleButton.ToggleButtonModel {
        /*
         * Need to Override keeping the super code, else the check mark won't come
         */
        public void setSelected(boolean b) {

            ButtonGroup group = getGroup();
            if (group != null) {
                // use the group model instead
                group.setSelected(this, b);
                b = group.isSelected(this);
            }

            if (isSelected() == b) {
                return;
            }

            if (b) {
                stateMask |= SELECTED;
            } else {
                stateMask &= ~SELECTED;
            }

            //			 Send ChangeEvent
            fireStateChanged();

            // Send ItemEvent
            fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, this,
                    this.isSelected() ? ItemEvent.SELECTED : ItemEvent.DESELECTED));

            //this.displayIcon(b);

        }

    }

// Returns true if Button will display Checked Icon on Click. Default Behaviour is to display a Checked Icon

    public boolean isDisplayCheck() {
        return displayCheck;
    }

    /**
     * Sets the property which determines whether a checked Icon should be displayed or not
     * Setting to false, makes this button display like a normal button
     * @param displayCheck
     */
    public void setDisplayCheck(boolean displayCheck) {
        this.displayCheck = displayCheck;
    }

}