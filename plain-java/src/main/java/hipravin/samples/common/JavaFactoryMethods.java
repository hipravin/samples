package hipravin.samples.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

/**
 * from, of, valueOf, getInstance, createInstance, getType, newType, type
 */
public class JavaFactoryMethods {
    public static void main(String[] args) throws IOException {
        //from - single argument
        Date date = Date.from(Instant.EPOCH);

        //of - aggregation
        Set<Integer> digits = Set.of(0,1,2,3,4,5,6,7,8,9);

        //valueOf - alternative of 'from' and 'of'
        BigInteger number = BigInteger.valueOf(1234567890);

        //getInstance - ?; createInstance, newInstance are similar but guarantee to return a different object
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

        //get<Type> - similar to getInstance if method resides in another class
        FileStore fs = Files.getFileStore(Paths.get(""));

        //new<Type> - similar to newInstance if method resides in another class
        try(BufferedReader reader = Files.newBufferedReader(Paths.get("sample.txt"))) {}

        //type - short version of get<Type> and new<Type>
        List list = Collections.list(Collections.enumeration(Arrays.asList(1,2,3))); //enumeration is old api (e.g. Ldap)
    }
}
