#version 330

out vec4 FragColor;

in vec3 outColor;

uniform vec2 u_resolution;

void main() {
    vec2 st = gl_FragCoord.xy/u_resolution;
    float pct = 0.0;

    pct = smoothstep(0.4, 1.0, distance(st, vec2(0.5,0.5)));

    vec3 color = vec3(pct);

    FragColor = vec4(outColor - color, 1.0);
}