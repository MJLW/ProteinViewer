package display;

import math.Vector3f;
import input.KeyboardHandler;

import java.awt.event.KeyEvent;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {

    }

    public void move() {
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_W)) {
            position.y -= 0.1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_S)) {
            position.y += 0.1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_A)) {
            position.x += 0.1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_D)) {
            position.x -= 0.1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_Z)) {
            position.z -= 0.1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_X)) {
            position.z += 0.1f;
        }

        if (KeyboardHandler.isKeyDown(KeyEvent.VK_I)) {
            pitch += 1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_K)) {
            pitch -= 1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_J)) {
            yaw -= 1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_L)) {
            yaw += 1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_N)) {
            roll -= 1f;
        }
        if (KeyboardHandler.isKeyDown(KeyEvent.VK_M)) {
            roll += 1f;
        }
    }

    public Vector3f getPosition() {
        return position;
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
