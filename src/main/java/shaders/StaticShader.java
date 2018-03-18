package shaders;

import display.Camera;
import math.Matrix4f;
import math.Vector3f;
import math.ViewMatrix;

public class StaticShader extends Shader {

    private static final String VERTEX_FILE = "src/main/resources/shaders/vertexShader";
    private static final String FRAGMENT_FILE = "src/main/resources/shaders/FragmentShader";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int colourVectorLocation;

    public StaticShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        colourVectorLocation = super.getUniformLocation("colourIn");
    }

    public void loadTransformationMatrix4f(Matrix4f matrix) {
        super.loadUniformMatrix4f(transformationMatrixLocation, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        ViewMatrix viewMatrix = new ViewMatrix(camera);
        super.loadUniformMatrix4f(viewMatrixLocation, viewMatrix);
    }

    public void loadProjectionMatrix4f(Matrix4f matrix) {
        super.loadUniformMatrix4f(projectionMatrixLocation, matrix);
    }

    public void loadColourVector(Vector3f vector) {
        super.loadUniformVector3f(colourVectorLocation, vector);
    }
}
