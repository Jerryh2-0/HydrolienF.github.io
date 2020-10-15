package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class CIndividu {
  private Individu contenu;
  protected CIndividu suivant;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CIndividu(Individu iu){
    contenu = iu;
  }
  // GET SET --------------------------------------------------------------------
  public Individu getContenu(){return contenu;}
  public void setContenu(Individu c){contenu = c;}
  public CIndividu getSuivant(){return suivant;}
  public void setSuivant(CIndividu ce){ suivant =ce; }
  public Individu getIndividuParType(byte type){
    if (this.getContenu().getType() == type){ return this.getContenu();}
    if (this.getSuivant() == null){ return null;}
    return suivant.getIndividuParType(type);
  }
  public int [] getTypeDIndividu(){
    int lentr = this.length();
    int [] tr = new int [lentr];
    CIndividu ci = this;int k=0;
    while(ci != null && k<lentr){
      tr[k]=ci.getContenu().getType();k++;
      ci = ci.getSuivant();
    }
    return tr;
  }
  // Fonctions propre -----------------------------------------------------------
  public void afficheToi(){
    contenu.afficheToi();
    if(suivant != null){
      suivant.afficheToi();
    }
  }
  public int length(){
    if (suivant == null){ return 1;}
    return 1+suivant.length();
  }
}