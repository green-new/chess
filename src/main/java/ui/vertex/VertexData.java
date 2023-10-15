package ui.vertex;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VertexData {
    private VertexPosition[] positions;
    private VertexColor[] colors;
    private VertexTexture[] textures;
    private VertexIndex[] indices;

    public VertexData(VertexPosition[] positions, VertexColor[] colors, VertexTexture[] textures, VertexIndex[] indices) {
        this.positions = positions;
        this.colors = colors;
        this.textures = textures;
        this.indices = indices;
    }

    public FloatBuffer getBuffer() throws RuntimeException, UnsupportedOperationException {
        FloatBuffer buffer = null;
        try {
            int n = (positions.length * positions[0].dimension()) +
                    (textures.length * textures[0].dimension()) +
                    (colors.length * colors[0].dimension());
            buffer = FloatBuffer.allocate(n);
            buffer.put((FloatBuffer) Arrays.stream(positions).map(VertexPosition::flatArray).flatMap(Arrays::stream));
            buffer.put((FloatBuffer) Arrays.stream(textures).map(VertexTexture::flatArray).flatMap(Arrays::stream));
            buffer.put((FloatBuffer) Arrays.stream(colors).map(VertexColor::flatArray).flatMap(Arrays::stream));
            buffer.flip();
        } catch (Exception e0) {
            e0.printStackTrace();
            throw e0;
        } finally {
            if (buffer != null) {
                MemoryUtil.memFree(buffer);
            }
        }
        return buffer;
    }

    public IntBuffer getIndexBuffer() throws RuntimeException {
        IntBuffer buffer = null;
        try {
            int n = indices.length * indices[0].dimension();
            buffer = IntBuffer.allocate(n);
            buffer.put((IntBuffer) Arrays.stream(indices).map(VertexIndex::flatArray).flatMap(Arrays::stream));
        } catch (Exception e0) {
            e0.printStackTrace();
            throw e0;
        } finally {
            if (buffer != null) {
                MemoryUtil.memFree(buffer);
            }
        }
        return buffer;
    }

    public int count() { return positions.length; }
    public VertexPosition[] getPositions() { return positions; }
    public VertexColor[] getColors() { return colors; }
    public VertexTexture[] getTextures() { return textures; }
    public VertexIndex[] getIndices() { return this.indices; }
    public void setPositions(VertexPosition[] positions) { this.positions = positions; }
    public void setColors(VertexColor[] colors) { this.colors = colors; }
    public void setTextures(VertexTexture[] textures) { this.textures = textures; }
    public void setIndices(VertexIndex[] indices) { this.indices = indices; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexData that = (VertexData) o;
        return Arrays.equals(positions, that.positions) && Arrays.equals(colors, that.colors) && Arrays.equals(textures, that.textures);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(positions);
        result = 31 * result + Arrays.hashCode(colors);
        result = 31 * result + Arrays.hashCode(textures);
        return result;
    }
}
