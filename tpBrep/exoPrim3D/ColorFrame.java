// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even (UHP / IUT de Saint-Dié - Dpt Info)

import com.jogamp.opengl.GL2;


/** Coloured reference frame to help align the 3D scenes */
public class ColorFrame
{
  /** Default arrow body length */
  private final static float AXIS = 0.02f;
  /** Default arrow head radius */
  private final static float RADIUS = 0.08f;
  /** Default arrow head length */
  private final static float LENGTH = 0.8f;
  /** Default resolution for revolution steps */
  private final static int RESOL = 16;
  /** Default X axis color */
  private final static float[] X_COLOR = {0.3f, 0.6f, 0.8f};
  /** Default Y axis color */
  private final static float[] Y_COLOR = {0.3f, 0.6f, 0.8f};
  /** Default Z axis color */
  private final static float[] Z_COLOR = {0.7f, 0.5f, 0.3f};

  /** Size of the reference frame. */
  private float size = 1.0f;
  /** Arrow body length */
  private float axis = AXIS;
  /** Arrow head radius */
  private float radius = RADIUS;
  /** Arrow axis length */
  private float length = LENGTH;
  /** Revolution steps resolution */
  private int resol = RESOL;
  /** X axis color */
  private final static float[] xCol = {X_COLOR[0], X_COLOR[1], X_COLOR[2]};
  /** Y axis color */
  private final static float[] yCol = {Y_COLOR[0], Y_COLOR[1], Y_COLOR[2]};
  /** Z axis color */
  private final static float[] zCol = {Z_COLOR[0], Z_COLOR[1], Z_COLOR[2]};



  /** Creates a reference frame with given size. */
  public ColorFrame (float size)
  {
    this.size = size;
    axis = AXIS * size;
    radius = RADIUS * size;
    length = LENGTH * size;
  }

  /** Sets one axis color. */
  public void setColor (int axis, float r, float g, float b)
  {
    switch (axis)
    {
      case 0 :
        xCol[0] = r;
        xCol[1] = g;
        xCol[2] = b;
        break;
      case 1 :
        yCol[0] = r;
        yCol[1] = g;
        yCol[2] = b;
        break;
      case 2 :
        zCol[0] = r;
        zCol[1] = g;
        zCol[2] = b;
        break;
    }
  }

  /** Sets one axis color. */
  public void setColor (char axis, float r, float g, float b)
  {
    switch (axis)
    {
      case 'x' :
      case 'X' :
        xCol[0] = r;
        xCol[1] = g;
        xCol[2] = b;
        break;
      case 'y' :
      case 'Y' :
        yCol[0] = r;
        yCol[1] = g;
        yCol[2] = b;
        break;
      case 'z' :
      case 'Z' :
        zCol[0] = r;
        zCol[1] = g;
        zCol[2] = b;
        break;
    }
  }

  /** Sets the revolution steps resolution */
  public void setResolution (int value)
  {
    resol = value;
  }

  /** Draws the reference frame */
  public void draw (GL2 gl)
  {
    // Y axis
    gl.glColor3f (yCol[0], yCol[1], yCol[2]);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        float ca = (float) Math.cos (a);
        float sa = (float) Math.sin (a);
        gl.glVertex3f (axis * ca, 0.0f, axis * sa);
        gl.glVertex3f (axis * ca, length, axis * sa);
      }
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, length, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = resol - 1; i > 0; i --)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3d (0.0f, size, 0.0f);
      gl.glVertex3d (radius, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();

    // X axis
    gl.glColor3f (xCol[0], xCol[1], xCol[2]);
    gl.glPushMatrix ();
    gl.glRotatef (- 90.0f, 0.0f, 0.0f, 1.0f);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        float ca = (float) Math.cos (a);
        float sa = (float) Math.sin (a);
        gl.glVertex3f (axis * ca, 0.0f, axis * sa);
        gl.glVertex3f (axis * ca, length, axis * sa);
      }
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, length, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = resol - 1; i > 0; i --)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, size, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glPopMatrix ();
 
    // Z axis
    gl.glColor3f (zCol[0], zCol[1], zCol[2]);
    gl.glPushMatrix ();
    gl.glRotatef (90.0f, 1.0f, 0.0f, 0.0f);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        float ca = (float) Math.cos (a);
        float sa = (float) Math.sin (a);
        gl.glVertex3f (axis * ca, 0.0f, axis * sa);
        gl.glVertex3f (axis * ca, length, axis * sa);
      }
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, length, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = resol - 1; i > 0; i --)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, size, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glPopMatrix ();
  }
}
