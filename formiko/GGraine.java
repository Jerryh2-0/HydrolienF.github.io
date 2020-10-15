package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class GGraine {
  protected CGraine début;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public void GGraine(){début = null;}
  // GET SET --------------------------------------------------------------------
  public CGraine getDébut(){ return début;}
  public void setDébut(CGraine x){début=x;}
  public CGraine getFin(){
    if (début == null ){ return null;}
    CGraine fin = début;
    while (fin.getSuivant() != null){
      fin = fin.getSuivant();
    }
    return fin;
  }
  public Graine getGrainePlusDeNourritureFournieSansDureté(Fourmi f){
    if (début == null){ erreur.erreur("Impossible de sélectionné la meilleur graine dans une liste vide."); return null;}
    CGraine ci = this.getDébut();
    //if (ci.getSuivant() != null){
     return ci.getGrainePlusDeNourritureFournieSansDureté(ci.getContenu());
    /*}
    if(!ci.getContenu().getOuverte()){ // si elle est fermé et cassable.
      return ci.getContenu();
    }else{
      return null;
    }*/
  }
  public Graine getGrainePlusDeNourritureFournie(Fourmi f){
    if (début == null){ erreur.erreur("Impossible de sélectionné la meilleur graine dans une liste vide."); return null;}
    byte duretéMax = f.getDuretéMax();
    CGraine ci = this.getDébut();
    //if (ci.getSuivant() != null){
     return ci.getGrainePlusDeNourritureFournie(ci.getContenu(),duretéMax);
    /*}
    if(ci.getContenu().getDureté() <  f.getDuretéMax() && !ci.getContenu().getOuverte()){ // si elle est fermé et cassable.
      return ci.getContenu();
    }else{
      return null;
    }*/
  }
  public Graine getGraineOuverte(){
    if (début == null){ erreur.erreur("Impossible de sélectionné 1 graine ouverte dans une liste vide."); return null;}
    if (début.getContenu().getOuverte()){ return début.getContenu();}
    return début.getGraineOuverte();
  }
    // ici on choisirai la graine avec le plus de nourritureFournie parmi toutes les Graine que la fourmi peut ouvrir.
  //}

  public int length(){
    if(début==null){
      return 0;
    }else {
      return début.length();
    }
  }
  // Fonctions propre -----------------------------------------------------------
  public void afficheToi(){
    if (début == null){
      System.out.println("La liste de Graine est vide");
    } else {
      System.out.println("La liste de Graine : ");
      CGraine ci = début;
      while(ci != null){
        ci.getContenu().afficheToi();
        ci = ci.getSuivant();
      }
    }
  }
  public void ajouterGraine(CCase cc){ ajouterGraine(new Graine(cc));}
  public void ajouterGraine(Graine i){
    if (i != null){
      CGraine ci = new CGraine(i);
      ci.setSuivant(début);
      début = ci;
    }
  }public void ajouter(Graine i){ajouterGraine(i);}
  public void ajouterGg(GGraine giTemp){
    if (this.début == null){
      this.début = giTemp.getDébut();
    }else{
      this.getFin().setSuivant(giTemp.getDébut()); // On raccroche les 2 bouts.
    }
  }
  public void retirerGraine(int i){
    if (this.début == null){ erreur.alerte("Impossible de retirer i d'un groupe de Graine null"); return;}
    if(début.getContenu().getId()==i){
      retirerGraine(début.getContenu());
    }else{
      début.retirerGraine(i);
    }
  }
  public void retirerGraine(Graine i){
    debug.débogage("Suppression de : "+i.getId());
    if (i == null){
      erreur.alerte("Impossible de retirer null d'un groupe de Graine");
    }
    if (début == null){
      erreur.alerte("Impossible de retirer une Graine d'un groupe vide.");
    }else if (début.getContenu().equals(i)){ // Si c'est le 1a
      début = début.getSuivant(); // On en retir 1.
      debug.débogage("début = début.getSuivant();");
    } else if(début.getSuivant() != null && début.getSuivant().getContenu().equals(i)){ // Si c'est le 2a
      début.setSuivant(début.getSuivant().getSuivant());
    }else {
      début.retirerGraine(i);
    }
  }public void retirer(Graine i){ retirerGraine(i);}
  public String ggToString(){
    if (début==null){ return "";}
    return début.ggToString();
  }
  public GGraine copierGGraine(){
    if(début==null){ return new GGraine();}
    return début.copierGGraine();
  }
  public void tour(){
    if(début!=null){ début.tour();}
  }
}