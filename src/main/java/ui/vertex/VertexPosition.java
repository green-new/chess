package ui.vertex;

public record VertexPosition(float x, float y) implements IVertexAttribute<Float> {
    public static int vaoIndex() {
        return 0;
    }

    public static int dimension() {
        return 2;
    }

    @Override
    public Float[] flatArray() {
        return new Float[] {x, y};
    }
}
