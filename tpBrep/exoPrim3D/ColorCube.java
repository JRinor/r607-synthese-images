import com.jogamp.opengl.GL2;

/** Colored cube primitive */
public class ColorCube extends ColorShape {
  /** Renders the box primitive.
    * @param gl GL2 context. 
    */ 
  public void draw(GL2 gl) {
    gl.glDisable(GL2.GL_CULL_FACE);
    gl.glBegin(GL2.GL_QUADS);

    gl.glColor3f(0.8f, 0.3f, 0.1f);
    gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glVertex3f(0.5f, 0.5f, -0.5f);
    gl.glVertex3f(0.5f, -0.5f, -0.5f);

    gl.glColor3f(0.6f, 0.3f, 0.6f);
    gl.glVertex3f(-0.5f, -0.5f, 0.5f);
    gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glVertex3f(-0.5f, 0.5f, 0.5f);

    gl.glColor3f(0.1f, 0.3f, 0.8f);
    gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glVertex3f(0.5f, -0.5f, -0.5f);
    gl.glVertex3f(0.5f, -0.5f, 0.5f);
    gl.glVertex3f(-0.5f, -0.5f, 0.5f);

    gl.glColor3f(0.3f, 0.8f, 0.1f);
    gl.glVertex3f(0.5f, 0.5f, 0.5f);
    gl.glVertex3f(-0.5f, 0.5f, 0.5f);
    gl.glVertex3f(-0.5f, -0.5f, 0.5f);
    gl.glVertex3f(0.5f, -0.5f, 0.5f);

    gl.glColor3f(0.3f, 0.6f, 0.6f);
    gl.glVertex3f(0.5f, -0.5f, 0.5f);
    gl.glVertex3f(0.5f, 0.5f, 0.5f);
    gl.glVertex3f(0.5f, 0.5f, -0.5f);
    gl.glVertex3f(0.5f, -0.5f, -0.5f);

    gl.glColor3f(0.3f, 0.1f, 0.8f);
    gl.glVertex3f(0.5f, 0.5f, 0.5f);
    gl.glVertex3f(0.5f, 0.5f, -0.5f);
    gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glVertex3f(-0.5f, 0.5f, 0.5f);

    gl.glEnd();
  }
}
