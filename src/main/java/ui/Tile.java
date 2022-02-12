package ui;

import core.Board;
import engine.Utils;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Tile {

    private final int vao;

    private final int posVbo;

    private final int colorVbo;

    private final int idxVbo;

    private final int rank;

    private final int file;

    private static final int BlackSquareColor = 0x2a2a2a;

    private static final int WhiteSquareColor = 0x808080;

    private static final int PosAttribIndex = 0;

    private static final int ColorAttribIndex = 1;

    public Tile(int rank, int file) {
        this.rank = rank;
        this.file = file;
        FloatBuffer posBuffer = null;
        FloatBuffer colorBuffer = null;
        IntBuffer indicesBuffer = null;
        // Allocate a buffer of floats of size 12 (each of 4 vertices has 3 components)
        try {
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            // Position VBO
            posVbo = glGenBuffers();
            float[] tilePos = BuildTileVertices();
            posBuffer = MemoryUtil.memAllocFloat(tilePos.length);
            posBuffer.put(tilePos).flip();
            glBindBuffer(GL_ARRAY_BUFFER, posVbo);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(Tile.PosAttribIndex, 3, GL_FLOAT, false, 0, 0);

            // Color VBO
            colorVbo = glGenBuffers();
            float[] squareColor = Utils.hexTo3f(
                    this.isWhite() ? WhiteSquareColor : BlackSquareColor
            );
            colorBuffer = MemoryUtil.memAllocFloat(squareColor.length * 4);
            colorBuffer.put(squareColor).put(squareColor).put(squareColor).put(squareColor).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
            glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(Tile.ColorAttribIndex, 3, GL_FLOAT, false, 0, 0);

            this.enableVertexAttribs();

            // Index VBO
            idxVbo = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(BasicQuad.Indices.length);
            indicesBuffer.put(BasicQuad.Indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVbo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (colorBuffer != null) {
                MemoryUtil.memFree(colorBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    private float[] BuildTileVertices() {
        float[] clone = BasicQuad.Vertices.clone();
        float normalX = Transformation.normalize(this.rank, Board.SIZE);
        float normalY = Transformation.normalize(this.file, Board.SIZE);
        float normalZ = 0.0f;

        Transformation.scale(clone, 1.0f / Board.SIZE);
        Transformation.translate(clone, normalX + 0.125f, normalY + 0.125f, normalZ);

        return clone;
    }

    public boolean isWhite() {
        return (rank + file) % 2 == 0;
    }

    public int getVao() {
        return vao;
    }

    public void cleanup() {
        this.disableVertexAttribs();

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVbo);
        glDeleteBuffers(colorVbo);
        glDeleteBuffers(idxVbo);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }

    public int vertexCount() {
        return 6;
    }

    public void enableVertexAttribs() {
        glEnableVertexAttribArray(Tile.PosAttribIndex);
        glEnableVertexAttribArray(Tile.ColorAttribIndex);
    }

    public void disableVertexAttribs() {
        glDisableVertexAttribArray(Tile.PosAttribIndex);
        glDisableVertexAttribArray(Tile.ColorAttribIndex);
    }
}
