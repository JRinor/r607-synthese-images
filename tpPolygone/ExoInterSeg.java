// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;


/** Surface de trace pour le test d'intersection de segments. */
public class ExoInterSeg extends ExoView
{
  /** Frontieres de l'espace visible. */
  private final static int SIZE = 600;
  /** Distance d'interaction. */
  private final static int DIST2 = 100;
  /** Distance d'interaction. */
  private final static int RAD = 6;
  /** Couleur du segment en instance du service intersection. */
  private final static Color COL_INST = Color.YELLOW;
  /** Couleur du segment en parametre en l'absence d' intersection. */
  private final static Color COL_PARAM = Color.BLUE;
  /** Couleur du segment en parametre en intersection. */
  private final static Color COL_INTER = Color.RED;
  /** Couleur du point d'intersection. */
  private final static Color COL_PTINTER = Color.ORANGE;
  /** Couleur du fond d'ecran. */
  private final static Color COL_BACK = Color.BLACK;

  /** Segments */
  private Segment[] segs = new Segment[2];
  /** Selection */
  private int sel = -1;


  /** Cree une surface de trace.
   */
  public ExoInterSeg ()
  {
    segs[0] = new Segment (new Point (120, 180), new Point (420, 120));
    segs[1] = new Segment (new Point (360, 480), new Point (120, 360));
  }

  /** Trace les segments.
   */
  public void paint (Graphics g)
  {
    g.setColor (COL_BACK);
    g.fillRect (0, 0, SIZE, SIZE);
    g.setColor (COL_INST);
    double[] ext = segs[0].coordonnees ();
    g.drawLine ((int) (ext[0] + 0.5), SIZE - 1 - (int) (ext[1] + 0.5),
                (int) (ext[2] + 0.5), SIZE - 1 - (int) (ext[3] + 0.5));
    g.setColor (segs[0].coupe (segs[1]) ? COL_INTER : COL_PARAM);
    ext = segs[1].coordonnees ();
    g.drawLine ((int) (ext[0] + 0.5), SIZE - 1 - (int) (ext[1] + 0.5),
                (int) (ext[2] + 0.5), SIZE - 1 - (int) (ext[3] + 0.5));
    Point inter = segs[0].intersection (segs[1]);
    if (inter != null)
    {
      g.setColor (COL_PTINTER);
      g.drawOval ((int) (inter.x () + 0.5) - RAD,
                  SIZE - 1 - (int) (inter.y () + 0.5) - RAD, RAD * 2, RAD * 2);
    }
  }

  /** Selectionne un segment.
   */
  public boolean select (int x, int y)
  {
    Point impact = new Point (x, SIZE - 1 - y);
    if (impact.distance2 (segs[0].extremite (0)) < DIST2) sel = 0;
    else if (impact.distance2 (segs[0].extremite (1)) < DIST2) sel = 1;
    else if (impact.distance2 (segs[1].extremite (0)) < DIST2) sel = 2;
    else if (impact.distance2 (segs[1].extremite (1)) < DIST2) sel = 3;
    else
    {
      sel = -1;
      return (false);
    }
    return (true);
  }

  /** Deplace une extremite.
   */
  public void moveSelection (int x, int y)
  {
    Point impact = new Point (x, SIZE - 1 - y);
    if (sel == 0)
    {
      if (impact.distance2 (segs[0].extremite (1)) > DIST2)
      {
        if (impact.distance2 (segs[1].extremite (0)) < DIST2)
          segs[0].extremite(0).set (segs[1].extremite (0));
        else if (impact.distance2 (segs[1].extremite (1)) < DIST2)
          segs[0].extremite(0).set (segs[1].extremite (1));
        else if (segs[1].distance2 (impact) < DIST2)
          segs[0].extremite(0).set (segs[1].projete (impact));
        else segs[0].extremite(0).set (impact);
      }
    }
    else if (sel == 1 )
    {
      if (impact.distance2 (segs[0].extremite (0)) > DIST2)
      {
        if (impact.distance2 (segs[1].extremite (0)) < DIST2)
          segs[0].extremite(1).set (segs[1].extremite (0));
        else if (impact.distance2 (segs[1].extremite (1)) < DIST2)
          segs[0].extremite(1).set (segs[1].extremite (1));
        else if (segs[1].distance2 (impact) < DIST2)
          segs[0].extremite(1).set (segs[1].projete (impact));
        else segs[0].extremite(1).set (impact);
      }
    }
    else if (sel == 2)
    {
      if (impact.distance2 (segs[1].extremite (1)) > DIST2)
      {
        if (impact.distance2 (segs[0].extremite (0)) < DIST2)
          segs[1].extremite(0).set (segs[0].extremite (0));
        else if (impact.distance2 (segs[0].extremite (1)) < DIST2)
          segs[1].extremite(0).set (segs[0].extremite (1));
        else if (segs[0].distance2 (impact) < DIST2)
          segs[1].extremite(0).set (segs[0].projete (impact));
        else segs[1].extremite(0).set (impact);
      }
    }
    else if (sel == 3)
    {
      if (impact.distance2 (segs[1].extremite (0)) > DIST2)
        segs[1].extremite(1).set (impact);
      {
        if (impact.distance2 (segs[0].extremite (0)) < DIST2)
          segs[1].extremite(1).set (segs[0].extremite (0));
        else if (impact.distance2 (segs[0].extremite (1)) < DIST2)
          segs[1].extremite(1).set (segs[0].extremite (1));
        else if (segs[0].distance2 (impact) < DIST2)
          segs[1].extremite(1).set (segs[0].projete (impact));
        else segs[1].extremite(1).set (impact);
      }
    }
  }
}
