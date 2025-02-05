// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)



/** Point dans le plan */
public class Point
{
  /** Coordonnees du point. */
  private double x, y;
  /** Seuil de comparaison pour les calculs. */
  public final static double EPS = 0.0001;


  /** Cree un point a partir de ses coordonnees.
   * @param x Abscisse
   * @param y Ordonnee
   */
  public Point (double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  /** Cree un point par recopie.
   * @param p Original
   */
  public Point (Point p)
  {
    this (p.x, p.y);
  }

  /** Retourne l'abscisse du point.
   */
  public double x ()
  {
    return x;
  }

  /** Retourne l'ordonnee du point.
   */
  public double y ()
  {
    return y;
  }

  /** Deplace le point.
   */
  public void set (int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  /** Deplace le point.
   */
  public void set (Point p)
  {
    this.x = p.x;
    this.y = p.y;
  }

  /* Retourne le carre de la distance a un autre point.
   */
  public double distance2 (Point p)
  {
    return ((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
  }

  /* Teste l'egalité avec un autre point.
   */
  public boolean equals (Object obj)
  {
    if (! (obj instanceof Point)) return false;
    double dx = ((Point) obj).x - x;
    double dy = ((Point) obj).y - y;
    return (dx * dx + dy * dy < EPS * EPS);
  }

  /* Teste si l'abscisse est egale a celle d'un autre point.
   */
  public boolean idX (Point p)
  {
    return ((p.x - x) * (p.x - x) < EPS * EPS);
  }

  /* Teste si l'ordonnee est egale a celle d'un autre point.
   */
  public boolean idY (Point p)
  {
    return ((p.y - y) * (p.y - y) < EPS * EPS);
  }

  /** Convertit le point en chaine de caracteres.
   */
  public String toString ()
  {
    return ("(" + x + "," + y + ")");
  }
}
