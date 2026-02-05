#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;


uniform float loopedTimeValue;
/** a value representing the screen width / height */
uniform float aspectRatio;


/*
===========================================================
LIGHTS
===========================================================
*/
const int MAX_LIGHTS = 32;
const int LIGHT_COMPONENT_COUNT = 4;

struct Light {
    vec2 screenPosition;
    float intensityMultiplier;
    float lightBrightness;
};


uniform float[MAX_LIGHTS * LIGHT_COMPONENT_COUNT] lights;
uniform int usedLights;
uniform vec3 ambientLight;


float calculateLightValue(Light light, vec2 position) {
    return length(light.screenPosition - position) / light.intensityMultiplier;
}

float lightScatter(vec2 position) {
    return min(
        cos((position.x + loopedTimeValue) * 10.0) + cos((position.y - loopedTimeValue) * 10.0),
        sin((position.x - loopedTimeValue) * 5.0) + sin((position.y + loopedTimeValue) * 5.0)
    );
}


float calculateLightStrength(Light light, vec2 position) {

    float distanceFactor = calculateLightValue(light, position);
    // distanceFactor *= 1.0 - ((lightScatter(position) * distanceFactor) * 0.15);


    return light.lightBrightness - smoothstep(0.0, 1.0, distanceFactor);
}



float getLightValueForPosition(vec2 position) {
    float heighestValue = 0.0;

    for (int i = 0; i < usedLights; i++) {
        Light light = Light(
        vec2(
        lights[i * LIGHT_COMPONENT_COUNT],
        lights[(i * LIGHT_COMPONENT_COUNT)+1]
        ),
        lights[(i*LIGHT_COMPONENT_COUNT)+2],
        lights[(i*LIGHT_COMPONENT_COUNT)+3]);

        float lightValue = calculateLightStrength(light, position);

        if (lightValue > heighestValue) {
            heighestValue = lightValue;
        }

    }

    return heighestValue;
}


vec4 applyLights(vec2 position, vec4 color) {
    float lightValue = getLightValueForPosition(position);
    color.rgb *= vec3(lightValue, lightValue, lightValue) + ambientLight;

    return color;
}



/*
===========================================================
CRT
===========================================================
*/

vec4 applyCrt(vec2 position, vec4 color, float scanLineIntensity, float numberOfScanLines) {
    float scanline = (1.0 - scanLineIntensity) + (scanLineIntensity * sin(position.y * numberOfScanLines));
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
    float vignette = 1.0 - smoothstep(0.0, 1.0, length(position) - 0.5);
    color.rgb *= vignette;
    return color;
}


/*
===========================================================
Chromatic Aberration
===========================================================
*/
vec4 applyChromaticAberration(vec2 position, vec4 color) {
    // Define the offset for RGB channels
    float offset = 0.010 * smoothstep(0.0, 1.0, length(position - 0.5) - 0.1); // Adjust offset based on distance from center

    // Sample the color with offsets
    vec4 rColor = texture2D(u_texture, position + vec2(-offset, 0.0)); // Red channel
    vec4 gColor = texture2D(u_texture, position);                        // Green channel
    vec4 bColor = texture2D(u_texture, position + vec2(offset, 0.0));  // Blue channel

    // Combine colors with equal weight
    return vec4(rColor.r, gColor.g, bColor.b, color.a);
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
Screen brigness
===========================================================
*/
uniform float screenBrightness;


vec4 applyScreenBrightness(vec4 color) {
    color.r *= screenBrightness;
    color.g *= screenBrightness;
    color.b *= screenBrightness;

    return color;
}

/*
===========================================================
Main
===========================================================
*/
void main() {
    vec2 centeredPosition = v_texCoords - 0.5;
    centeredPosition.x *= 1.77777;



    // Sample the texture with adjusted UVs
    vec4 texelColor = texture2D(u_texture, v_texCoords);




    texelColor = applyChromaticAberration(v_texCoords, texelColor);

    // light
    texelColor = applyLights(centeredPosition, texelColor);

    texelColor = applyVignette(centeredPosition, texelColor);
    texelColor = applyScreenBrightness(texelColor);

    //texColor = restrictColorResolution(texColor, 0.033);
    texelColor = restrictColorResolution(texelColor, 0.0099);


    texelColor = applyCrt(centeredPosition, texelColor, 0.20, 1000.0);

    // Set final color
    gl_FragColor = texelColor * v_color;
}