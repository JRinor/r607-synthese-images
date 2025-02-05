// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
// Complete par X



/** Segment dans le plan */
public class Segment
{
  private Point[] pts = new Point[2];


  /** Cree un segment a patrir de ses extremites.
   * @param pt1 Extremite de depart
   * @param pt2 Extremite d'arrivee
   */
  public Segment (Point pt1, Point pt2)
  {
    pts[0] = pt1;
    pts[1] = pt2;
  }

  /** Convertit le segment en chaine de caracteres.
   */
  public String toString ()
  {
    return ("<" + pts[0] + "," + pts[1] + ">");
  }

  /** Retourne les coordonnees des extremites.
   */
  public double[] coordonnees ()
  {
    return (new double[] { pts[0].x (), pts[0].y (),
                           pts[1].x (), pts[1].y () });
  } 

  /** Retourne une des extremites du segment.
   */
  public Point extremite (int num)
  {
    return (pts[num]);
  } 

  /** Verifie si le point fourni est sur le segment.
   */
  public boolean contient (Point p)
  {
    double eps = Math.sqrt (pts[0].distance2 (pts[1])) * Point.EPS;
    return (((pts[1].x () - pts[0].x ()) * (p.x () - pts[0].x ())
             + (pts[1].y () - pts[0].y ()) * (p.y () - pts[0].y ()) > - eps)
            && ((pts[0].x () - pts[1].x ()) * (p.x () - pts[1].x ())
                + (pts[0].y () - pts[1].y ()) * (p.y () - pts[1].y ()) > - eps)
            && distance2 (p) < Point.EPS * Point.EPS);
  }

  /** Retourne le carre de la distance du point fourni a la droite support.
   */
  public double distance2 (Point p)
  {
    double pv = (pts[1].x () - pts[0].x ()) * (p.y () - pts[0].y ())
                - (pts[1].y () - pts[0].y ()) * (p.x () - pts[0].x ());
    return (pv * pv / pts[0].distance2 (pts[1]));
  }

  /** Retourne le projete du point fourni sur la droite support.
   */
  public Point projete (Point p)
  {
    double h = ((pts[1].x () - pts[0].x ()) * (p.x () - pts[0].x ())
                + (pts[1].y () - pts[0].y ()) * (p.y () - pts[0].y ()))
               / pts[0].distance2 (pts[1]);
    return (new Point (pts[0].x () + (pts[1].x () - pts[0].x ()) * h,
                       pts[0].y () + (pts[1].y () - pts[0].y ()) * h));
  } 

  /** Verifie si le segment en coupe un autre.
   * Teste une coupure stricte (extremites de this non comprises)
   * @param seg Autre segment.
   */
  public boolean coupe (Segment seg)
  {
    return (false);
  }

  /** Retourne l'intersection du segment avec un autre segment.
   * Retourne null s'il n'y a pas d'intersection.
   * @param seg L'autre segment
   */
  public Point intersection (Segment seg)
  {
    return (null);
  }
}
