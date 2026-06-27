package hipravin.samples.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


// also see:
// https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-libraries/src/main/java/com/baeldung/barcodes/generators

//Up to 7089 digits or 4296 characters, including punctuation marks and special characters, can be entered in one Code.
// In addition to numbers and characters, words and phrases (e.g. Internet addresses) can be encoded as well.
// As more data is added to the QR Code, the Code size increases and the Code structure becomes more complex.
public class QrSampleGen {
    public static void main(String[] args) throws WriterException, IOException {
//        String data = "https://example.com/parafin";
        String data = IntStream.range(0, 2048).mapToObj(i -> String.valueOf((char) ('a' + i % 25))).collect(Collectors.joining());
        System.out.println(data);
        int width = 300;
        int height = 300;
        Path imagePath = Path.of("qrcode.png");

        Map<EncodeHintType, Object> hints = new HashMap<>();
        // Уровень коррекции ошибок H — до 30% повреждений
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", imagePath);
        System.out.println("QR-код сохранён как " + imagePath);
    }


}
