// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/** Gestionnaire d'evenements pour l'affichage de polygones. */
public class ExoController
  implements KeyListener, MouseListener, MouseMotionListener
{
  /** Surface de trace. */
  private ExoView myView;

  public ExoController (ExoView myView)
  {
    this.myView = myView;
  }

  public void keyPressed (KeyEvent e)
  {
    processKeyEvent (e, true);
  }

  public void keyReleased (KeyEvent e)
  {
    processKeyEvent (e, false);
  }

  private void processKeyEvent (KeyEvent e, boolean pressed)
  {
    switch (e.getKeyCode ())
    {
      case KeyEvent.VK_Q :
      case KeyEvent.VK_ESCAPE :
        if (! pressed) System.exit (0);
        break;
    }
  }

  public void keyTyped (KeyEvent e)
  {
    char c = e.getKeyChar ();
    if (c == 'n')
    {
      myView.next ();
      myView.repaint ();
    }
    else if (c == 'q') System.exit (0);
  }

  public void mouseClicked (MouseEvent e)
  {
  }

  public void mouseDragged (MouseEvent e)
  {
    myView.moveSelection (e.getX (), e.getY ());
    myView.repaint ();
  }

  public void mouseEntered (MouseEvent e)
  {
  }

  public void mouseExited (MouseEvent e)
  {
  }

  public void mouseMoved (MouseEvent e)
  {
  }

  public void mousePressed (MouseEvent e)
  {
    if (myView.select (e.getX (), e.getY ())) myView.repaint ();
  }

  public void mouseReleased (MouseEvent e)
  {
    myView.moveSelection (e.getX (), e.getY ());
    myView.repaint ();
  }
}
