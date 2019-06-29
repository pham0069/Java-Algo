package minima;

public class Test
{
    public static void main(String [] args)
    {
        Test t1 = new Test();
        System.out.println(t1);
        Test t2 = m1(t1); // line 6
        System.out.println(t1);
        System.out.println(t2);
        Test t3 = new Test();
        t2 = t3; // line 8

    }

    static Test m1(Test temp)
    {
        temp = new Test();
        return temp;
    }
}
