package entities;

import math.Vector3f;
import models.RawModel;

public class Entity {

    private RawModel model;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public Entity(RawModel model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void addToPosition(Vector3f positionAddition) {
        this.position.add(positionAddition);
    }

    public void addToRotation(Vector3f rotationAddition) {
        this.rotation.add(rotationAddition);
    }

    public RawModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
}
