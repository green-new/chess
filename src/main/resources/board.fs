#version 330

out vec4 FragColor;

uniform vec2 u_resolution;
uniform vec2 u_offset;

// Add tile patterns (wood, marble, etc)
// Give lifelike textures (optional)

float checker(vec2 uv, float repeats) {
  float cx = floor(repeats * uv.x);
  float cy = floor(repeats * uv.y);
  float result = mod(cx + cy, 2.0);
  return sign(result);
}

void main() {
    vec2 uv = (gl_FragCoord.xy - u_offset.xy) / u_resolution.xy;
    uv.x *= u_resolution.x / u_resolution.y;

    float c = checker(uv, 8.0);
    vec3 black_square = vec3(0.415, 0.125, 0.168);
    vec3 white_square = vec3(0.972, 0.964, 0.929);

    float pct = smoothstep(0.4, 1.0, distance(uv, vec2(0.5, 0.5)));
    vec3 vignette = vec3(pct);

    FragColor = vec4(clamp(vec3(c), black_square, white_square) - vignette, 1.0);
}