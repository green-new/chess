#version 330

out vec4 FragColor;

in vec2 tex;

uniform sampler2D tex_sampler;

// Add piece patterns in the future (wood, lighting, gradients, etc)

void main() {
    FragColor = texture(tex_sampler, tex);
}