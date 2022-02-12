package ui;

public class BasicQuad {
    public static final float[] Vertices = new float[] {
            1.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f
    };

    public static final int[] Indices = new int[] {
            0, 1, 3, 3, 1, 2
    };
}
