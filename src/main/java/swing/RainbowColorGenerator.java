package swing;

import java.awt.Color;

public class RainbowColorGenerator {
    private static final RainbowColor[] COLORS = RainbowColor.values();
    private int index = -1;
    private final int number;
    RainbowColorGenerator(int number) {
        this.number = number;
    }
    public Color nextColor() {
        index++;
        Color color = COLORS[index%COLORS.length].getColor();
        if (index == (number-1) && index % COLORS.length == 0)
            return COLORS[3].getColor();
        return color;
    }

    private Color getOppositeColor(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int r1 = max(r,b,g) + min(r,b,g) - r;
        int b1 = max(r,b,g) + min(r,b,g) - b;
        int g1 = max(r,b,g) + min(r,b,g) - g;
        return new Color(r1, b1, g1);
    }

    private int max(int r, int g, int b) {
        return Math.max(r, Math.max(g, b));
    }

    private int min(int r, int g, int b) {
        return Math.min(r, Math. min(g, b));
    }
}
