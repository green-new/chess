package ui.vertex;

public record VertexColor(float r, float g, float b) implements IVertexAttribute<Float> {
    public static int vaoIndex() {
        return 2;
    }

    public static int dimension() {
        return 3;
    }

    @Override
    public Float[] flatArray() {
        return new Float[] {r, g, b};
    }
}
