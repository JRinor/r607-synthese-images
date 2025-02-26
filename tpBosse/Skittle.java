// Trame du TP de modelisation 3D : embossage
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Skittle solid primitive */
public class Skittle
{
  /** Radius */
  private float radius = 1.0f;
  /** Height of the base part */
  private float height = 1.0f;
  /** Stripe width */
  private float sw = height / 6;
  /** Count of stripes */
  private int ns = 3;
  /** Chamfer type */
  private int ct = 1;
  /** Chamfer size */
  private float cs = 0.2f;
  /** Circular resolution of the skittle base */
  private int bres = DEFAULT_CIRCULAR_RESOLUTION;
  /** Circular resolution of the skittle top */
  private int tres = DEFAULT_CIRCULAR_RESOLUTION;
  /** Default circular resolution */
  private final static int DEFAULT_CIRCULAR_RESOLUTION = 15;


  /**
   * Constructs a default skittle.
   */
  public Skittle ()
  {
  }

  /**
   * Constructs a simple skittle with specific radius and height.
   * @param width Skittle radius
   * @param height Skittle base height
   */
  public Skittle (float radius, float height)
  {
    this.radius = radius;
    this.height = height;
  }

  /** Constructs a nice skittle.
   * @param width Skittle radius
   * @param height Skittle base height
   * @param stripeCount Count of stripes
   * @param stripeWidth Width of the stripes
   * @param chamferType Type of chamfer
   * @param chamferSize Width of the chamfer
   * @param baseResolution Circular resolution of the base
   * @param topResolution Circular resolution of the top
   */
  public Skittle (float radius, float height,
                  int stripeCount, float stripeWidth,
                  int chamferType, float chamferSize,
                  int baseResolution, int topResolution)
  {
    this.radius = radius;
    this.height = height;
    ns = stripeCount;
    sw = stripeWidth;
    ct = chamferType;
    cs = chamferSize;
    bres = baseResolution;
    tres = topResolution;
  }

  /** Increments or decrements the skittle stripe width.
   * Returns the new stripe width.
   * @param larger increments if true, decrements otherwise. 
   */
  public float moveStripeWidth (boolean larger)
  {
    if (larger)
    {
      sw += height / 100;
      if (sw > height / 6) sw = height / 6;
    }
    else
    {
      sw -= height / 100;
      if (sw < height / 100) sw = height / 100;
    }
    return sw;
  }

  /** Sets the count of skittle stripes.
   * Returns the new count of stripes.
   * @param n new count of skittle stripes. 
   */
  public int setCountOfStripes (int n)
  {
    ns = n;
    if (ns > 3) ns = 1;
    if (ns < 1) ns = 3;
    return ns;
  }

  /** Sets the skittle chamfer type.
   * Returns the new chamfer type.
   * @param type new chamfer type. 
   */
  public int setChamferType (int type)
  {
    ct = type;
    if (ct > 2) ct = 0;
    if (ct < 0) ct = 2;
    return ct;
  }

  /** Increments or decrements the chamfer size.
   * Returns the new chamfer size.
   * @param larger increments if true, decrements otherwise. 
   */
  public float moveChamferSize (boolean larger)
  {
    if (larger)
    {
      cs += radius / 100;
      if (cs > radius * 0.99f) cs = radius * 0.99f;
    }
    else
    {
      cs -= height / 100;
      if (cs < radius / 100) cs = radius / 100;
    }
    return cs;
  }

  /** Renders the skittle.
   * @param gl GL2 context. 
   */
  public void draw (GL2 gl)
  {
    drawLowerBase (gl);
    drawUpperBase (gl);
    drawTop (gl);
  }

  /** Renders the skittle base.
   * @param gl GL2 context.
   */
  public void drawBase (GL2 gl)
  {
    drawLowerBase (gl);
    drawUpperBase (gl);
  }

  /** Renders the upper part of the skittle base.
   * @param gl GL2 context.
   */ 
  public void drawUpperBase (GL2 gl)
  {
    float n[][] = new float[bres][2];
    for (int i = 0; i < bres; i++)
    {
      double angle = 2 * i * Math.PI / bres;
      n[i][0] = (float) Math.cos (angle);
      n[i][1] = (float) Math.sin (angle);
    }

    // Top face 
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f (0.0f, 0.0f, 1.0f);
      gl.glVertex3f (0.0f, 0.0f, height);
      for (int j = 0; j < bres; j++)
        gl.glVertex3f (radius * n[j][0], radius * n[j][1], height);
      gl.glVertex3f (radius * n[0][0], radius * n[0][1], height);
    gl.glEnd ();

    // Up cylinder face
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      for (int j = 0; j < bres; j++)
      {
        gl.glNormal3f (n[j][0], n[j][1], 0.0f);
        gl.glVertex3f (radius * n[j][0], radius * n[j][1], height);
        gl.glVertex3f (radius * n[j][0], radius * n[j][1], height / 2);
      }
      gl.glNormal3f (n[0][0], n[0][1], 0.0f);
      gl.glVertex3f (radius * n[0][0], radius * n[0][1], height);
      gl.glVertex3f (radius * n[0][0], radius * n[0][1], height / 2);
    gl.glEnd ();
  }

  /** Creates horizontal crevasses on the lower part of the skittle */
  private void createCrevasses(GL2 gl, float[][] n) {
    for (int i = 0; i < ns; i++) {
      float angleOffset = (float) (2 * Math.PI * i / ns);
      for (int j = 0; j < bres; j++) {
        float angle = (float) (2 * Math.PI * j / bres) + angleOffset;
        float nx = (float) Math.cos(angle);
        float ny = (float) Math.sin(angle);
        float nz = (j % 2 == 0) ? 0.1f : -0.1f; // Adjust normals for crevasse effect

        gl.glNormal3f(nx, ny, nz);
        gl.glVertex3f(radius * nx, radius * ny, height / 2);
        gl.glVertex3f(radius * nx, radius * ny, 0.0f);
      }
    }
  }

  /** Adjusts the normals of the vertices of all cylinders in the lower base */
  private void adjustNormals(GL2 gl, float[][] n, int k) {
    float z1 = k * height / 8;
    float z2 = (k + 1) * height / 8;

    gl.glBegin(GL2.GL_TRIANGLE_STRIP);
    for (int j = 0; j < bres; j++) {
      float nx = n[j][0];
      float ny = n[j][1];
      float nz = (k % 2 == 0) ? 0.1f : -0.1f; // Adjust normals for crevasse effect

      gl.glNormal3f(nx, ny, nz);
      gl.glVertex3f(radius * nx, radius * ny, z2);
      gl.glVertex3f(radius * nx, radius * ny, z1);
    }
    gl.glEnd();
  }

  /** Renders the lower part of the skittle base.
   * @param gl GL2 context.
   */ 
  @Override
  public void drawLowerBase (GL2 gl)
  {
    float n[][] = new float[bres][2];
    for (int i = 0; i < bres; i++)
    {
      double angle = 2 * i * Math.PI / bres;
      n[i][0] = (float) Math.cos(angle);
      n[i][1] = (float) Math.sin(angle);
    }

    // Divide the base into multiple horizontal sections and adjust normals
    for (int k = 0; k < 4; k++) {
      adjustNormals(gl, n, k);
    }

    // Create crevasses
    createCrevasses(gl, n);

    // Bottom face
    gl.glBegin(GL2.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 0.0f, -1.0f);
    gl.glVertex3f(0.0f, 0.0f, 0.0f);
    gl.glVertex3f(radius * n[0][0], radius * n[0][1], 0.0f);
    for (int j = bres - 1; j >= 0; j--) {
      gl.glVertex3f(radius * n[j][0], radius * n[j][1], 0.0f);
    }
    gl.glEnd();
  }

  /** Renders the skittle top.
   * @param gl GL2 context.
   */ 
  public void drawTop (GL2 gl)
  {
    float n[][][] = new float[tres][4 * tres][3];
    for (int i = 0; i < tres; i++)
    {
      double alpha = i * Math.PI / (2 * tres);
      float ca = (float) Math.cos (alpha);
      float sa = (float) Math.sin (alpha);
      for (int j = 0; j < 4 * tres; j++)
      {
        double beta = j * Math.PI / (2 * tres);
        float cb = (float) Math.cos (beta);
        float sb = (float) Math.sin (beta);
        n[i][j][0] = ca * cb;
        n[i][j][1] = ca * sb;
        n[i][j][2] = sa;
      }
    }

    // North pole
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f (0.0f, 0.0f, 1.0f);
      gl.glVertex3f (0.0f, 0.0f, height + radius * 2.0f);
      for (int j = 0; j < 4 * tres; j++)
      {
        gl.glNormal3f (n[tres-1][j][0], n[tres-1][j][1], n[tres-1][j][2]);
        gl.glVertex3f (radius * n[tres-1][j][0], radius * n[tres-1][j][1],
                       height + radius * (1.0f + n[tres-1][j][2]));
      }
      gl.glNormal3f (n[tres-1][0][0], n[tres-1][0][1], n[tres-1][0][2]);
      gl.glVertex3f (radius * n[tres-1][0][0], radius * n[tres-1][0][1],
                     height + radius * (1.0f + n[tres-1][0][2]));
    gl.glEnd ();

    // North hemisphere
    for (int i = tres - 1; i > 0; i--)
    {
      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        for (int j = 0; j < 4 * tres; j++)
        {
          gl.glNormal3f (n[i][j][0], n[i][j][1], n[i][j][2]);
          gl.glVertex3f (radius * n[i][j][0], radius * n[i][j][1],
                         height + radius * (1.0f + n[i][j][2]));
          gl.glNormal3f (n[i-1][j][0], n[i-1][j][1], n[i-1][j][2]);
          gl.glVertex3f (radius * n[i-1][j][0], radius * n[i-1][j][1],
                         height + radius * (1.0f + n[i-1][j][2]));
        }
        gl.glNormal3f (n[i][0][0], n[i][0][1], n[i][0][2]);
        gl.glVertex3f (radius * n[i][0][0], radius * n[i][0][1],
                       height + radius * (1.0f + n[i][0][2]));
        gl.glNormal3f (n[i-1][0][0], n[i-1][0][1], n[i-1][0][2]);
        gl.glVertex3f (radius * n[i-1][0][0], radius * n[i-1][0][1],
                       height + radius * (1.0f + n[i-1][0][2]));
      gl.glEnd ();
    }

    // South hemisphere
    for (int i = 0; i < tres - 1; i++)
    {
      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        for (int j = 0; j < 4 * tres; j++)
        {
          gl.glNormal3f (n[i][j][0], n[i][j][1], - n[i][j][2]);
          gl.glVertex3f (radius * n[i][j][0], radius * n[i][j][1],
                         height + radius * (1.0f - n[i][j][2]));
          gl.glNormal3f (n[i+1][j][0], n[i+1][j][1], - n[i+1][j][2]);
          gl.glVertex3f (radius * n[i+1][j][0], radius * n[i+1][j][1],
                         height + radius * (1.0f - n[i+1][j][2]));
        }
        gl.glNormal3f (n[i][0][0], n[i][0][1], - n[i][0][2]);
        gl.glVertex3f (radius * n[i][0][0], radius * n[i][0][1],
                       height + radius * (1.0f - n[i][0][2]));
        gl.glNormal3f (n[i+1][0][0], n[i+1][0][1], - n[i+1][0][2]);
        gl.glVertex3f (radius * n[i+1][0][0], radius * n[i+1][0][1],
                       height + radius * (1.0f - n[i+1][0][2]));
      gl.glEnd ();
    }

    // South pole
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f (0.0f, 0.0f, - 1.0f);
      gl.glVertex3f (0.0f, 0.0f, height);
      gl.glNormal3f (n[tres-1][0][0], n[tres-1][0][1], - n[tres-1][0][2]);
      gl.glVertex3f (radius * n[tres-1][0][0], radius * n[tres-1][0][1],
                     height + radius * (1.0f - n[tres-1][0][2]));
      for (int j = 4 * tres - 1; j >= 0; j--)
      {
        gl.glNormal3f (n[tres-1][j][0], n[tres-1][j][1], - n[tres-1][j][2]);
        gl.glVertex3f (radius * n[tres-1][j][0], radius * n[tres-1][j][1],
                       height + radius * (1.0f - n[tres-1][j][2]));
      }
    gl.glEnd ();
  }
}
