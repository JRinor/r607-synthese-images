// Trame du TP sur le traitement des polygones
// BUT Info - 2024/2025
// Preparateur: P. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
// Complete par X



/** Polygones dans le plan */
public class Polygone
{
  /** Caracteristiques possibles du polygone. */
  public final static int TRIANGLE = 0;
  public final static int CONVEXE = 1;
  public final static int MONOTONE_XY = 2;
  public final static int MONOTONE_X = 3;
  public final static int MONOTONE_Y = 4;
  public final static int SIMPLE = 5;      // sans auto-intersections
  public final static int INCOHERENT = 6;  // avec auto-intersections
  public final static int INCONNU = 7;
  /** Noms des caracteristiques. */
  public final static String[] NOM = {"TRIANGLE", "CONVEXE", "MONOTONE_XY",
                                      "MONOTONE_X", "MONOTONE_Y",
                                      "SIMPLE", "INCOHERENT", "INCONNU"};
  /** Sommets du polygone. */
  private Point[] pts = null;
  /** Type du polygone. */
  private int type = INCONNU;


  /** Cree un polygone a partir de sommets.
   * @param pts Tableau de sommets
   */
  public Polygone (Point[] pts)
  {
    this.pts = pts;
  }

  /** Convertit le polygone en chaine de caracteres.
   */
  public String toString ()
  {
    String s = "[" + pts[0];
    for (int i = 1; i < pts.length; i++) s += "," + pts[i];
    return (s + "]");
  }

  /** Retourne les sommets du polygone.
   */
  public Point[] sommets ()
  {
    return (pts);
  }

  /** Retourne le sommet suivant le ieme sommet.
   */
  public Point suiv (int i)
  {
    return (pts[i == pts.length - 1 ? 0 : i + 1]);
  }

  /** Retourne le sommet precedent le ieme sommet.
   */
  public Point prec (int i)
  {
    return (pts[i == 0 ? pts.length - 1 : i - 1]);
  }

  /** Retourne le barycentre du polygone.
   */
  public Point barycentre ()
  {
    double x = 0., y = 0.;
    for (int i = 0; i < pts.length; i++)
    {
      x += pts[i].x ();
      y += pts[i].y ();
    }
    return (new Point (x / pts.length, y / pts.length));
  }

  /** Retourne les aretes du polygone.
   */
  public Segment[] aretes ()
  {
    Segment[] aretes = new Segment[pts.length];
    for (int i = 0; i < pts.length - 1; i ++)
      aretes[i] = new Segment (pts[i], pts[i + 1]);
    aretes[pts.length - 1] = new Segment (pts[pts.length - 1], pts[0]);
    return (aretes);
  }

  /** Verifie si un point appartient au polygone.
   * @param pt Point a tester
   */
  public boolean contient (Point pt)
  {
    return (false);
  }

  /** Verifie si un sommet est convexe.
   * @param n Indice du sommet a tester
   */
  public boolean convexe (int n)
  {
    return (true);
  }
}
