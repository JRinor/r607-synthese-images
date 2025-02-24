// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Box shape */
public class Box extends ComplientShape
{
  /** Half depth (X) */
  private float depth_2 = DEFAULT_VALUE;
  /** Half width (Y) */
  private float width_2 = DEFAULT_VALUE;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;


  /** Creates a box with given parameters.
    * @param depth Box depth
    * @param width Box width
    * @param height Box height
    */ 
  public Box (float depth, float width, float height)
  {
    depth_2 = depth / 2; 
    width_2 = width / 2; 
    height_2 = height / 2; 
  }

  /** Creates a unit box
    */ 
  public Box ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
  }

  /** Renders the box shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
    gl.glBegin (GL2.GL_QUADS);
      gl.glNormal3f (0.0f, 0.0f, -1.0f);
      gl.glVertex3f (-depth_2, -width_2, -height_2);
      gl.glVertex3f (-depth_2, width_2, -height_2);
      gl.glVertex3f (depth_2, width_2, -height_2);
      gl.glVertex3f (depth_2, -width_2, -height_2);

      gl.glNormal3f (-1.0f, 0.0f, 0.0f);
      gl.glVertex3f (-depth_2, -width_2, -height_2);
      gl.glVertex3f (-depth_2, -width_2, height_2);
      gl.glVertex3f (-depth_2, width_2, height_2);
      gl.glVertex3f (-depth_2, width_2, -height_2);

      gl.glNormal3f (0.0f, -1.0f, 0.0f);
      gl.glVertex3f (-depth_2, -width_2, -height_2);
      gl.glVertex3f (depth_2, -width_2, -height_2);
      gl.glVertex3f (depth_2, -width_2, height_2);
      gl.glVertex3f (-depth_2, -width_2, height_2);

      gl.glNormal3f (0.0f, 0.0f, 1.0f);
      gl.glVertex3f (depth_2, width_2, height_2);
      gl.glVertex3f (-depth_2, width_2, height_2);
      gl.glVertex3f (-depth_2, -width_2, height_2);
      gl.glVertex3f (depth_2, -width_2, height_2);

      gl.glNormal3f (1.0f, 0.0f, 0.0f);
      gl.glVertex3f (depth_2, width_2, height_2);
      gl.glVertex3f (depth_2, -width_2, height_2);
      gl.glVertex3f (depth_2, -width_2, -height_2);
      gl.glVertex3f (depth_2, width_2, -height_2);

      gl.glNormal3f (0.0f, 1.0f, 0.0f);
      gl.glVertex3f (depth_2, width_2, height_2);
      gl.glVertex3f (depth_2, width_2, -height_2);
      gl.glVertex3f (-depth_2, width_2, -height_2);
      gl.glVertex3f (-depth_2, width_2, height_2);
    gl.glEnd ();
  }

  /** Sets the box reference system.
    * @param ref Reference system position wrt the shape.
    */
  public void setReference (int ref)
  {
    switch (ref)
    {
      case REF_CENTER :
        refMatrix[12] = 0.0f;
        refMatrix[13] = 0.0f;
        refMatrix[14] = 0.0f;
        break;
      case REF_BASE :
        refMatrix[12] = 0.0f;
        refMatrix[13] = 0.0f;
        refMatrix[14] = height_2;
        break;
      case REF_TOP :
        refMatrix[12] = 0.0f;
        refMatrix[13] = 0.0f;
        refMatrix[14] = - height_2;
        break;
      case REF_CORNER :
        refMatrix[12] = depth_2;
        refMatrix[13] = width_2;
        refMatrix[14] = height_2;
        break;
      default :
        break;
    }
  }
}
