// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Circular section torus shape */
public class CircTorus extends ComplientShape
{
  /** Torus radius */
  private float rext = DEFAULT_VALUE;
  /** Section radius */
  private float rint = DEFAULT_VALUE / 2;
  /** Torus circular resolution. */
  private int res = DEFAULT_RESOLUTION;
  /** Section circular resolution. */
  private int sres = DEFAULT_RESOLUTION;


  /** Creates a circular section torus with given parameters.
    * @param tdiam Torus diameter
    * @param sdiam Section diameter
    * @param tres Torus circular resolution
    * @param sres Section circular resolution
    */ 
  public CircTorus (float tdiam, float sdiam, int tres, int sres)
  {
    rext = tdiam / 2; 
    rint = sdiam / 2; 
    setResolution (tres, sres);
  }

  /** Creates a circular section torus with given parameters.
    * @param depth Torus diameter
    * @param width Torus width
    * @param height Torus height
    */ 
  public CircTorus (float tdiam, float sdiam)
  {
    this (tdiam, sdiam, DEFAULT_RESOLUTION, DEFAULT_RESOLUTION);
  }

  /** Creates a unit circular section torus
    */ 
  public CircTorus ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE / 2,
          DEFAULT_RESOLUTION, DEFAULT_RESOLUTION);
  }

  /** Renders the circular section torus shape centered.
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

  /** Sets new circular resolutions for the torus.
   * @param tres new resolution value for the torus rotation.
   * @param sres new resolution value for the torus section rotation.
   */
  public void setResolution (int tres, int sres)
  {
    res = tres;
    this.sres = sres;
  }

  /** Sets a new circular resolution for the torus.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    setResolution (resolution, resolution);
  }
}
