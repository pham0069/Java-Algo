package data_structure;

public class Assert {
    public static void assertTrue(boolean condition, String message){
        if (!condition)
            System.out.println(message);
    }
}
