package display;


import entities.Entity;
import input.KeyboardHandler;
import math.Vector3f;
import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import shaders.StaticShader;
import util.Loader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Screen {

    private long window;
    public static int WIDTH, HEIGHT;

    public void run() {

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(800, 600, "LWJGL Engine", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW Window");
        }

        glfwSetKeyCallback(window, new KeyboardHandler());

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            WIDTH = pWidth.get(0);
            HEIGHT = pHeight.get(0);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window,
                    (vidMode.width() - pWidth.get(0))/2,
                    (vidMode.height() - pHeight.get(0))/2);
        }

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f
        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
        };

//        float[] vertices = {
//                -0.5f, 0.5f, 0,
//                -0.5f, -0.5f, 0,
//                0.5f, -0.5f, 0,
//                0.5f, 0.5f, 0
//        };

//        int[] indices = {
//                0, 1, 3,
//                3, 1, 2
//        };

        RawModel model = loader.loadModel(vertices, indices);

        Entity entity = new Entity(model, new Vector3f(0, 0f, -5f), new Vector3f(0, 0, 0), 1);

        Camera camera = new Camera();

        while (!glfwWindowShouldClose(window)) {
            renderer.clear(); // clear previous screen

            camera.move();

            // rendering
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();

            // logic
//            entity.addToRotation(new Vector3f(0f, 0f, 0.025f));
//            entity.addToPosition(new Vector3f(0f, 0.1f, 0f));

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        shader.clean();
        loader.clean();
    }

    public static void main(String args[]) {
        new Screen().run();
    }
}
