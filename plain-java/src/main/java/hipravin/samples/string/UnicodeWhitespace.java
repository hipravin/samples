package hipravin.samples.string;

import java.util.regex.Pattern;

public class UnicodeWhitespace {
    public static void main(String[] args) {
        //remove all white spaces including non-breaking space
        String whitespaced = " a\u00A0b  c\td\ne\rf ";

        //solution 1
        String whiteSpacesRemoved = whitespaced.replaceAll("(?U)\\s+", "");

        //solution 2
        Pattern whitespacePattern = Pattern.compile("\\s+", Pattern.UNICODE_CHARACTER_CLASS);
        String whiteSpacesRemoved2 = whitespacePattern.matcher(whitespaced).replaceAll("");

        System.out.println(whitespaced);
        System.out.println(whiteSpacesRemoved);
        System.out.println(whiteSpacesRemoved2);
    }
}
