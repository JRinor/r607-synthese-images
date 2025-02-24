// Trame du TP de modelisation 3D : primitives solides
// BUT Info - 2024/2025
// Preparateur: P. Even

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
//Deprec import java.util.TimerTask;


/** Event handler for OpenGL exercices */
public class ExoController implements KeyListener
{
  /** Controlled display area. */
  private GLCanvas canvas;
  /** Controlled OpenGL context. */
  private ExoView myView;

  /** Constructs a event handler.
    * @param canvas display area.
    * @param myView OpenGL context.
    */
  public ExoController (GLCanvas canvas, ExoView myView)
  {
    this.myView = myView;
    this.canvas = canvas;
    FPSAnimator animator = new FPSAnimator (canvas, 60, true);
//Deprec     animator.setRunAsFastAsPossible (false);
    animator.start ();
  }

  /** Invoked when a key has been pressed.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyPressed (KeyEvent e)
  {
    processKeyEvent (e, true);
  }

  /** Invoked when a key has been released.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyReleased (KeyEvent e)
  {
    processKeyEvent (e, false);
  }

  /** Invoked when a key has been pressed or released.
    * Local implementation from KeyListener.
    * @param e detected key event.
    * @param pressed pressed or released key status.
    */
  private void processKeyEvent (KeyEvent e, boolean pressed)
  {
    switch (e.getKeyCode ())
    {
      case KeyEvent.VK_Q :
      case KeyEvent.VK_ESCAPE :
        if (! pressed) System.exit (0);
        break;
      case KeyEvent.VK_M :
        if (pressed) myView.switchControlMode ();
        break;
      case KeyEvent.VK_UP :
        if (pressed) myView.turn (ExoView.VERTICAL, true);
        break;
      case KeyEvent.VK_DOWN :
        if (pressed) myView.turn (ExoView.VERTICAL, false);
        break;
      case KeyEvent.VK_LEFT :
        if (pressed) myView.turn (ExoView.HORIZONTAL, false);
        break;
      case KeyEvent.VK_RIGHT :
        if (pressed) myView.turn (ExoView.HORIZONTAL, true);
        break;
      case KeyEvent.VK_O :
        if (pressed) myView.turn (ExoView.SELF, false);
        break;
      case KeyEvent.VK_P :
        if (pressed) myView.turn (ExoView.SELF, true);
        break;
    }
  }

  /** Invoked when a key has been typed.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyTyped (KeyEvent e)
  {
    switch (e.getKeyChar ())
    {
      case '1' :
        myView.set (1);
        break;
      case '2' :
        myView.set (2);
        break;
      case '3' :
        myView.set (3);
        break;
      case '4' :
        myView.set (4);
        break;
      case '5' :
        myView.set (5);
        break;
      case '6' :
        myView.set (6);
        break;
      case '7' :
        myView.set (7);
        break;
      case '8' :
        myView.set (8);
        break;
      case 'r' :
        myView.testResolution (7);
        break;
      case 'R' :
        myView.testResolution (15);
        break;
      case 'f' :
        myView.toggleFrame ();
        break;
      case 't' :
        myView.tiny ();
        break;
      case 'b' :
        myView.switchBackground ();
        break;
      case 'c' :
        myView.cull ();
        break;
      case 'z' :
        myView.decreaseFieldOfView ();
        break;
      case 'Z' :
	myView.increaseFieldOfView ();
        break;
    }
  }
}
