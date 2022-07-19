package ui;

import org.joml.Vector2f;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Checkerboard {

    private final int vao;

    private final int posVbo;

    private final int idxVbo;

    private static final int PosAttribIndex = 0;

    public Checkerboard() {
        FloatBuffer posBuffer = null;
        IntBuffer indicesBuffer = null;
        // Allocate a buffer of floats of size 12 (each of 4 vertices has 3 components)
        try {
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            // Position VBO
            posVbo = glGenBuffers();
            posBuffer = MemoryUtil.memAllocFloat(BasicQuad.Vertices.length);
            posBuffer.put(BasicQuad.Vertices).flip();
            glBindBuffer(GL_ARRAY_BUFFER, posVbo);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(PosAttribIndex, 3, GL_FLOAT, false, 0, 0);

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
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public int getVao() {
        return vao;
    }

    public void cleanup() {
        this.disableVertexAttribs();

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVbo);
        glDeleteBuffers(idxVbo);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }

    public int vertexCount() {
        return 6;
    }

    public void enableVertexAttribs() {
        glEnableVertexAttribArray(PosAttribIndex);
    }

    public void disableVertexAttribs() {
        glDisableVertexAttribArray(PosAttribIndex);
    }

    public int boardWidth() {
        return 920;
    }

    public int boardHeight() {
        return 920;
    }

    public int boardOffsetX() {
        return 52;
    }

    public int boardOffsetY() {
        return 52;
    }

    public Vector2f getRes2f() {
        return new Vector2f((float)this.boardWidth(), (float)this.boardHeight());
    }

    public Vector2f getOffset2f() {
        return new Vector2f((float)this.boardOffsetX(), (float)this.boardOffsetY());
    }
}
