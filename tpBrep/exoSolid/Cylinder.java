// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Cylinder shape */
public class Cylinder extends ComplientShape
{
  /** Radius */
  private float radius = DEFAULT_VALUE;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;
  /** Circular resolution. */
  private int res = DEFAULT_RESOLUTION;


  /** Creates a cylinder with given parameters.
    * @param diameter Cylinder diameter
    * @param height Cylinder height
    * @param resolution Circular resolution
    */ 
  public Cylinder (float diameter, float height, int resolution)
  {
    radius = diameter / 2; 
    height_2 = height / 2; 
    setResolution (resolution);
  }

  /** Creates a cylinder with given parameters.
    * @param diameter Cylinder diameter
    * @param height Cylinder height
    */ 
  public Cylinder (float diameter, float height)
  {
    this (diameter, height, DEFAULT_RESOLUTION);
  }

  /** Creates a unit cylinder
    */ 
  public Cylinder ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE);
  }

  /** Renders the cylinder shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
  }

  /** Sets the cylinder reference system.
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

  /** Sets a new circular resolution for the cylinder.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    res = resolution;
  }
}
