#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 squareColor;

out vec3 outColor;

uniform mat4 transformMat;

void main() {
    gl_Position = transformMat * vec4(position, 1.0);
    outColor = squareColor;
}