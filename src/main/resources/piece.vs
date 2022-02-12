#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 tex;

out vec2 outTex;

void main() {
    gl_Position = vec4(position, 1.0);
    outTex = tex;
}