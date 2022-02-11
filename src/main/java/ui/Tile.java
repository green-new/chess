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
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(0);

            // Color VBO
            colorVbo = glGenBuffers();
            float[] squareColor = Utils.hexTo3f(
                    this.isWhite() ? WhiteSquareColor : BlackSquareColor
            );
            colorBuffer = MemoryUtil.memAllocFloat(squareColor.length * 4);
            colorBuffer.put(squareColor).put(squareColor).put(squareColor).put(squareColor).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
            glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(1);

            // Index VBO
            idxVbo = glGenBuffers();
            int[] tileIdx = new int[]
                    {0, 1, 3, 3, 1, 2};
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
            if (colorBuffer != null) {
                MemoryUtil.memFree(colorBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    private float[] BuildTileVertices() {
        // Perform scale transformation based on board size
        float[] TileVertices = new float[]{
                (1.0f / Board.SIZE), (1.0f / Board.SIZE), 0.0f,
                (-1.0f / Board.SIZE), (1.0f / Board.SIZE), 0.0f,
                (-1.0f / Board.SIZE), (-1.0f / Board.SIZE), 0.0f,
                (1.0f / Board.SIZE), (-1.0f / Board.SIZE), 0.0f
        };

        // Perform translation transformation based on rank, file position
        // Normalize them
        // Rank and file domain: [0, 7]
        // New domain: [-1, 1].
        // Therefore, 0, 0 would be -1.0f, -1.0f.
        // 7, 7 would be +1.0, +1.0f.
        // Need to subtract these values.
        float NormalizedRank = Tile.normalize(this.rank) - 1.0f / 8.0f;
        float NormalizedFile = Tile.normalize(this.file) - 1.0f / 8.0f;

        // Subtract to vertices
        TileVertices[0] += NormalizedRank;
        TileVertices[1] += NormalizedFile;
        TileVertices[3] += NormalizedRank;
        TileVertices[4] += NormalizedFile;
        TileVertices[6] += NormalizedRank;
        TileVertices[7] += NormalizedFile;
        TileVertices[9] += NormalizedRank;
        TileVertices[10] += NormalizedFile;

        return TileVertices;
    }

    public boolean isWhite() {
        return (rank + file) % 2 == 0;
    }

    public int getVao() {
        return vao;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVbo);
        glDeleteBuffers(colorVbo);
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

    public static float normalize(int x) {
        x+=1;
        return 2.0f * ((float)x / (float)(Board.SIZE)) - 1.0f;
    }
}
