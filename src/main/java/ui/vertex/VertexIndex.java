package ui.vertex;

public record VertexIndex(int i) implements IVertexAttribute {
    public static int vaoIndex() {
        return 0;
    }

    public static int dimension() {
        return 1;
    }

    @Override
    public Object[] flatArray() {
        return new Object[0];
    }
}
