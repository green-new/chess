package ui;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Tile {
    private final int vertexCount = 6;

    private final int vao;

    private final int posVbo;

    private final int idxVbo;

    private int rank;

    private int file;



    public Tile(int rank, int file) {
        this.rank = rank;
        this.file = file;
        FloatBuffer posBuffer = null;
        IntBuffer indicesBuffer = null;
        // Allocate a buffer of floats of size 12 (each of 4 vertices has 3 components)
        try {
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            // Position VBO
            posVbo = glGenBuffers();
            float[] tilePos = new float[]{
                    -1.0f, -1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f,
                    1.0f, 1.0f, 0.0f,
                    -1.0f, 1.0f, 0.0f};
            posBuffer = MemoryUtil.memAllocFloat(tilePos.length);
            posBuffer.put(tilePos).flip();
            glBindBuffer(GL_ARRAY_BUFFER, posVbo);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Index VBO
            idxVbo = glGenBuffers();
            int[] tileIdx = new int[]{0, 1, 3, 3, 1, 2};
            indicesBuffer = MemoryUtil.memAllocInt(tileIdx.length);
            indicesBuffer.put(tileIdx).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVbo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public boolean isWhite() {
        return (rank + file) % 2 == 0;
    }

    public int getVao() {
        return vao;
    }

    public int getVertexCount() {
        return 6;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVbo);
        glDeleteBuffers(idxVbo);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }
}