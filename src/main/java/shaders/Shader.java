package shaders;

import math.Matrix4f;
import math.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class Shader {

    private int id;
    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader(String vertexFile, String fragmentFile) {
        vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        id = GL20.glCreateProgram();
        GL20.glAttachShader(id, vertexShaderId);
        GL20.glAttachShader(id, fragmentShaderId);
        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);
        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(id, uniformName);
    }

    protected void loadUniformFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected  void loadUniformVector3f(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
    }

    protected void loadUniformBoolean(int location, boolean value) {
        float booleanFloat = 0;
        if(value) {
            booleanFloat = 1;
        }
        GL20.glUniform1f(location, booleanFloat);
    }

    protected void loadUniformMatrix4f(int location, Matrix4f matrix) {
        GL20.glUniformMatrix4fv(location, false, matrix.toFloatBuffer());
    }

    public void start() {
        GL20.glUseProgram(id);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void clean() {
        stop();
        GL20.glDetachShader(id, vertexShaderId);
        GL20.glDetachShader(id, fragmentShaderId);
        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(id);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(id, attribute, variableName);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch( IOException e ) {
            System.err.println("Could not read file!");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }

        return shaderId;
    }
}
