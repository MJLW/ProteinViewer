package math;

import display.Camera;

public class ViewMatrix extends Matrix4f {

    public ViewMatrix(Camera camera) {
        super(getIdentityMatrix());
        translate(camera.getNegativePosition());
        rotate( new Vector3f( (float) Math.toRadians(camera.getYaw()),
                (float) Math.toRadians(camera.getPitch()),
                (float) Math.toRadians(camera.getRoll())));
    }
}
