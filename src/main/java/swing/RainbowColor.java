package swing;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Color;

@AllArgsConstructor
public enum RainbowColor {
    RED(new Color(0xcb1f47)),
    ORANGE(new Color(0xf56f02)),
    YELLOW(new Color(0xffba00)),
    GREEN(new Color(0x04b390)),
    BLUE(new Color(0x0870cb)),
    PINK(new Color(0xff85c1)),
    VIOLET(new Color(0x645dac));

    @Getter
    private final Color color;
}

