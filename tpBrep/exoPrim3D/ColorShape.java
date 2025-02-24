// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;


/** Colored primitive */
public abstract class ColorShape
{
  /** Renders the shape.
    * @param gl GL2 context. 
    */ 
  public abstract void draw (GL2 gl);
}
