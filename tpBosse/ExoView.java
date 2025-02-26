// Trame du TP de modelisation 3D : embossage
// BUT Info - 2024/2025
// Preparateur: P. Even

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


/** GL2 context to render a movable 3D primitive */
public class ExoView implements GLEventListener
{
  /** Skittle radius */
  float sr = 0.2f;
  /** Skittle height */
  float sh = 1.0f;
  /** Count of stripes */
  int ns = 1;
  /** Stripe width */
  float sw = 1.0f / 6;
  /** Chamfer type */
  int ct = 1;
  /** Chamfer size */
  float cs = 0.05f;
  /** Base resolution */
  int br = 7;
  /** Top resolution */
  int tr = 9;
  /** Rendered 3D primitive */
  private Skittle skitty = new Skittle (sr, sh, ns, sw, ct, cs, br, tr);

  /** Reference frame */
  private ColorFrame ref = new ColorFrame (0.4f);
  private boolean frameOn = false;

  /** primitive materials */
  private float[] topDiffusion = {0.3f, 0.6f, 0.7f, 1.0f};
  private float[] topSpecularity = {0.8f, 0.2f, 0.3f, 1.0f};
  private float[] topShininess = {50.0f};
  private float[] uBaseDiffusion = {0.8f, 0.5f, 0.4f, 1.0f};
  private float[] uBaseSpecularity = {0.2f, 0.1f, 0.9f, 1.0f};
  private float[] uBaseShininess = {10.0f};
  private float[] lBaseDiffusion = {0.2f, 0.8f, 0.4f, 1.0f};
  private float[] lBaseSpecularity = {0.9f, 0.1f, 0.2f, 1.0f};
  private float[] lBaseShininess = {2.0f};

  /** Half value of the field of view */
  private double fieldOfView = 1.0;
  private boolean fieldOfViewChanged = false;
  private static final double FIELD_OF_VIEW_INC = 1.01;
  private static final double FIELD_OF_VIEW_MIN = 0.5;
  private static final double FIELD_OF_VIEW_MAX = 10.0;

  /** User-controlled rotations */
  private int turning = 0;
  public final static int HORIZONTAL = 1;
  public final static int VERTICAL = 2;
  public final static int SELF = 4;
  public boolean directTurn = true;
  private boolean objCentered = true;
  private float[] poseMatrix = {1.0f, 0.0f, 0.0f, 0.0f,
                                             0.0f, 1.0f, 0.0f, 0.0f,
                                             0.0f, 0.0f, 1.0f, 0.0f,
                                             0.0f, 0.0f, 0.0f, 1.0f};
  private float[] poseQuat = {1.0f, 0.0f, 0.0f, 0.0f};
  private static final float POSE_INC_COS = 0.9998476951563913f;
  private static final float POSE_INC_SIN = 0.01745240643728351f;
  private float azimuthAngle = 0.0f;
  private float heightAngle = 0.0f;
  private float selfAngle = 0.0f;
  private static final float ANGULAR_INC = 1.0f;

  /** Viewport size */
  private int vpWidth = 100, vpHeight = 100;

  /** Heart visualization */
  private boolean cullModif = false;


  /** Called by the drawable immediately after the OpenGL2 context is
    * initialized for the first time.
    * Implementation from GLEventListener. 
    * Used to perform one-time OpenGL2 initializations.
    * @param gLDrawable GLAutoDrawable object.
    */
  public void init (GLAutoDrawable gLDrawable)
  {
    // Light parameters
    float _lightPos[] = {1.0f, 1.0f, 3.0f, 0.0f};
    float _lightAmbient[] = {0.1f, 0.1f, 0.2f, 1.0f};
    float _lightDiffuse[] = {0.6f, 0.6f, 0.6f, 1.0f};
    float _lightSpecular[] = {0.6f, 0.6f, 0.6f, 1.0f};

    final GL2 gl = gLDrawable.getGL().getGL2 ();
    gl.glShadeModel (GL2.GL_FLAT);                  // No Smooth Shading
    gl.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);      // Black Background
    gl.glClearDepth (1.0f);                        // Depth Buffer Setup
    gl.glEnable (GL2.GL_DEPTH_TEST);                // Enables Depth Testing
    gl.glDepthFunc (GL2.GL_LEQUAL);                 // Type Of Depth Testing
    gl.glDisable (GL2.GL_CULL_FACE);                // Face culling off

    // Light model
    gl.glShadeModel (GL2.GL_SMOOTH);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_AMBIENT, _lightAmbient, 0);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_POSITION, _lightPos, 0);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_DIFFUSE, _lightDiffuse, 0);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_SPECULAR, _lightSpecular, 0);
    gl.glEnable (GL2.GL_LIGHTING);
    gl.glEnable (GL2.GL_LIGHT0);
  }

  /** Called by the drawable to initiate OpenGL2 rendering by the client.
    * Implementation from GLEventListener. 
    * After all GLEventListeners have been notified of a display event,
    * the drawable will swap its buffers if necessary.
    * @param gLDrawable GLAutoDrawable object.
    */
  public void display (GLAutoDrawable gLDrawable)
  {
    final GL2 gl = gLDrawable.getGL().getGL2 ();

    if (cullModif)
      if (gl.glIsEnabled (GL2.GL_CULL_FACE))
        gl.glDisable (GL2.GL_CULL_FACE);
      else gl.glEnable (GL2.GL_CULL_FACE);
    cullModif = false;

    gl.glClearColor (0.0f, 0.0f, 0.0f, 0.5f); // Black Background
    if (fieldOfViewChanged)
    {
      setProjection (gl, vpWidth, vpHeight);
      fieldOfViewChanged = false;
    }

    gl.glClear (GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

    switch (turning)
    {
      case HORIZONTAL :
        applyHorizontalTurn ();
        break;
      case VERTICAL :
        applyVerticalTurn ();
        break;
      case SELF :
        applySelfTurn ();
        break;
    }
    turning = 0;
    if (objCentered)
    {
      gl.glLoadIdentity ();
      gl.glRotatef (azimuthAngle, 0.0f, 1.0f, 0.0f);
      gl.glRotatef (heightAngle, 1.0f, 0.0f, 0.0f);
      gl.glRotatef (selfAngle, 0.0f, 0.0f, 1.0f);
    }
    else gl.glLoadMatrixf (poseMatrix, 0);

    gl.glTranslatef (0.0f, 0.0f, - 0.5f);

    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, topSpecularity, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, topShininess, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, topDiffusion, 0);
    skitty.drawTop (gl);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, uBaseSpecularity, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, uBaseShininess, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, uBaseDiffusion, 0);
    skitty.drawUpperBase (gl);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, lBaseSpecularity, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, lBaseShininess, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, lBaseDiffusion, 0);
    skitty.drawLowerBase (gl);

    if (frameOn)
    {
      gl.glDisable (GL2.GL_LIGHTING);
      ref.draw (gl);
      gl.glEnable (GL2.GL_LIGHTING);
    }
  }

  /** Called by the drawable during the first repaint after the component
    * has been resized.
    * Implementation from GLEventListener. 
    * @param gLDrawable GLAutoDrawable object.
    * @param x X Coordinate of the viewport area.
    * @param y Y coordinate of the viewport area.
    * @param width new width of the window.
    * @param height new height of the window.
    */
  public void reshape (GLAutoDrawable gLDrawable,
                       int x, int y, int width, int height)
  {
    final GL2 gl = gLDrawable.getGL().getGL2 ();

    if (height <= 0) height = 1; // avoid a divide by zero error!
    vpWidth = width;
    vpHeight = height;
    gl.glViewport (0, 0, vpWidth, vpHeight);
    setProjection (gl, vpWidth, vpHeight);
    gl.glLoadIdentity ();
  }

  /** Called when the display mode has been changed.
    * Implementation from GLEventListener. 
    * @param gLDrawable GLAutoDrawable object.
    */
  public void dispose (GLAutoDrawable glDrawable)
  {
  }

  /** Resets the projection matrix according to new viewport and
    * projection angle values.
    * @param gl OpenGL2 context.
    * @param width viewport width.
    * @param height viewport height.
    */ 
  private void setProjection (GL2 gl, int width, int height)
  {
    gl.glMatrixMode (GL2.GL_PROJECTION);
    gl.glLoadIdentity ();
    gl.glOrtho (- fieldOfView, fieldOfView,
                - fieldOfView * height / width,
                fieldOfView * height / width, - 10.0, 10.0);
    gl.glMatrixMode (GL2.GL_MODELVIEW);
  }

  /** Increases the projection field of view.
    */
  public void increaseFieldOfView ()
  {
    fieldOfView /= FIELD_OF_VIEW_INC;
    if (fieldOfView < FIELD_OF_VIEW_MIN) fieldOfView = FIELD_OF_VIEW_MIN;
    fieldOfViewChanged = true;
  }

  /** Decreases the projection field of view.
    */
  public void decreaseFieldOfView ()
  {
    fieldOfView *= FIELD_OF_VIEW_INC;
    if (fieldOfView > FIELD_OF_VIEW_MAX) fieldOfView = FIELD_OF_VIEW_MAX;
    fieldOfViewChanged = true;
  }

  /** Toggles the reference frame display */
  public void toggleFrame ()
  {
    frameOn = ! frameOn;
  }

  /** Toggles the face culling */
  public void cull ()
  {
    cullModif = true;
  }

  /** Controls the stripe type */
  public void incCountOfStripes ()
  {
    ns = skitty.setCountOfStripes (ns + 1);
  }

  /** Controls the stripe width */
  public void incStripeWidth (boolean larger)
  {
    sw = skitty.moveStripeWidth (larger);
  }

  /** Controls the chamfer type */
  public void incChamferType ()
  {
    ct = skitty.setChamferType (ct + 1);
  }
  
  /** Controls the stripe width */
  public void incChamferSize (boolean larger)
  {
    cs = skitty.moveChamferSize (larger);
  }

  /** Switches the rotation control mode between object- and user- centered. */ 
  public void switchControlMode ()
  {
    objCentered = ! objCentered;
  }

  /** Controls the primitive rotation in the horizontal plane.
    * @param plane rotation plane (HORIZONTAL, VERTICAL, SELF)
    * @param isDirect rotates counterclockwise if set to true.
    */ 
  public void turn (int plane, boolean isDirect)
  {
    turning |= plane;
    directTurn = isDirect;
  }

  /** Controls the primitive rotation in the horizontal plane.
    */ 
  private void applyHorizontalTurn ()
  {
    if (objCentered)
    {
      azimuthAngle += (directTurn ? ANGULAR_INC : - ANGULAR_INC);
      if (azimuthAngle > 180.0f) azimuthAngle -= 360.0f;
      else if (azimuthAngle < -180.0f) azimuthAngle += 360.0f;
    }
    else
      updatePose (new float[] {POSE_INC_COS, 0.0f,
                    (directTurn ? POSE_INC_SIN : - POSE_INC_SIN), 0.0f});
  }

  /** Controls the primitive rotation in vertical plane.
    */ 
  private void applyVerticalTurn ()
  {
    if (objCentered)
    {
      heightAngle += (directTurn ? - ANGULAR_INC : ANGULAR_INC);
      if (heightAngle > 180.0f) heightAngle -= 360.0f;
      else if (heightAngle < -180.0f) heightAngle += 360.0f;
    }
    else
      updatePose (new float[] {POSE_INC_COS,
                    (directTurn ? - POSE_INC_SIN : POSE_INC_SIN), 0.0f, 0.0f});
  }

  /** Controls the primitive rotation in front plane.
    */ 
  private void applySelfTurn ()
  {
    if (objCentered)
    {
      selfAngle += (directTurn ? - ANGULAR_INC : ANGULAR_INC);
      if (selfAngle > 180.0f) selfAngle -= 360.0f;
      else if (selfAngle < -180.0f) selfAngle += 360.0f;
    }
    else
      updatePose (new float[] {POSE_INC_COS, 0.0f, 0.0f,
                    (directTurn ? - POSE_INC_SIN : POSE_INC_SIN)});
  }

  /** Update the pose quaternion and the 4x4 pose matrix. */ 
  private void updatePose (float[] dr)
  {
    float[] np = {dr[0] * poseQuat[0] - dr[1] * poseQuat[1]
                  - dr[2] * poseQuat[2] - dr[3] * poseQuat[3],
                  dr[0] * poseQuat[1] + poseQuat[0] * dr[1]
                  + dr[2] * poseQuat[3] - dr[3] * poseQuat[2],
                  dr[0] * poseQuat[2] + poseQuat[0] * dr[2]
                  + dr[3] * poseQuat[1] - dr[1] * poseQuat[3],
                  dr[0] * poseQuat[3] + poseQuat[0] * dr[3]
                  + dr[1] * poseQuat[2] - dr[2] * poseQuat[1]};
    float n = (float) Math.sqrt (np[0] * np[0] + np[1] * np[1]
                                 + np[2] * np[2] + np[3] * np[3]);
    for (int i = 0; i < 4; i++) poseQuat[i] = np[i] / n;

    poseMatrix[0] = poseQuat[0] * poseQuat[0] + poseQuat[1] * poseQuat[1]
                    - poseQuat[2] * poseQuat[2] - poseQuat[3] * poseQuat[3];
    poseMatrix[1] = 2 * (poseQuat[1] * poseQuat[2] + poseQuat[0] * poseQuat[3]);
    poseMatrix[2] = 2 * (poseQuat[1] * poseQuat[3] - poseQuat[0] * poseQuat[2]);
    poseMatrix[4] = 2 * (poseQuat[1] * poseQuat[2] - poseQuat[0] * poseQuat[3]);
    poseMatrix[5] = poseQuat[0] * poseQuat[0] - poseQuat[1] * poseQuat[1]
                    + poseQuat[2] * poseQuat[2] - poseQuat[3] * poseQuat[3];
    poseMatrix[6] = 2 * (poseQuat[2] * poseQuat[3] + poseQuat[0] * poseQuat[1]);
    poseMatrix[8] = 2 * (poseQuat[1] * poseQuat[3] + poseQuat[0] * poseQuat[2]);
    poseMatrix[9] = 2 * (poseQuat[2] * poseQuat[3] - poseQuat[0] * poseQuat[1]);
    poseMatrix[10] = poseQuat[0] * poseQuat[0] - poseQuat[1] * poseQuat[1]
                     - poseQuat[2] * poseQuat[2] + poseQuat[3] * poseQuat[3];
  }

  /** Updates the normals of the lower base */
  public void updateLowerBaseNormals(GL2 gl) {
    skitty.drawLowerBase(gl); // Trigger the normal adjustment
  }
}
