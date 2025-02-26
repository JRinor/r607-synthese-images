// Trame du TP de modelisation 3D : embossage
// BUT Info - 2024/2025
// Preparateur: P. Even

import java.awt.Frame;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jogamp.opengl.awt.GLCanvas;
//Deprec import javax.media.opengl.GLCapabilities;


/** Exercice main window */
public class Exo
{
  /** Creates AWT Frame with OpenGL context */
  public static void main (String[] args)
  {
    // AWT window creation
    Frame frame = new Frame ("Carrosserie");

    // OpenGL display area creation
//Deprec     GLCapabilities capabilities = new GLCapabilities ();
//Deprec     //capabilities.setDoubleBuffered (false); 
    GLCanvas canvas = new GLCanvas ();

    // Adding a OpenGL context (view)
    ExoView myView = new ExoView ();
    canvas.addGLEventListener (myView);
    // Adding a user event handler (controller)
    ExoController myController = new ExoController (canvas, myView);
    canvas.addKeyListener (myController);

    // Window closing behavior
    frame.addWindowListener (
      new WindowAdapter ()
      {
        public void windowClosing (WindowEvent e)
        {
          System.exit (0);
        }
      });

    // End of window specification
    frame.add (canvas);
    frame.setSize (600, 600);
    frame.setLocation (0, 0);
    frame.setBackground (Color.white);
    frame.setVisible (true);

    // Starting the AWT loop
    canvas.requestFocus ();
  }
}
