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
import util.ObjectLoader;
import util.PDBReader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

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

        RawModel model = ObjectLoader.loadObjectModel("ball", loader);

        Camera camera = new Camera(0, 0, 0);
        PDBReader pdbReader = new PDBReader();
        String pdbLine = pdbReader.readFromPDBFile("src/main/resources/testPdb.pdb");

        ArrayList<Vector3f>[] result = pdbReader.parseAtoms(pdbLine);
        ArrayList<Entity> entities = new ArrayList<>();
        Vector3f avgPositionOfProtein = new Vector3f(0, 0, 0);

        for (int i = 0; i < result[0].size(); i++) {
            entities.add(new Entity(model, result[0].get(i),new Vector3f(0, 0, 0), 0.5f, result[1].get(i)));
            avgPositionOfProtein.add(result[0].get(i));
        }
        avgPositionOfProtein.set(-avgPositionOfProtein.x / entities.size(), -avgPositionOfProtein.y / entities.size(), -avgPositionOfProtein.z / entities.size());
        System.out.println("X: " + avgPositionOfProtein.x + ", Y: " + avgPositionOfProtein.y + ", Z: " + avgPositionOfProtein.z);
//        camera.setTargetPosition(avgPositionOfProtein);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).addToPosition(avgPositionOfProtein);
        }

        while (!glfwWindowShouldClose(window)) {
            renderer.clear(); // clear previous screen
            camera.move();

            // rendering
            shader.start();
            shader.loadViewMatrix(camera);
            for (Entity entity : entities.toArray(new Entity[entities.size()])) {
                renderer.render(entity, shader);
            }
            shader.stop();

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
