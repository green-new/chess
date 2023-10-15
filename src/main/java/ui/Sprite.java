package ui;

import engine.Texture;
import org.lwjgl.system.MemoryUtil;
import ui.vertex.VertexColor;
import ui.vertex.VertexData;
import ui.vertex.VertexPosition;
import ui.vertex.VertexTexture;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public abstract class Sprite {
    private final VertexData vertexData;
    private final Texture texture;
    private final int xOffset;
    private final int yOffset;
    private final int vao;
    private final int vbo;
    private final int ebo;

    protected Sprite(Texture texture, int xOffset, int yOffset, VertexData data) {
        this.vertexData = data;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.texture = texture;
        FloatBuffer vertexBuffer = data.getBuffer();
        IntBuffer indicesBuffer = data.getIndexBuffer();
        try {
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            // Vertex VBO
            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(VertexPosition.vaoIndex(), VertexPosition.dimension(), GL_FLOAT, false, 0, 0);
            glVertexAttribPointer(VertexTexture.vaoIndex(), VertexTexture.dimension(), GL_FLOAT, false, 0, 0);
            glVertexAttribPointer(VertexColor.vaoIndex(), VertexColor.dimension(), GL_FLOAT, false, 0, 0);

            // Index VBO
            ebo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (vertexBuffer != null) {
                MemoryUtil.memFree(vertexBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public void bindTexture() { this.bindTexture(GL_TEXTURE_2D); }
    public void bindTexture(final int textureMode) { glBindTexture(textureMode, this.texture.getTexid()); }

    public void destroy() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }
}
