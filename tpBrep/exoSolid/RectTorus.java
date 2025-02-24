// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Rectangular section torus shape */
public class RectTorus extends ComplientShape
{
  /** External radius */
  private float radius = DEFAULT_VALUE;
  /** Internal radius */
  private float intRadius = DEFAULT_VALUE / 2;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;
  /** Circular resolution. */
  private int res = DEFAULT_RESOLUTION;


  /** Creates a rectangular section torus with given parameters.
    * @param diameter Torus diameter
    * @param width Torus width
    * @param height Torus height
    * @param resolution Circular resolution
    */ 
  public RectTorus (float diameter, float width, float height, int resolution)
  {
    radius = diameter / 2; 
    intRadius = radius - width; 
    height_2 = height / 2; 
    setResolution (resolution);
  }

  /** Creates a rectangular section torus with given parameters.
    * @param depth Torus diameter
    * @param width Torus width
    * @param height Torus height
    */ 
  public RectTorus (float diameter, float width, float height)
  {
    this (diameter, width, height, DEFAULT_RESOLUTION);
  }

  /** Creates a unit rectangular section torus
    */ 
  public RectTorus ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_RESOLUTION);
  }

  /** Renders the rectangular section torus shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
  }

  /** Sets the torus reference system.
    * @param ref Reference system position wrt the shape.
    */
  public void setReference (int ref)
  {
    switch (ref)
    {
      case REF_CENTER :
      case REF_BASE :
      case REF_TOP :
      case REF_CORNER :
        refMatrix[12] = 0.0f;
        refMatrix[13] = 0.0f;
        refMatrix[14] = 0.0f;
        break;
      default :
        break;
    }
  }

  /** Sets a new circular resolution for the torus.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    res = resolution;
  }
}
