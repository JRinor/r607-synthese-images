// Trame du TP de modelisation 3D : primitives solides
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
    Frame frame = new Frame ("Arlequins");

    // OpenGL display area creation
//Deprec     GLCapabilities capabilities = new GLCapabilities ();
//Deprec     //capabilities.setDoubleBuffered (false); 
    GLCanvas canvas = new GLCanvas ();

    // Adding a OpenGL context (view)
    int num = 1;
    if (args.length != 0)
      if (args[0].equals ("2")) num = 2;
      else if (args[0].equals ("3")) num = 3;
    ExoView myView = new ExoView (num);
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

    // Starting the AWT event loop
    canvas.requestFocus ();
  }
}
