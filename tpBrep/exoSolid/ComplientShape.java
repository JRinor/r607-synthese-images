// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Abstract class for complient (modeler-friendly) shapes.
 * Complient shapes allow the positionning of reference frame well suited
 *   to custom interaction tasks.
 */
public abstract class ComplientShape
{
  /** Reference system at the shape center. */
  public final static int REF_CENTER = 0;
  /** Reference system at the shape base center. */
  public final static int REF_BASE = 1;
  /** Reference system at the shape top center. */
  public final static int REF_TOP = 2;
  /** Reference system a shape corner. */
  public final static int REF_CORNER = 3;

  /** Default parameter values. */
  protected final static float DEFAULT_VALUE = 1.0f;
  /** Default circular resolutions. */
  protected final static int DEFAULT_RESOLUTION = 16;

  /** Shape reference wrt user reference. */
  protected float[] refMatrix = {1.0f, 0.0f, 0.0f, 0.0f,
                                 0.0f, 1.0f, 0.0f, 0.0f,
                                 0.0f, 0.0f, 1.0f, 0.0f,
                                 0.0f, 0.0f, 0.0f, 1.0f};


  /** Renders the shape centered.
   * @param gl GL2 context. 
   */
  protected abstract void drawShape (GL2 gl);

  /** Renders the box wrt current reference.
   * @param gl GL2 context. 
   */
  public void draw (GL2 gl)
  {
    gl.glPushMatrix ();
      gl.glMultMatrixf (refMatrix, 0);
      drawShape (gl);
    gl.glPopMatrix ();
  }

  /** Sets the shape user reference.
   * @param ref Reference system position wrt the shape.
   */
  public void setReference (int ref)
  {
  }

  /** Sets a new circular resolution for the shape.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
  }
}
