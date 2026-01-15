#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;




/*
===========================================================
LIGHTS
===========================================================
*/
const int MAX_LIGHTS = 10;

struct Light {
    vec2 screenPosition;
    float intensityMultiplier;
};


uniform float[MAX_LIGHTS] lights;
uniform int usedLights;


float calculateLightValue(Light light, vec2 position) {
    return length(light.screenPosition - position) / light.intensityMultiplier;
}


Light getStrongestLight(vec2 position) {
    float closestDistance = 99.0;
    Light closesestLight;

    for (int i = 0; i < usedLights; i++) {
        Light light = Light(vec2(lights[i * 3], lights[(i*3)+1]), lights[(i*3)+2]);
        float lightValue = calculateLightValue(light, position);

        if (lightValue < closestDistance) {
            closestDistance = lightValue;
            closesestLight = light;
        }

    }

    return closesestLight;
}

float calculateLightStrength(Light light, vec2 position) {

    float distanceFactor = calculateLightValue(light, position);


    return 1.0 - smoothstep(0.0, 1.0, distanceFactor);
}

vec4 applyLights(vec2 position, vec4 color) {
    Light light = getStrongestLight(position);
    float lightValue = calculateLightStrength(light, position);
    color.rgb *= lightValue;

    return color;
}

/*
===========================================================
CRT
===========================================================
*/

vec4 applyCrt(vec2 position, vec4 color) {
    float scanline = 0.8 + (0.2 * sin(position.y * 1000.0));
    color.rgb *= scanline;
    return color;
}

/*
===========================================================
Vignette
===========================================================
*/
vec4 applyVignette(vec2 position, vec4 color) {
    // Apply vignette effect
    float vignette = 1.0 - smoothstep(0.4, 0.7, length(position));
    color.rgb *= vignette;
    return color;
}


/*
===========================================================
Color resolution
===========================================================
*/

float calculateRestrictedColorValue(float color, float resolution) {


    return color - mod(color, resolution);
}

vec4 restrictColorResolution(vec4 color, float resolution) {


    color.r = calculateRestrictedColorValue(color.r, resolution);
    color.g = calculateRestrictedColorValue(color.g, resolution);
    color.b = calculateRestrictedColorValue(color.b, resolution);


    return color;
}


/*
===========================================================
Main
===========================================================
*/
void main() {
    vec2 uv = v_texCoords;
    vec2 normalized_uv = uv - 0.5;
    normalized_uv.x *= 1.777777;



    // Sample the texture with adjusted UVs
    vec4 texColor = texture2D(u_texture, uv);


    texColor = applyVignette(normalized_uv, texColor);



    // light
    texColor = applyLights(normalized_uv, texColor);



    texColor = restrictColorResolution(texColor, 0.033);

    texColor = applyCrt(normalized_uv, texColor);


    // Set final color
    gl_FragColor = texColor * v_color;
}