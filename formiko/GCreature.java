package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

import fr.formiko.usuel.exeption.*;
public class GCreature {
  //protected Creature gc []; // A modifier en liste chainées, simple ou double a voir.
  protected CCreature début;
  protected CCreature fin;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GCreature(CCreature cc){
    début = cc; fin = cc;
  }
  public GCreature(Creature c){
    this(new CCreature(c));
  }
  public GCreature(){
    this((CCreature) null);
  }

  public GCreature(int nbrDeCreature, Fourmiliere fere, Espece e, CCase cc){
    this();
    debug.débogage("Création d'un groupe de Fourmi avec au moins 1 fourmis.");
    Fourmi reine = new Fourmi(fere,e, (byte) 0,(byte) 0);
    //reine.setCCase(cc);
    ajouterFin(reine);
    for (int i =1 ;i < nbrDeCreature ;i++ ) {
      Fourmi f = new Fourmi(fere,e,(byte) 3,(byte) 0,reine.getPheromone());
      //f.setCCase(cc);
      ajouterFin(f);
    }
  }
  // GET SET -----------------------------------------------------------------------
  public CCreature getDébut(){return début;}
  public CCreature getFin(){return fin;}
  public void setDébut(CCreature cc){début = cc;}
  public Fourmi getReine(){
    if (début==null){return null;}
    Fourmi f1 = (Fourmi) début.getContenu();
    if (f1.getType()==0){
      return f1;
    }else {
      return début.getReine();
    }
  }
  public Fourmi getPlusAffamée(){
    if (début==null){return null;}
    return début.getPlusAffamée();
  }
  public GCreature getGcStade(int x){
    if (début==null){return null;}
    return début.getGcStade(x);
  }
  public GCreature getGcType(int x){
    if (début==null){return null;}
    return début.getGcType(x);
  }
  public GCreature getCouvain(){ // on renvoie d'habord les plus proches de la transformation en Fourmi adulte.
    GCreature gcr = getGcStade(-1);
    gcr.ajouter(getGcStade(-2));
    gcr.ajouter(getGcStade(-3));
    return gcr;
  }
  public Creature getCouvainSaleE()throws ListeVideException{
    if (début==null){ throw new ListeVideException("GCreature","trouver la créature sale du couvain");}
    return début.getCouvainSale();
  }
  public Creature getCouvainSale(){
    try {
      return getCouvainSaleE();
    }catch (ListeVideException e) {
    return null;}
  }
  public GCreature getCouvainsSale(){
    GCreature gcr = getCouvain();
    // on garde le premier sale :
    while (gcr.getDébut() != null){
      Fourmi fTest = (Fourmi) gcr.getDébut().getContenu();
      if (fTest.getPropreté() < 90) {
        gcr.retirer(gcr.getDébut().getContenu());
      }else{
        break;
      }
    }if (gcr.getDébut() == null){ return null;}
    gcr.getDébut().getCouvainsSale(); // on filtre les propre dans la suite d ela liste.
    return gcr;
  }
  // a ajouter :
  // public GCreature getGcSiMemeFere(Fourmiliere fere){}
  private Creature getCreatureParIdE(int id)throws ListeVideException{
    if (début==null){ throw new ListeVideException("GCreature","trouver la créature "+id);}
    if (début.getContenu().getId()==id){
      return début.getContenu();
    }else {
      return début.getCreatureParId(id);
    }
  }
  public Creature getCreatureParId(int id){
    try {
      return getCreatureParIdE(id);
    }catch (ListeVideException e) {return null;}
  }
  public Fourmi getFourmiParId(int id){
    Creature c = getCreatureParId(id);
    try {
      return (Fourmi)c;
    }catch (Exception e) {
      erreur.erreur("La creature selectionné n'as pas put etre transformer en fourmi.");
      return null;
    }
  }
  private Fourmi getFourmiParFereE(Fourmiliere f)throws ListeVideException{
    if (début==null){ throw new ListeVideException("GCreature","trouver la créature par fere");}
    return début.getFourmiParFere(f);
  }
  public Fourmi getFourmiParFere(Fourmiliere f){
    try {
      return getFourmiParFereE(f);
    }catch (Exception e) {
      return null;
    }
  }
  public GCreature filtreAlliés(Creature c, int différenceTolléré){
    if (début==null || c==null){ return null;}
    return début.filtreAlliés(c,différenceTolléré);
  }
  public GCreature filtreAlliés(Creature c){ return filtreAlliés(c,5);} // en théorie 4 suffisent.
  public void setLienFere(Fourmiliere fere){
    if(début==null){ return;}
    début.setLienFere(fere);
  }
  public int getNbrGcStade(int x){ return getGcStade(x).length();}
  public int getNbrImago(){ return getNbrGcStade(0);}
  public int getNbrReine(){ return getGcStade(0).getGcType(0).length();}
  public int getNbrOuvrière(){
    try {
    return getGcStade(0).getGcType(3).length() + getGcStade(0).getGcType(4).length() + getGcStade(0).getGcType(5).length();
    }catch (Exception e) {
      erreur.erreur("Impossible de prende en compte les type major et minor dans les ouvrières.","GCreature.getNbrOuvrière");
      return getGcStade(0).getGcType(3).length();
    }
  }
  public Espece getEspece(){
    Fourmi c = this.getReine();
    if(c!=null){return c.getEspece();}
    if(début!=null){return ((Fourmi)début.getContenu()).getEspece();}
    return null;
  }

  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if (début==null){ return "";}
    return début.toString();
  }public String gcToString(){return gcToString();}
  public int length(){
    if (début==null){
      return 0;
    }else if(début.getContenu().equals(fin.getContenu())){
      return 1;
    }else {
      return début.length();
    }
  }
  public int [] gcToTInt(){
    if (début==null){ return null;}
    return début.gcToTInt();
  }
  public GCreature copier(){
    if(début==null){ return new GCreature();}
    return début.copier();
  }
  public void actualiserFin(){
    CCreature cc = début;
    while(cc!=null){
      cc=cc.getSuivant();
    }
    fin = cc;
  }
  public GInsecte getGi(){
    GInsecte gi = new GInsecte();
    CCreature cc = début;
    while(cc != null){
      try {
        gi.ajouter((Insecte) cc.getContenu());
      }catch (Exception e) {
        debug.débogage("Une créature a été filtré.");
      }
      cc = cc.getSuivant();
    }
    return gi;
  }
  public void ajouter(Creature c){
    ajouterFin(c);
  }public void add(Creature c){ajouter(c);}
  public void ajouterFin(Creature c){
    CCreature cc = new CCreature(c);
    if (fin ==  null){
      début = cc;
      fin = cc;
    }else {
      fin.setSuivant(cc);
      cc.setPrécédent(fin);
      fin = cc;
    }
  }
  public void ajouter(GCreature gc){
    if(gc == null || gc.getDébut() == null){ return;}
    if (fin == null){
      début = gc.getDébut();
      fin = gc.getFin();
    }else {
      fin.setSuivant(gc.getDébut());
      gc.getDébut().setPrécédent(fin);
      fin = gc.getFin();
    }
  }
  public void add(GInsecte gi ){
    GCreature gc = gi.toGCreature();
    ajouter(gc);
  }
  public void retirerE(Creature c)throws ListeVideException{
    if (début == null){ throw new ListeVideException("GCreature","retirer la Creature "+c.getId(),true);}//erreur.erreur("Aucune créature n'as pu être retirer car GCreature est vide","GCreature.retirer",true); return;}
    if (début.getContenu().equals(c)){
      if(fin.getContenu().equals(c)){
        début = null; fin = null; // on retire la seule créature
      }else{
        début = début.getSuivant(); // on retire la 1a créature.
      }
      return;
    }
    try {
      début.retirer(c);
    }catch (ElementListeIntrouvableException e) {}
    // On remet fin a la dennière case.
    fin = début;
    while(fin.getSuivant() != null){
      fin = fin.getSuivant();
    }
  }
  public void retirer(Creature c){
    try {
      retirerE(c);
    }catch (Exception e) {}
  }
  public void afficheToiE() throws ListeVideException{
    if(début==null){
      throw new ListeVideException("GCreature","tout afficher");
    }else{
      début.afficheTout();
    }
  }
  public void afficheToi(){
    try {
      afficheToiE();
    }catch (ListeVideException e) {}
  }
  public void afficheToiRéduitE() throws ListeVideException{
    if(début==null){
      throw new ListeVideException("GCreature","tout afficher");
    }else{
      System.out.print(g.get("listeCreature")+" : ");
      début.afficheToutRéduit();
    }
  }
  public void afficheToiRéduit(){
    try {
      afficheToiRéduitE();
    }catch (ListeVideException e) {}
  }
  private void jouerE() throws ListeVideException{
    if(début == null){
      throw new ListeVideException("GCreature","jouer");
    }else{
      début.jouer();
    }
  }
  public void jouer(){
    try{
      jouerE();
    }catch (ListeVideException e) {}
  }
  private void finTourE() throws ListeVideException{
    if(début == null){
      throw new ListeVideException("GCreature","finTour");
    }else{
      début.finTour();
    }
  }
  public void finTour(){
    try{
      finTourE();
    }catch (ListeVideException e) {}
  }
  public void actualiserCaseSN(){
    if(début == null){ return;}
    début.actualiserCaseSN();
  }
  public void setPheromone(Pheromone ph){
    if (début==null){ return;}
    début.setPheromone(ph);
  }
  public void classerPourNetoyage(){
    if (début==null){ return;}
    début.classerPourNétoyage();
  }
  public boolean aFiniDeJouer(){
    if (début==null){ return true;}
    return début.aFiniDeJouer();
  }
}