package engine;
import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    public float[] hexTo3f(int hex) {
        int blueMask = 0xFF, greenMask = 0x00FF00, redMask = 0xFF0000;
        int r = hex & redMask;
        int g = hex & greenMask;
        int b = hex & blueMask;
        return new float[] {
                r / 255.0f,
                g / 255.0f,
                b / 255.0f
        };
    }

    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Utils.class.getResourceAsStream(fileName);
            Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

}