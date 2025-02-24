// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Colored icosahedron primitive
  */
public class ColorIcosahedron
{
  /** Coordinates values for the icosahedron */
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

  /** Dome radius */
  private float radius = 1.0f;

  /** Dome indices to successive vertices */
  private int[] triangles = new int[icoFaces.length];
  private float[] r = new float[icoFaces.length / 3];
  private float[] g = new float[icoFaces.length / 3];
  private float[] b = new float[icoFaces.length / 3];


  /** Constructs a geodesic dome with given parameters.
    * @param radius Dome radius
    */
  public ColorIcosahedron (float radius)
  {
    // Initialisation of the dome parameters
    this.radius = radius; 

    // Count of required edges
    int edgeCount = icoEdges.length / 2;

    // Builds the triangles array
    for (int i = 0; i < icoFaces.length / 3; i++)
    {
      if (icoFaces[i*3] < edgeCount)
      {
        triangles[i*3] = icoEdges[icoFaces[i*3]*2];
        triangles[i*3+1] = icoEdges[icoFaces[i*3]*2+1];
      }
      else
      {
        triangles[i*3] = icoEdges[(icoFaces[i*3]-edgeCount)*2+1];
        triangles[i*3+1] = icoEdges[(icoFaces[i*3]-edgeCount)*2];
      }
      if (icoFaces[i*3+1] < edgeCount)
        triangles[i*3+2] = icoEdges[icoFaces[i*3+1]*2+1];
      else
        triangles[i*3+2] = icoEdges[(icoFaces[i*3+1]-edgeCount)*2];
    } 

    // Faces color initialisation
    for (int i = 0; i < triangles.length / 3; i++)
    {
      r[i] = 0.2f + (float) (Math.random () * 0.7);
      g[i] = 0.2f + (float) (Math.random () * 0.7);
      b[i] = 0.2f + (float) (Math.random () * 0.7);
    }
  }

  /** Constructs a unit dome
    */
  public ColorIcosahedron ()
  {
    this (1.0f);
  }

  /** Renders the dome primitive.
    * @param gl GL2 context.
    */
  public void draw (GL2 gl)
  {
    gl.glBegin (GL2.GL_TRIANGLES);
      for (int i = 0; i < triangles.length; i++)
      {
        if (i % 3 == 0) gl.glColor3f (r[i/3], g[i/3], b[i/3]);
        gl.glVertex3f (icoVertices[triangles[i]][0] * radius,
                       icoVertices[triangles[i]][1] * radius,
                       icoVertices[triangles[i]][2] * radius);
      }
    gl.glEnd ();
  }
}
