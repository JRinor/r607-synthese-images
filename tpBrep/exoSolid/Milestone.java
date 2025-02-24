// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Milestone shape */
public class Milestone extends ComplientShape
{
  /** Half depth (Y) */
  private float depth_2 = DEFAULT_VALUE;
  /** Half width (X) */
  private float width_2 = DEFAULT_VALUE;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;
  /** Circular resolution. */
  private int res = DEFAULT_RESOLUTION;


  /** Creates a milestone with given parameters.
    * @param depth Milestone depth
    * @param width Milestone width
    * @param height Milestone height
    * @param resolution Circular resolution
    */ 
  public Milestone (float depth, float width, float height, int resolution)
  {
    depth_2 = depth / 2; 
    width_2 = width / 2; 
    height_2 = height / 2; 
    setResolution (resolution);
  }

  /** Creates a milestone with given parameters.
    * @param depth Milestone depth
    * @param width Milestone width
    * @param height Milestone height
    */ 
  public Milestone (float depth, float width, float height)
  {
    this (depth, width, height, DEFAULT_RESOLUTION);
  }

  /** Creates a unit milestone
    */ 
  public Milestone ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
  }

  /** Renders the milestone shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
  }

  /** Sets the milestone reference system.
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

  /** Sets a new circular resolution for the milestone.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    res = resolution;
  }
}
