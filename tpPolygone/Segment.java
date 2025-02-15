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
  public boolean coupe (Segment seg) {
    // Calcul des produits vectoriels pour this par rapport à seg
    double x1 = pts[1].x() - pts[0].x();
    double y1 = pts[1].y() - pts[0].y();
    double x2 = seg.pts[0].x() - pts[0].x();
    double y2 = seg.pts[0].y() - pts[0].y();
    double x3 = seg.pts[1].x() - pts[0].x();
    double y3 = seg.pts[1].y() - pts[0].y();
    
    double pv1 = x1 * y2 - y1 * x2;
    double pv2 = x1 * y3 - y1 * x3;
    
    // Si les points de seg sont du même côté de this, pas d'intersection
    if ((pv1 > 0 && pv2 > 0) || (pv1 < 0 && pv2 < 0)) return false;
    
    // Calcul des produits vectoriels pour seg par rapport à this
    x2 = pts[0].x() - seg.pts[0].x();
    y2 = pts[0].y() - seg.pts[0].y();
    x3 = pts[1].x() - seg.pts[0].x();
    y3 = pts[1].y() - seg.pts[0].y();
    x1 = seg.pts[1].x() - seg.pts[0].x();
    y1 = seg.pts[1].y() - seg.pts[0].y();
    
    pv1 = x1 * y2 - y1 * x2;
    pv2 = x1 * y3 - y1 * x3;
    
    // Les points de this doivent être strictement de part et d'autre de seg
    return (pv1 * pv2 < 0);
  }

  /** Retourne l'intersection du segment avec un autre segment.
   * Retourne null s'il n'y a pas d'intersection.
   * @param seg L'autre segment
   */
  public Point intersection (Segment seg) {
    if (!coupe(seg)) return null;
    
    // Résolution du système d'équations linéaires
    double x1 = pts[1].x() - pts[0].x();
    double y1 = pts[1].y() - pts[0].y();
    double x2 = seg.pts[1].x() - seg.pts[0].x();
    double y2 = seg.pts[1].y() - seg.pts[0].y();
    
    double determinant = x1 * (-y2) - (-x2) * y1;
    
    if (Math.abs(determinant) < Point.EPS) return null;
    
    double dx = seg.pts[0].x() - pts[0].x();
    double dy = seg.pts[0].y() - pts[0].y();
    
    double t = (dx * (-y2) - (-x2) * dy) / determinant;
    
    return new Point(pts[0].x() + t * x1, pts[0].y() + t * y1);
  }
}
