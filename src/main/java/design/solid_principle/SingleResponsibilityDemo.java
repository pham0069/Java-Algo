package design.solid_principle;

/**
 * Offload printing functionality to BookPrinter class
 * instead of putting it to Book class, which violates the single responsibility
 */
public class SingleResponsibilityDemo {
    class Book {
        private String name;
        private String author;
        private String text;

        //constructor, getters and setters

        // methods that directly relate to the book properties
        public String replaceWordInText(String word){
            return text.replaceAll(word, text);
        }

        public boolean isWordInText(String word){
            return text.contains(word);
        }

        /**
         *
        void printTextToConsole(){
            // our code for formatting and printing the text
        }
         */
    }

    class BookPrinter {

        // methods for outputting text
        void printTextToConsole(String text){
            //our code for formatting and printing the text
        }

        void printTextToAnotherMedium(String text){
            // code for writing to any other location..
        }
    }

}
