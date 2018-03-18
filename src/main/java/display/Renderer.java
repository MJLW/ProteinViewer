package display;

import entities.Entity;
import math.Matrix4f;
import math.TransformationMatrix;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shaders.StaticShader;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Renderer {

    private static final float FOV = 70f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader) {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        setProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix4f(projectionMatrix);
        shader.stop();
    }

    public void clear() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0, 0.3f, 1);
    }

    public void render(Entity entity, StaticShader shader) {
        RawModel model = entity.getModel();
        GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        Matrix4f transformation = new TransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.loadTransformationMatrix4f(transformation);
        shader.loadColourVector(entity.getColour());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    private void setProjectionMatrix() {
        float aspectRatio = (float) Screen.WIDTH / (float) Screen.HEIGHT;
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.setValue(0, 0, xScale);
        projectionMatrix.setValue(1, 1, yScale);
        projectionMatrix.setValue(2, 2, -((FAR_PLANE + NEAR_PLANE) / frustumLength));
        projectionMatrix.setValue(3, 2, -1);
        projectionMatrix.setValue(2, 3, -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength));
        projectionMatrix.setValue(3, 3, 0);
    }
}
