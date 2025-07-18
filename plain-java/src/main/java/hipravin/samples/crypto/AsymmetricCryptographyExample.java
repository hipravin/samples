package hipravin.samples.crypto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collector;

public class AsymmetricCryptographyExample {

    private static final Map<String, String> callbackParams = Map.of(
            "amount", "35000099",
            "sign_alias", "SHA-256 with RSA",
            "checksum", "163BD9FAE437B5DCDAAC4EB5ECEE5E533DAC7BD2C8947B0719F7A8BD17C101EBDBEACDB295C10BF041E903AF3FF1E6101FF7DB9BD024C6272912D86382090D5A7614E174DC034EBBB541435C80869CEED1F1E1710B71D6EE7F52AE354505A83A1E279FBA02572DC4661C1D75ABF5A7130B70306CAFA69DABC2F6200A698198F8",
            "mdOrder", "12b59da8-f68f-7c8d-12b5-9da8000826ea",
            "operation", "deposited",
            "status", "1");

    private static final String certificate =
            "MIICcTCCAdqgAwIBAgIGAWAnZt3aMA0GCSqGSIb3DQEBCwUAMHwxIDAeBgkqhkiG9w0BCQEWEWt6" +
                    "bnRlc3RAeWFuZGV4LnJ1MQswCQYDVQQGEwJSVTESMBAGA1UECBMJVGF0YXJzdGFuMQ4wDAYDVQQH" +
                    "EwVLYXphbjEMMAoGA1UEChMDUkJTMQswCQYDVQQLEwJRQTEMMAoGA1UEAxMDUkJTMB4XDTE3MTIw" +
                    "NTE2MDEyMFoXDTE4MTIwNTE2MDExOVowfDEgMB4GCSqGSIb3DQEJARYRa3pudGVzdEB5YW5kZXgu" +
                    "cnUxCzAJBgNVBAYTAlJVMRIwEAYDVQQIEwlUYXRhcnN0YW4xDjAMBgNVBAcTBUthemFuMQwwCgYD" +
                    "VQQKEwNSQlMxCzAJBgNVBAsTAlFBMQwwCgYDVQQDEwNSQlMwgZ8wDQYJKoZIhvcNAQEBBQADgY0A" +
                    "MIGJAoGBAJNgxgtWRFe8zhF6FE1C8s1t/dnnC8qzNN+uuUOQ3hBx1CHKQTEtZFTiCbNLMNkgWtJ/" +
                    "CRBBiFXQbyza0/Ks7FRgSD52qFYUV05zRjLLoEyzG6LAfihJwTEPddNxBNvCxqdBeVdDThG81zC0" +
                    "DiAhMeSwvcPCtejaDDSEYcQBLLhDAgMBAAEwDQYJKoZIhvcNAQELBQADgYEAfRP54xwuGLW/Cg08" +
                    "ar6YqhdFNGq5TgXMBvQGQfRvL7W6oH67PcvzgvzN8XCL56dcpB7S8ek6NGYfPQ4K2zhgxhxpFEDH" +
                    "PcgU4vswnhhWbGVMoVgmTA0hEkwq86CA5ZXJkJm6f3E/J6lYoPQaKatKF24706T6iH2htG4Bkjre" +
                    "gUA=";

    public static void main(String[] args) throws Exception {

        String signedString = callbackParams.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("checksum") && !entry.getKey().equals("sign_alias"))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .collect(Collector.of(
                        StringBuilder::new,
                        (accumulator, element) -> accumulator
                                .append(element.getKey()).append(";")
                                .append(element.getValue()).append(";"),
                        StringBuilder::append,
                        StringBuilder::toString
                ));

        InputStream publicCertificate = new ByteArrayInputStream(Base64.getDecoder().decode(certificate));
        String signature = callbackParams.get("checksum");

        boolean verified = checkSignature(signedString.getBytes(), signature.getBytes(), publicCertificate);
        System.out.println("signature verification result: " + verified);
    }

    private static boolean checkSignature(byte[] signedString, byte[] signature, InputStream publicCertificate) throws Exception {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Cert = (X509Certificate) certFactory.generateCertificate(publicCertificate);

        Signature signatureAlgorithm = Signature.getInstance("SHA512withRSA");
        signatureAlgorithm.initVerify(x509Cert.getPublicKey());
        signatureAlgorithm.update(signedString);

        return signatureAlgorithm.verify(decodeHex(new String(signature)));
    }

    private static byte[] decodeHex(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}