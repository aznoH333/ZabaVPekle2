#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
    vec2 uv = v_texCoords;

    // Add curvature for the CRT effect
    float curvature = 0.05;
    vec2 centeredUV = uv - 0.5;
    centeredUV *= 1.0 + curvature * length(centeredUV);
    centeredUV += 0.5;

    // Sample the texture with adjusted UVs
    vec4 texColor = texture2D(u_texture, centeredUV);

    // Apply a basic scanline effect
    float scanline = 0.8 + (0.2 * sin(uv.y * 1000.0));
    texColor.rgb *= scanline;

    // Apply vignette effect
    float vignette = 1.0 - smoothstep(0.4, 0.7, length(uv - 0.5));
    texColor.rgb *= vignette;

    // Set final color
    gl_FragColor = texColor * v_color;
}