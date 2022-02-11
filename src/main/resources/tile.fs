#version 330

out vec4 FragColor;

in vec3 outColor;

uniform vec2 u_resolution;

void main() {
    vec2 st = gl_FragCoord.xy/u_resolution;
    float pct = 0.0;

    pct = distance(st, vec2(0.0,0.0));

    vec3 color = vec3(pct);

    FragColor = vec4(color * outColor, 1.0);
}