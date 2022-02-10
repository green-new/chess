package ui.shader;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int program;

    private int vertexShader;

    private int fragmentShader;

    public Shader() throws Exception{
        program = glCreateProgram();
        if (program == 0) {
            throw new Exception("Error creating shader program");
        }
    }

    public void createVertexShader(String path) throws Exception {
        vertexShader = createShader(path, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String path) throws Exception {
        fragmentShader = createShader(path, GL_FRAGMENT_SHADER);
    }

    private int createShader(String path, int shaderType) throws Exception {
        int shader = glCreateShader(shaderType);
        if (shader == 0) {
            throw new Exception("Error creating shader of type " + shader);
        }

        glShaderSource(shader, path);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling shader code: " + glGetShaderInfoLog(shader, 1024));
        }
        glAttachShader(program, shader);

        return shader;
    }

    public void link() throws Exception {
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking shader code: " + glGetProgramInfoLog(program, 1024));
        }

        if (vertexShader != 0) {
            glDetachShader(program, vertexShader);
        }
        if (fragmentShader != 0) {
            glDetachShader(program, fragmentShader);
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating shader code: " + glGetProgramInfoLog(program, 1024));
        }

    }

    public void setInt(int value, String name) {
        glUniform1i(glGetUniformLocation(program, name), value);
    }

    public void setFloat(float value, String name) {
        glUniform1f(glGetUniformLocation(program, name), value);
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (program != 0) {
            glDeleteProgram(program);
        }
    }
}
