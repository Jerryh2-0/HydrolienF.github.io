package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;

public class Case {
  private Point p;
  private byte type; //0 = herbe ...
  private Fourmiliere fere;
  private GCreature gc; private GGraine gg;
  private byte nourritureInsecte;
  private byte nourritureInsecteMax;
  private byte nourritureInsecteParTour;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Case(Point p, Fourmiliere fere, GCreature gc, byte nourritureInsecte, byte nourritureInsecteMax, byte nt){
    this.p =p;
    this.fere = fere;
    this.gc = gc;
    this.nourritureInsecte = nourritureInsecte;
    this.nourritureInsecteMax = nourritureInsecteMax;
    nourritureInsecteParTour = nt;
    this.gg = new GGraine(); type = 1;
  }
  public Case(Point p, Fourmiliere fere, GCreature gc){
    this(p,fere,gc,(byte) allea.getAlléa(3),(byte)(allea.getAlléa(100)+2),(byte) allea.getAlléa(3));
    // si la nourriture de départ n'est pas réduite :
    nourritureInsecte = (byte) allea.getAlléa(nourritureInsecteMax);
  }
  public Case(Point p){this(p,null,new GCreature());}
  public Case(int x, int y){this(new Point(x,y));}
  // GET SET --------------------------------------------------------------------
  public Point getP(){return p;}
  public Point getPoint(){ return getP();}
  public void setP(Point p){this.p = p;}
  public int getX(){ return p.getX();}
  public int getY(){ return p.getY();}
  public Fourmiliere getFere(){return fere;}
  public void setFere(Fourmiliere fere){this.fere = fere;}
  public GCreature getGc(){return gc;}
  public void setGc(GCreature gc){this.gc = gc;}
  public GInsecte getGi(){return gc.getGi();}
  public byte getNourritureInsecte(){return nourritureInsecte;}
  public void setNourritureInsecte(byte x){nourritureInsecte=x;}
  public byte getNourritureInsecteMax(){return nourritureInsecteMax;}
  public void setNourritureInsecteMax(byte x){nourritureInsecteMax =x;}
  public byte getNourritureInsecteParTour(){return nourritureInsecteParTour;}
  public void setNourritureInsecteParTour(byte x){nourritureInsecteParTour=x;}
  public GGraine getGGraineCopier(){return gg.copierGGraine();}
  public GGraine getGGraine(){return gg;}
  public GGraine getGg(){ return getGGraine();}
  public byte getType(){ return type;}
  public void setType(byte x){type = x; if(type==3 || type<0){setNourritureInsecteMax((byte)0); setNourritureInsecteParTour((byte)0);}}
  public void setType(int x){setType((byte)x);}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String s = g.get("case")+" : ("+g.get("nourritureInsecte")+" :"+nourritureInsecte+"/"+nourritureInsecteMax+" (+"+nourritureInsecteParTour+"))";
    s=s+ p.toString();s=s+"\n";
    if (fere != null){
      s=s+g.get("fourmiliere")+" :";s=s+"\n";
      s=s+fere.toString();s=s+"\n";
    }
    if (gc != null && gc.getDébut() != null){
      s=s+g.get("creatures")+" : "; s=s+"\n";
      s=s+gc.toString();s=s+"\n";
    }
    if (gg != null && gg.getDébut() != null){
      s=s+g.get("graines")+" : ";s=s+"\n";
      s=s+gg.toString();s=s+"\n";
    }
    return s;
  }
  public void afficheToi(){
    System.out.println(this);
  }
  public int getNbrDElementSurCase(){
    int xr = 0;
    if (fere != null){ xr=1;}
    return xr + gc.length() + gg.length();
  }
  public int length(){
    return getNbrDElementSurCase();
  }
  public boolean equals(Case c){
    if(c.length() != this.length()){ return false;}
    if(!this.getPoint().equals(c.getPoint())){ return false;}
    return true;
  }
  public boolean estCaseVide(){
    if (getNbrDElementSurCase() == 0){ return true;}
    return false;
  }
  public String description(){
    return p.toString();
  }
  public void actualisationNourritureInsecte(){
    setNourritureInsecte((byte) (getNourritureInsecte()+getNourritureInsecteParTour()));
    if(nourritureInsecte > nourritureInsecteMax){
      nourritureInsecte=nourritureInsecteMax;
    }
  }
  public void actualisationGraine(CCase p){
    //ici un %age dépendant du type de la Case et de la saison serait bienvenue. (multiplié par l'abondance des graines.)
    int x  = allea.getAlléa(50);
    if(x==0 && this.getFere()==null){ gg.ajouterGraine(p);} // si on a de la chance et que il n'y a pas de fere sur la case.
    gg.tour();
  }
}