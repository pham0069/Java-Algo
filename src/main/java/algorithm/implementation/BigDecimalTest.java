package algorithm.implementation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * MathContext encapsulates both precision and rounding mode.
 * There are few predefined MathContexts:
 *
 * DECIMAL32 – 7 digits precision and a rounding mode of HALF_EVEN
 * DECIMAL64 – 16 digits precision and a rounding mode of HALF_EVEN
 * DECIMAL128 – 34 digits precision and a rounding mode of HALF_EVEN
 * UNLIMITED – unlimited precision arithmetic
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1);
        a = a.divide(new BigDecimal(7), MathContext.DECIMAL64);
        a = a.multiply(new BigDecimal(14), MathContext.UNLIMITED);
        //2.000000000000001 for DECIMAL64, 1.999999 for DECIMAL32
        System.out.println(a);

        double b = 1;
        b = b/7;
        b = b*14;
        System.out.println(b); // 2.0 ?
    }

}

class Test {
    static String ID = "TEST";
    public Test() {
        System.out.println(getID());
    }

    public String getID() {
        return ID;
    }

    public static void main(String[] args) {
        //Test test = new Test();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        //ZonedDateTime zonedDateTime = ZonedDateTime.from()parse("2015-05-05 10:15:30 Europe/Paris", formatter, TimeZone.getTimeZone("UTC"));
        final DateFormat df = new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println(df.parse("20210708-09:52:23.088").toInstant().toEpochMilli());
        } catch (Exception ex) {
            System.out.println(0);
        }
    }
}
