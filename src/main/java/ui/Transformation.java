package ui;

import core.Board;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Transformation {
    /*
    Normalizes a value between -1 and 1 based on max.
    Returns: [-1, 1] depending on x.
     */
    public static float normalize(int x, int max) {
        return 2.0f * ((float)x / (float)(max)) - 1.0f;
    }

    /*
    Scales the array "array" of its positions by a factor of "scale".
     */
    public static void scale(float[] array, float scale) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= scale;
        }
    }

    /*
    Translates a 3-d array of vertices by x, y, z.
     */
    public static void translate(float[] array, float normalizedX, float normalizedY, float normalizedZ) {
        for (int i = 0; i < array.length; i += 3) {
            array[i] += normalizedX;
            array[i+1] += normalizedY;
            array[i+2] += normalizedZ;
        }
    }
}
