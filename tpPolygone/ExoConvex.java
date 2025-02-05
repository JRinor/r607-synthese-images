// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Dimension;


/** Surface de trace pour le test de convexite. */
public class ExoConvex extends ExoView
{
  /** Nombre initial de sommets du polygone. */
  private final static int NB = 6;
  /** Distance d'interaction. */
  private final static int DIST2 = 100;
  /** Distance d'interaction. */
  private final static int RAD = 6;
  /** Frontieres de l'espace visible. */
  private final static int SIZE = 600;
  /** Couleur du segment en instance du service intersection. */
  private final static Color COL_INST = Color.YELLOW;
  /** Couleur des sommets. */
  private final static Color COL_PARAM = Color.BLUE;
  /** Couleur du point d'intersection. */
  private final static Color COL_PTINTER = Color.ORANGE;
  /** Couleur du fond d'ecran. */
  private final static Color COL_BACK = Color.BLACK;

  /** Polygone */
  private Polygone pol = null;
  /** Selection */
  private int sel = -1;


  /** Cree une surface de trace.
   */
  public ExoConvex ()
  {
    Point[] pts = new Point[NB];
    for (int i = 0; i < NB; i ++)
      pts[i] = new Point (
                 (int) (200. * Math.cos (2 * i * Math.PI / NB) + 300.5),
                 (int) (200. * Math.sin (2 * i * Math.PI / NB) + 300.5));
    pol = new Polygone (pts);
  }

  /** Trace les segments.
   */
  public void paint (Graphics g)
  {
    g.setColor (COL_BACK);
    g.fillRect (0, 0, SIZE, SIZE);
    Point[] pts = pol.sommets ();
    g.setColor (COL_INST);
    for (int i = 1; i < pts.length; i ++)
      g.drawLine ((int) (pts[i-1].x () + 0.5),
                  SIZE - 1 - (int) (pts[i-1].y () + 0.5),
                  (int) (pts[i].x () + 0.5),
                  SIZE - 1 - (int) (pts[i].y () + 0.5));
    g.drawLine ((int) (pts[pts.length-1].x () + 0.5),
                SIZE - 1 - (int) (pts[pts.length-1].y () + 0.5),
                (int) (pts[0].x () + 0.5),
                SIZE - 1 - (int) (pts[0].y () + 0.5));
    g.setColor (COL_PARAM);
    for (int i = 0; i < pts.length; i ++)
      g.fillOval ((int) (pts[i].x () + 0.5) - RAD / 2,
                  SIZE - 1 - (int) (pts[i].y () + 0.5) - RAD / 2,
                  RAD / 2 * 2, RAD / 2 * 2);
    g.setColor (COL_PTINTER);
    for (int i = 0; i < pts.length; i++)
      if (! pol.convexe (i))
        g.drawOval ((int) (pts[i].x () + 0.5) - RAD,
                    SIZE - 1 - (int) (pts[i].y () + 0.5) - RAD,
                    RAD * 2, RAD * 2);
  }

  /** Selectionne un sommet.
   */
  public boolean select (int x, int y)
  {
    Point impact = new Point (x, SIZE - 1 - y);
    sel = -1;
    for (int i = 0; i < pol.sommets().length; i++)
      if (impact.distance2 (pol.sommets()[i]) < DIST2)
      {
        sel = i;
        return (true);
      }
    return (false);
  }

  /** Deplace une extremite.
   */
  public void moveSelection (int x, int y)
  {
    if (sel != -1)
    {
      Point impact = new Point (x, SIZE - 1 - y);
      for (int i = 0; i < pol.sommets().length; i++)
        if (i != sel && impact.distance2 (pol.sommets()[i]) < DIST2) return;
      Segment seg = new Segment (pol.prec (sel), pol.suiv (sel));
      if (seg.distance2 (impact) < DIST2)
      {
        impact = seg.projete (impact);
        if (impact.distance2 (pol.prec (sel)) < DIST2
            || impact.distance2 (pol.suiv (sel)) < DIST2) return;
        else pol.sommets()[sel].set (impact);
      }
      else pol.sommets()[sel].set (impact);
    }
  }
}
