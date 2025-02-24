// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Geodesic dome shape */
public class Dome extends ComplientShape
{
  /** Dome radius */
  private float radius = DEFAULT_VALUE;
  /** Dome iterations. */
  private int div = DEFAULT_DIV;

  /** Default number of subdivisions */
  private final static int DEFAULT_DIV = 2;

  /** Coordinates values for the initial icosahedron */
  private final static float VALX = 0.525731112119133606f;
  private final static float VALZ = 0.850650808352039932f;

  /** Icosahedron vertices */
  private final static float[][] icoVertices = {{-VALX, 0.0f, VALZ},
                                                {VALX, 0.0f, VALZ},
                                                {-VALX, 0.0f, -VALZ},
                                                {VALX, 0.0f, -VALZ},
                                                {0.0f, VALZ, VALX},
                                                {0.0f, VALZ, -VALX},
                                                {0.0f, -VALZ, VALX},
                                                {0.0f, -VALZ, -VALX},
                                                {VALZ, VALX, 0.0f},
                                                {-VALZ, VALX, 0.0f},
                                                {VALZ, -VALX, 0.0f},
                                                {-VALZ, -VALX, 0.0f}};

  /** Icosahedron edges */
  private final static int[] icoEdges = {
            0, 1, 0, 4, 0, 6, 0, 9, 0, 11,
            1, 4, 1, 6, 1, 8, 1, 10, 2, 3,
            2, 5, 2, 7, 2, 9, 2, 11, 3, 5,
            3, 7, 3, 8, 3, 10, 4, 5, 4, 8,
            4, 9, 5, 8, 5, 9, 6, 7, 6, 10,
            6, 11, 7, 10, 7, 11, 8, 10, 9, 11};

  /** Icosahedron indices */
  private final static int[] icoFaces = {
            5, 31, 0, 20, 33, 1, 18, 22, 50, 51, 48, 19,
            7, 49, 35, 8, 58, 37, 47, 16, 28, 46, 14, 21,
            39, 10, 44, 15, 41, 9, 17, 56, 45, 54, 23, 26,
            25, 57, 53, 32, 4, 55, 36, 30, 2, 38, 6, 24,
            34, 3, 29, 13, 59, 42, 40, 12, 52, 43, 11, 27};


  /** Creates a dome with given parameters.
    * @param diameter Dome diameter
    * @param iter Number of subdivisions.
    */ 
  public Dome (float diameter, int subdivisions)
  {
    radius = diameter / 2; 
    setResolution (subdivisions);
  }

  /** Creates a dome with given diameter.
    * @param diameter Dome diameter
    */ 
  public Dome (float diameter)
  {
    this (diameter, DEFAULT_DIV);
  }

  /** Creates a unit dome
    */ 
  public Dome ()
  {
    this (DEFAULT_VALUE, DEFAULT_DIV);
  }

  /** Renders the dome shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
  }

  /** Sets the dome reference system.
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

  /** Sets a new resolution for the dome.
   * @param resolution Four times the number of subdivisions.
   */
  public void setResolution (int resolution)
  {
    div = resolution / 4;
  }
}
