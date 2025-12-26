package hipravin.samples.tasks;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

//Compress the string "aabcccccaaa" into "a2b1c5a3" with minimal space complexity.
public class Task1 {
//    private static final String ALFABET_PATTERN = "abcdefghiklmnopqrtsuvwzyz".chars().mapToObj(c -> "(" + (char) c + "+)")
//            .collect(Collectors.joining("|"));
//    private static final Pattern ANY_LETTER_REPEATING = Pattern.compile(ALFABET_PATTERN);


    private static final Pattern ANY_LETTER_REPEATING = Pattern.compile("((.)\\2*)");

    public static String compress(String s) {
        return ANY_LETTER_REPEATING.matcher(s).results()
                .map(mr -> s.substring(mr.start(), mr.start() + 1) + (mr.end() - mr.start()))
                .collect(Collectors.joining());
    }

    @Test
    void testSample() {
        assertEquals("a2b1c5a3", compress("aabcccccaaa"));
    }
}
