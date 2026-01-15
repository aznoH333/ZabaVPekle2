#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

uniform float dir;


const int MAX_LIGHTS = 10;

struct Light {
    vec2 screenPosition;
    float intensityMultiplier;
};


uniform float[MAX_LIGHTS] lights;
uniform int usedLights;

Light getStrongestLight(vec2 uv) {
    float closestDistance = 99.0;
    Light closesestLight;

    for (int i = 0; i < usedLights; i++) {
        Light light = Light(vec2(lights[i * 3], lights[(i*3)+1]), lights[(i*3)+2]);

        if (length(light.screenPosition - uv) < closestDistance) {
            closestDistance = length(light.screenPosition - uv);
            closesestLight = light;
        }

    }

    return closesestLight;
}

float calculateLightStrength(Light light, vec2 uv) {

    float distanceFactor = (length(uv-light.screenPosition) / light.intensityMultiplier);


    return 1.0 - smoothstep(0.0, 1.0, distanceFactor);
}

void main() {
    vec2 uv = v_texCoords;
    vec2 normalized_uv = uv - 0.5;
    normalized_uv.x *= 1.777777;

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



    // light
    Light light = getStrongestLight(normalized_uv);
    float lightValue = calculateLightStrength(light, normalized_uv);
    texColor.rgb *= lightValue;

    // make pitch black if too dark
    if (texColor.r + texColor.g + texColor.b < 0.1) {
        texColor.rgb = vec3(0.0, 0.0, 0.0);
    }


    // Set final color
    gl_FragColor = texColor * v_color;
}