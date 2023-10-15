package ui;

import core.ids.ResourceIds;
import core.ids.Shaders;
import ui.shader.Shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class RenderBoard extends Render {
    private Checkerboard cb;
    public RenderBoard(Checkerboard cb) {
        super(engine, shaders, states);
        this.cb = cb;
    }

    @Override
    public void init() {
/*        try {
            shader.createVertexShader(Utils.loadResource("/board.vs"));
            shader.createFragmentShader(Utils.loadResource("/board.fs"));
            shader.link();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void render() {
        Shader shader = (Shader) this.engine.getResource(ResourceIds.Shaders).get(Shaders.Board);
        shader.bind();
        glViewport(cb.boardOffsetX(), cb.boardOffsetY(), cb.boardWidth(), cb.boardHeight());
        shader.setVec2(cb.getRes2f(), "u_resolution");
        shader.setVec2(cb.getOffset2f(), "u_offset");

        // Render tile
        glBindVertexArray(cb.getVao());
        cb.enableVertexAttribs();

        glDrawElements(GL_TRIANGLES, cb.vertexCount(), GL_UNSIGNED_INT, 0);

        cb.disableVertexAttribs();
        glBindVertexArray(0);

        shader.unbind();
    }
}
