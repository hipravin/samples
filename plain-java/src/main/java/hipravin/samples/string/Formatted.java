package hipravin.samples.string;

import java.util.List;
import java.util.Locale;

public class Formatted {
    public static void main(String[] args) {
        //%[argument_index$][flags][width][.precision]conversion

        //Conversions denoted by an upper-case character (i.e. 'B', 'H', 'S', 'C', 'X', 'E', 'G', 'A', and 'T')
        // are the same as those for the corresponding lower-case conversion characters except that the result
        // is converted to upper case according to the rules of the prevailing Locale.

        Locale.setDefault(Locale.ENGLISH);

        System.out.printf("escape: %% %n");

        String s = "string: %s".formatted("Pravin");//If arg implements Formattable, then arg.formatTo is invoked. Otherwise, the result is obtained by invoking arg.toString().
        System.out.println(s);

        String d = "integer/long: %d".formatted(Long.MAX_VALUE); //The result is formatted as a decimal integer (long also workd)
        System.out.println(d);

        String h = "hex: %h %h".formatted(Integer.MAX_VALUE, Integer.MIN_VALUE); //Integer.toHexString(), thus doesn't work with long
        System.out.println(h);

        String hd = "hexadecimal: %x %x".formatted(Integer.MAX_VALUE, Long.MIN_VALUE);
        System.out.println(hd);

        System.out.printf("reorder, reuse arguments: %4$s %3$s %2$s %1$s %4$s %3$s %2$s %1$s%n", "a", "b", "c", "d");

        var numbers = List.of(0L, 10L, 1_000_000L, Long.MAX_VALUE);
        numbers.forEach(n -> System.out.printf("%19d: column alignment%n", n));

        System.out.printf("scientific: %e  %1$E%n", 100.1);
    }
}

