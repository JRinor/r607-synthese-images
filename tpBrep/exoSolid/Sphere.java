// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Sphere shape */
public class Sphere extends ComplientShape
{
  /** Sphere radius */
  private float radius = DEFAULT_VALUE;
  /** Longitudinal resolution. */
  private int longr = DEFAULT_RESOLUTION * 2;
  /** Latitudinal resolution. */
  private int latr = DEFAULT_RESOLUTION;


  /** Creates a sphere with given parameters.
    * @param diameter Sphere diameter
    * @param longres Longitudinal resolution
    * @param latres Latitudinal resolution
    */ 
  public Sphere (float diameter, int longres, int latres)
  {
    radius = diameter / 2; 
    setResolution (longres, latres);
  }

  /** Creates a sphere with given diameter.
    * @param depth Sphere diameter
    */ 
  public Sphere (float diameter)
  {
    this (diameter, DEFAULT_RESOLUTION * 2, DEFAULT_RESOLUTION);
  }

  /** Creates a unit sphere
    */ 
  public Sphere ()
  {
    this (DEFAULT_VALUE, DEFAULT_RESOLUTION * 2, DEFAULT_RESOLUTION);
  }

  /** Renders the sphere shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
  }

  /** Sets the sphere reference system.
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

  /** Sets new circular resolutions for the sphere.
   * @param tres new longitudinal resolution value.
   * @param sres new latitudinal resolution value.
   */
  public void setResolution (int longres, int latres)
  {
    longr = longres;
    latr = latres;
  }

  /** Sets a new resolution for the sphere.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    setResolution (resolution * 2, resolution);
  }
}
