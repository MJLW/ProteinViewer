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
        float[] identity = {1f, 0, 0, 0, // column 1
                            0, 1f, 0, 0, // column 2
                            0, 0, 1f, 0, // column 3
                            0, 0, 0, 1f}; // column 4
        return identity;
    }

    public void translate(float x, float y, float z) {
        Matrix4f translateMatrix = new Matrix4f(getIdentityMatrix());
        translateMatrix.setValue(3, 0, x);
        translateMatrix.setValue(3, 1, y);
        translateMatrix.setValue(3, 2, z);
        multiplyByMatrix(translateMatrix);
    }

    public void translate(Vector3f vector) {
        translate(vector.getX(), vector.getY(), vector.getZ());
    }

    private void rotateX(double theta) {
        Matrix4f rotateMatrix = new Matrix4f(getIdentityMatrix());
        rotateMatrix.setValue(1, 1, (float) Math.cos(theta));
        rotateMatrix.setValue(2, 1, (float) Math.sin(theta));
        rotateMatrix.setValue(1, 2, (float) -Math.sin(theta));
        rotateMatrix.setValue(2, 2, (float) Math.cos(theta));
        multiplyByMatrix(rotateMatrix);
    }

    private void rotateY(double theta) {
        Matrix4f rotateMatrix = new Matrix4f(getIdentityMatrix());
        rotateMatrix.setValue(0, 0, (float) Math.cos(theta));
        rotateMatrix.setValue(0, 2, (float) Math.sin(theta));
        rotateMatrix.setValue(2, 0, (float) -Math.sin(theta));
        rotateMatrix.setValue(2, 2, (float) Math.cos(theta));
        multiplyByMatrix(rotateMatrix);
    }

    private void rotateZ(double theta) {
        Matrix4f rotateMatrix = new Matrix4f(getIdentityMatrix());
        rotateMatrix.setValue(0, 0, (float) Math.cos(theta));
        rotateMatrix.setValue(0, 1, (float) -Math.sin(theta));
        rotateMatrix.setValue(1, 0, (float) Math.sin(theta));
        rotateMatrix.setValue(1, 1, (float) Math.cos(theta));
        multiplyByMatrix(rotateMatrix);
    }

    public void rotateAll(Vector3f rotations) {
        setValue(0, 1, (float) -Math.sin(rotations.z));
        setValue(1, 0, (float) Math.sin(rotations.z));

        setValue(0, 0, (float) Math.cos(rotations.z + rotations.y));
        setValue(0, 2, (float) Math.sin(rotations.y));
        setValue(2, 0, (float) -Math.sin(rotations.y));

        setValue(1, 1, (float) Math.cos(rotations.z + rotations.x));
        setValue(2, 1, (float) Math.sin(rotations.x));
        setValue(1, 2, (float) -Math.sin(rotations.x));
        setValue(2, 2, (float) Math.cos(rotations.y + rotations.x));
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
        return values[col * 4 + row];
    }

    public void setValue(int row, int col, float value) throws NullPointerException {
        values[col * 4 + row] = value;
    }

    public void addValue(int row, int col, float value) throws NullPointerException {
        setValue(row, col, getValue(row, col) + value);
    }

    public void multiplyValue(int row, int col, float value) throws NullPointerException {
        setValue(row, col, getValue(row, col) * value);
    }

    /**
     * Multiplies the parameter matrix with the targeted matrix object and sets the targeted object
     * equal to the result. Eq: this = matrix * this
     * @param matrix the parameter matrix, left hand
     */
    public void multiplyByMatrix(Matrix4f matrix) {
        Matrix4f result = new Matrix4f();
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
                result.setValue(rowIndex, columnIndex, 0);
                for (int multiplicationIndex = 0; multiplicationIndex < 4; multiplicationIndex++) {
                    result.addValue(rowIndex, columnIndex, this.getValue(rowIndex, multiplicationIndex) * matrix.getValue(columnIndex, multiplicationIndex));
                }
            }
        }
        this.set(result.getValues());
    }
    public FloatBuffer toFloatBuffer() {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrixBuffer.put(values);
        matrixBuffer.flip();
        return matrixBuffer;
    }
}
