package math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Vector;

public class Matrix4f {

    private final int LENGTH = 16;
    protected float[] values;

    public Matrix4f() {
        set(new float[LENGTH]);
    }

    public Matrix4f(float[] values) {
        set(values);
    }

    public Matrix4f(Matrix4f matrix) {
        set(matrix.getValues());
    }

    public void set(Matrix4f matrix) {
        this.values = getValues();
    }

    public void set(float[] values) {
        this.values = values;
    }

    public void setAsIdentityMatrix() {
        set(getIdentityMatrix());
    }

    public static float[] getIdentityMatrix() {
        float[] identity = {1f, 0, 0, 0,
                0, 1f, 0, 0,
                0, 0, 1f, 0,
                0, 0, 0, 1f};
        return identity;
    }

    public void translate(float x, float y, float z) {
        addValue(3, 0, x);
        addValue(3, 1, y);
        addValue(3, 2, z);
    }

    public void translate(Vector3f vector) {
        translate(vector.getX(), vector.getY(), vector.getZ());
    }

    public void rotateX(double theta) {
        multiplyValue(1, 1, (float) Math.cos(theta));
        multiplyValue(2, 1, (float) Math.sin(theta));
        multiplyValue(1, 2, (float) -Math.sin(theta));
        multiplyValue(2, 2, (float) Math.cos(theta));
    }

    public void rotateY(double theta) {
        multiplyValue(0, 0, (float) Math.cos(theta));
        multiplyValue(0, 2, (float) Math.sin(theta));
        multiplyValue(2, 0, (float) -Math.sin(theta));
        multiplyValue(2, 2, (float) Math.cos(theta));
    }

    public void rotateZ(double theta) {
        multiplyValue(0, 0, (float) Math.cos(theta));
        multiplyValue(1, 0, (float) Math.sin(theta));
        multiplyValue(0, 1, (float) -Math.sin(theta));
        multiplyValue(1, 1, (float) Math.cos(theta));
    }

    public void rotate(Vector3f vector, double theta) {
        if(vector.getX() == 1) {
            rotateX(theta);
        }
        if(vector.getY() == 1) {
            rotateX(theta);
        }
        if(vector.getZ() == 1) {
            rotateX(theta);
        }
    }

    public void rotate(Vector3f rotation) {
        if(rotation.getX() != 0) {
            rotateX((double) rotation.getX());
        }
        if(rotation.getY() != 0) {
            rotateY((double) rotation.getY());
        }
        if(rotation.getZ() != 0) {
            rotateZ((double) rotation.getZ());
        }
    }

    public void scale(float scale) {
        multiplyValue(0, 0, scale);
        multiplyValue(1, 1, scale);
        multiplyValue(2, 2, scale);
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        multiplyValue(0, 0, scaleX);
        multiplyValue(1, 1, scaleY);
        multiplyValue(2, 2, scaleZ);
    }

    public void scale(Vector3f vector) {
        scale(vector.getX(), vector.getY(), vector.getZ());
    }

    public float[] getValues() {
        return values;
    }

    public float getValue(int row, int col) throws NullPointerException {
        return values[row * 4 + col];
    }

    public void setValue(int row, int col, float value) throws NullPointerException {
        values[row * 4 + col] = value;
    }

    public void addValue(int row, int col, float value) throws NullPointerException {
        values[row * 4 + col] = values[row * 4 + col] + value;
    }

    public void multiplyValue(int row, int col, float value) throws NullPointerException {
        values[row * 4 + col] = values[row * 4 + col] * value;
    }

    public FloatBuffer toFloatBuffer() {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrixBuffer.put(values);
        matrixBuffer.flip();
        return matrixBuffer;
    }
}
