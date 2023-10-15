package ui.vertex;

public record VertexTexture(float u, float v) implements IVertexAttribute<Float> {
    public static int vaoIndex() {
        return 1;
    }

    public static int dimension() {
        return 2;
    }

    @Override
    public Float[] flatArray() {
        return new Float[] {u, v};
    }
}
