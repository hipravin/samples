package hipravin.samples.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SymmetricCryptographyExample {

    private static final String secretToken = "ooc7slpvc61k7sf7ma7p4hrefr";
    private static final Map<String, String> callbackParams = Map.of(
            "checksum", "EAF2FB72CAB99FD5067F4BA493DD84F4D79C1589FDE8ED29622F0F07215AA972",
            "mdOrder", "06cf5599-3f17-7c86-bdbc-bd7d00a8b38b",
            "operation", "approved",
            "orderNumber", "2003",
            "status", "1"
    );

    private static final Map<String, String> callbackParamsHack = Map.of(
            "checksum", "EAF2FB72CAB99FD5067F4BA493DD84F4D79C1589FDE8ED29622F0F07215AA972",
            "mdOrder", "06cf5599-3f17-7c86-bdbc-bd7d00a8b38b",
            "operation", "approved",
            "orderNumber", "2003;status;1"
    );

    public static void main(String[] args) throws Exception {
        String signedString = callbackParams.entrySet().stream()
                .filter(e -> !e.getKey().equals("checksum"))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .flatMap(e -> Stream.of(e.getKey(), e.getValue()))
                .collect(Collectors.joining(";","",";"));

        System.out.println(signedString);

        byte[] mac = generateHMacSHA256(secretToken.getBytes(), signedString.getBytes());
        String signature = callbackParams.get("checksum");

        boolean verified = verifyMac(signature, mac);
        System.out.println("signature verification result: " + verified);
    }

    private static boolean verifyMac(String signature, byte[] mac) {
        return signature.equals(bytesToHex(mac));
    }

    public static byte[] generateHMacSHA256(byte[] hmacKeyBytes, byte[] dataBytes) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA256");

        Mac hMacSHA256 = Mac.getInstance("HmacSHA256");
        hMacSHA256.init(secretKey);

        return hMacSHA256.doFinal(dataBytes);
    }

    private static String bytesToHexOriginal(byte[] bytes) {
        final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    private static String bytesToHex(byte[] bytes) {
        return HexFormat.of().withUpperCase().formatHex(bytes);
    }
}