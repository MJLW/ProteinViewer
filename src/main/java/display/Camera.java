package display;

import math.Vector3f;
import input.KeyboardHandler;

import java.awt.event.KeyEvent;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f targetPosition = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {

    }

    public Camera(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Camera(Vector3f position) {
        this.position = position;
    }

    public void move() {
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_W)) {
            if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                System.out.println("Pressed W");
                yaw -= 1.5f;
            } else {
                position.y += 0.3f;
            }
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_S)) {
            if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                System.out.println("Pressed S");
                yaw += 1.5f;
            } else {
                position.y -= 0.3f;
            }
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_A)) {
            if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                pitch += -1.5f;
            } else {
                position.x -= 0.3f;
            }
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_D)) {
            if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                pitch -= -1.5f;
            } else {
                position.x += 0.3f;
            }
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_Z)) {
            if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                roll -= -1.5f;
            } else {
                position.z -= 0.3f;
            }
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_X)) {
            if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                roll += -1.5f;
            } else {
                position.z += 0.3f;
            }
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector3f targetPosition) {
        this.targetPosition = targetPosition;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public Vector3f getNegativePosition() {
        return new Vector3f(-position.x, -position.y, -position.z);
    }
}
