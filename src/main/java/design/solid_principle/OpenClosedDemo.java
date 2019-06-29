package design.solid_principle;

/**
 * Let say a program is stable and working well with Guitar class
 * Now you want to add a new feature to Guitar, like flame
 * It is tempting to directly modify Guitar, however who knows if this might throw new bug/error in our app
 * Instead one option is extending Guitar to include new feature flameColor
 */
public class OpenClosedDemo {
    class Guitar {

        private String make;
        private String model;
        private int volume;

        //Constructors, getters & setters
    }

    class SuperCoolGuitarWithFlames extends Guitar {

        private String flameColor;

        //constructor, getters + setters
    }
}
