package engine;

import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    public final int texid;

    public Texture(String filename) throws Exception {
        this(loadTexture(filename));
    }

    public Texture(int texid) {
        this.texid = texid;
    }

    private static int loadTexture(String filename) throws Exception {
        int width;
        int height;
        ByteBuffer bb;// = ImageIO.read(new File(filename)).getRaster().getDataBuffer();
        try (MemoryStack ms = MemoryStack.stackPush()) {
            IntBuffer w = ms.mallocInt(1);
            IntBuffer h = ms.mallocInt(1);
            IntBuffer channels = ms.mallocInt(1);

            bb = stbi_load(filename, w, h, channels, 4);
            if (bb == null) {
                throw new Exception("Image file [" + filename  + "] not loaded: " + stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }

        // Create a new OpenGL texture
        int textureId = glGenTextures();
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, bb);
        // Generate Mip Map
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(bb);

        return textureId;
    }
    public void cleanup() {
        glDeleteTextures(this.texid);
    }
}
