package math;

public class Vector3f {

    public float x, y, z;

    public Vector3f() {
        set(0, 0, 0);
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3f(Vector3f vector) {
        set(vector);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3f vector) {
        set(vector.getX(), vector.getY(), vector.getZ());
    }

    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Vector3f vector) {
        add(vector.getX(), vector.getY(), vector.getZ());
    }

    public void sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(Vector3f vector) {
        sub(vector.getX(), vector.getY(), vector.getZ());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {

        return z;
    }
}
