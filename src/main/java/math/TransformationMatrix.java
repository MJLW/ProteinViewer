package math;

public class TransformationMatrix extends Matrix4f {

    public TransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
        super(getIdentityMatrix());
        translate(translation);
        rotate(rotation);
        scale(scale);
    }

    public TransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        super(getIdentityMatrix());
        translate(translation);
        rotate(rotation);
        scale(scale);
    }
}
