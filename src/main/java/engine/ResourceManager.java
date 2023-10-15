package engine;

import ui.shader.Shader;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ResourceManager {
    private final Resource<Shader> shaderResources;
    private final Resource<Texture> textureResources;

    public ResourceManager() {
        this.shaderResources = new Resource<>();
        this.textureResources = new Resource<>();
    }

    public Texture getTexture(Integer key) { return this.textureResources.get(key); }
    public Shader getShader(Integer key) { return this.shaderResources.get(key); }
}
