// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Dimension;


/** Surface de trace pour le test d'appartenance d'un point au polygone. */
public class ExoPointIn extends ExoView
{
  /** Nombre initial de sommets du polygone. */
  private final static int NB = 24;
  /** Distance d'interaction. */
  private final static int DIST2 = 100;
  /** Distance d'interaction. */
  private final static int RAD = 6;
  /** Frontieres de l'espace visible. */
  private final static int SIZE = 600;
  /** Couleur du segment en instance du service intersection. */
  private final static Color COL_INST = Color.YELLOW;
  /** Couleur du point. */
  private final static Color COL_PARAM = Color.BLUE;
  /** Couleur du point d'intersection. */
  private final static Color COL_PTINTER = Color.ORANGE;
  /** Couleur du fond d'ecran. */
  private final static Color COL_BACK = Color.BLACK;

  /** Polygone */
  private Polygone pol = null;
  /** Point */
  private Point pt = null;
  /** Selection */
  private int sel = -1;
  /** Numero de test */
  private int numero = 0;


  /** Cree une surface de trace.
   */
  public ExoPointIn ()
  {
    next ();
  }


  /** Cree le polygone suivant
   */
  public void next ()
  {
    pt = new Point (300, 300);
    if (numero == 0)
    {
      Point[] pts = new Point[NB];
      for (int i = 0; i < NB; i ++)
      {
        double r = (i % 3 == 0 ? 50. : 250.);
        pts[i] = new Point (
                   (int) (r * Math.cos (2 * i * Math.PI / NB) + 300.5),
                   (int) (r * Math.sin (2 * i * Math.PI / NB) + 300.5));
      }
      pol = new Polygone (pts);
    }
    else if (numero == 1)
    {
      pol = new Polygone (new Point[] {
        new Point (325, 275), new Point (325, 325),
        new Point (250, 325), new Point (250, 225),
        new Point (375, 225), new Point (375, 375),
        new Point (200, 375), new Point (200, 175),
        new Point (425, 175), new Point (425, 425),
        new Point (150, 425), new Point (150, 125),
        new Point (475, 125), new Point (475, 425),
        new Point (450, 425), new Point (450, 150),
        new Point (175, 150), new Point (175, 400),
        new Point (400, 400), new Point (400, 200),
        new Point (225, 200), new Point (225, 350),
        new Point (350, 350), new Point (350, 250),
        new Point (275, 250), new Point (275, 275)});
    }
    else if (numero == 2)
    {
      pol = new Polygone (new Point[] {
        new Point (450, 300), new Point (550, 550),
        new Point (450, 450), new Point (300, 550),
        new Point (300, 450), new Point (50, 550),
        new Point (150, 450), new Point (50, 300),
        new Point (150, 300), new Point (50, 50),
        new Point (150, 150), new Point (300, 50),
        new Point (300, 150), new Point (550, 50),
        new Point (450, 150), new Point (550, 300)});
    }
    else if (numero == 3)
    {
      pol = new Polygone (new Point[] {
        new Point (500, 300), new Point (400, 275),
        new Point (450, 450), new Point (400, 350),
        new Point (300, 500), new Point (325, 400),
        new Point (150, 450), new Point (250, 400),
        new Point (100, 300), new Point (200, 325),
        new Point (150, 150), new Point (200, 250),
        new Point (300, 100), new Point (275, 200),
        new Point (450, 150), new Point (350, 200)});
    }
    else if (numero == 4)
    {
      pol = new Polygone (new Point[] {
        new Point (550, 300), new Point (500, 300),
        new Point (400, 275), new Point (500, 500),
        new Point (450, 450), new Point (400, 350),
        new Point (300, 550), new Point (300, 500),
        new Point (325, 400), new Point (100, 500),
        new Point (150, 450), new Point (250, 400),
        new Point (50, 300), new Point (100, 300),
        new Point (200, 325), new Point (100, 100),
        new Point (150, 150), new Point (200, 250),
        new Point (300, 50), new Point (300, 100),
        new Point (275, 200), new Point (500, 100),
        new Point (450, 150), new Point (350, 200)});
    }
    if (++numero == 5) numero = 0;
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
    g.fillOval ((int) (pt.x () + 0.5) - RAD / 2,
                SIZE - 1 - (int) (pt.y () + 0.5) - RAD / 2,
                RAD / 2 * 2, RAD / 2 * 2);
    g.setColor (COL_PTINTER);
    if (! pol.contient (pt))
      g.drawOval ((int) (pt.x () + 0.5) - RAD,
                  SIZE - 1 - (int) (pt.y () + 0.5) - RAD,
                  RAD * 2, RAD * 2);
  }

  /** Selectionne un sommet.
   */
  public boolean select (int x, int y)
  {
    Point impact = new Point (x, SIZE - 1 - y);
    sel = (impact.distance2 (pt) < DIST2 ? 0 : -1);
    return (sel == 0);
  }

  /** Deplace une extremite.
   */
  public void moveSelection (int x, int y)
  {
    if (sel != -1)
    {
      Point impact = new Point (x, SIZE - 1 - y);
      Segment[] polar = pol.aretes ();
      for (int i = 0; i < polar.length; i++)
        if (polar[i].distance2 (impact) < DIST2)
        {
          Point proj = polar[i].projete (impact);
          if (polar[i].contient (proj))
          {
            pt.set (proj);
            return;
          }
        }
      Point c = new Point (300, 300);
      pt.set (impact.distance2 (c) < DIST2 ? c : impact);
    }
  }
}
