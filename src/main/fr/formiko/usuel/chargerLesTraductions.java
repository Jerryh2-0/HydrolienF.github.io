package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.util.Map;
import java.util.HashMap;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.liste.GString;
import java.io.File;

/**
*{@summary Loard translation file class.<br>}
*@author Hydrolien
*@version 1.5
*/
public class chargerLesTraductions {
  private static Map<String, String> map;
  private static String rep="data/langue/";
  private static String tLangue[];
  // get set -------------------------------------------------------------------
  public static String [] getTLangue(){return tLangue;}
  public static void setTLangue(String t []){tLangue=t;}
  public static String getRep(){return rep;}
  public static void setRep(String s){rep = str.sToDirectoryName(s);}
  public static void setRep(){setRep("data/langue/");}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary get the int that corresponds to the language String.<br>}
  *@param x language id.
  *@return language String in ISO code 639-1 if tLangue is correct.
  *@version 1.5
  */
  public static String getLangue(int x){
    if(tLangue == null || x<0 || x>=tLangue.length){
      int l = 0;
      if(tLangue!=null){l=tLangue.length;}
      erreur.erreur("langue non reconnu parmi les "+l+" langue(s) disponible(s).","chargerLesTraductions.getLangue","\"en\" retourné");
      return "en";
    }
    return tLangue[x];
  }
  /**
  *{@summary get the String that corresponds to the language int.<br>}
  *An error will return 2, the id of "en" (english) (except if tLangue isn't correctly loard (return -1;))
  *@param s language String in ISO code 639-1.
  *@version 1.5
  */
  public static int getLangue(String s){
    if(tLangue == null || s==null || s.equals("")){ return -1;}
    int k=0;
    for (String s2 : tLangue) {
      if(s2.equals(s)){return k;}
      k++;
    }
    if(tLangue.length<3){return -1;}
    return 2;
  }
  /**
  *{@summary Loard language file "langue.csv".<br>}
  *If it fail only "en" will be aviable.
  *@version 1.5
  */
  public static boolean iniTLangue(){
    try {
      String t []=lireUnFichier.lireUnFichier(rep+"langue.csv");
      if(t==null || t.length==0){throw new Exception();}//on passe dans le catch.
      tLangue=new String[t.length];int k=0;
      for (String s : t ) {
        String s2 = s.split(",")[0];
        tLangue[k]=s2;k++;
      }
      return true;
    }catch (Exception e) {
      erreur.erreur("Impossible de charger tLangue.");
      tLangue=new String [1]; tLangue[0]="en";
      return false;
    }
  }
  /**
  *{@summary Check that every language file exists and create is if it's need.<br>}
  *@version 1.5
  */
  public static boolean créerLesFichiers(){
    for (String s :tLangue ) {
      File f = new File(rep+str.sToFileName(s)+".txt");
      try {
        f.createNewFile();
      }catch (Exception e) {
        erreur.erreur("Impossible de créer un fichier de trad","chargerLesTraductions.créerLesFichiers");
        return false;
      }
    }
    return true;
  }
  /**
  *{@summary Check that the line is a translation line.<br>}
  *@param s line to check
  *@version 1.5
  */
  public static boolean estLigneDeTrad(String s){
    if(s==null){return false;}
    if(s.length()<2){return false;}//minimum = "c:"
    //if(s.charAt(0)=='/' && s.charAt(1)=='/'){return false;}
    if(str.contient(s,"//",0)){return false;}
    if(str.contient(s,":",0)){return false;}
    if(str.nbrDeX(s,':')!=1){return false;}//si il y a un nombre différent de fois ":" que 1 fois.
    //return s.length()>2 && (s.charAt(0)!='\\' || s.charAt(1)!='\\');// si la ligne n'est pas vide ou pas un commentaire.
    return true;
  }




  public static String [] getTableauDesTrad(int langue){
    //String tDéfaut [] = lireUnFichier.lireUnFichier(rep+"fr.txt");
    String t [] = new String[0];
    try{
      debug.débogage("chargement de la langue "+getLangue(langue));
      t=lireUnFichier.lireUnFichier(rep+getLangue(langue)+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement de la langue spécifiée","en choisi par défaut");
      t=lireUnFichier.lireUnFichier(rep+"en.txt");
    }
    return t;
  }
  public static String [] getTableauDesCmd(){
    String t [] = new String[0];
    try{
      debug.débogage("chargement des commandes");
      t=lireUnFichier.lireUnFichier(rep+"cmd"+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement des commandes");
    }
    return t;
  }
  public static Map<String, String> chargerLesTraductions(int langue){
    debug.débogage("Chargement des textes");//on lit le fichier de langue
    map = chargerLesTraductionsSansCommande(langue);
    String t2[] = getTableauDesCmd();
    for (String s : t2) {//on ajoute toutes les commande qu'on peu ajouter.
      ajouterObjetMap(s);
    }
    return map;
  }
  public static Map<String, String> chargerLesTraductionsSansCommande(int langue){
    map = new HashMap<>();
    String t[] = getTableauDesTrad(langue);
    for (String s : t) {//on ajoute toutes les lignes qu'on peu ajouter.
      ajouterObjetMap(s);
    }
    return map;
  }
  private static void ajouterObjetMap(String s){
    if(!estLigneDeTrad(s)){return;}
    //getPosDu1a ":"
    //coupe en 2.
    int lens = s.length();
    debug.débogage("Ligne non vide et non commentaire idendifiée");
    String s1 = "";
    String s2 = "";
    int i=0;
    debug.débogage("élément de "+lens+"identifié");
    char c = s.charAt(i);
    while(c != ':' && i<lens-1){
      if(c!='\\'){s1 = s1+c;}
      i++; c = s.charAt(i);
    }
    while(i<lens-1){
      i++; c = s.charAt(i);
      s2 = s2+c;
    }
    debug.débogage("Ajout du couple clé valeur  "+s1+" : "+s2);
    map.put(s1,s2);
  }
  public static void ajouterTradAuto(){
    int lentl=getTLangue().length;
    new ThTrad(0);
    for (int i=2;i<lentl ;i++ ) {
      new ThTrad(i);
    }
  }
  private static char maj(char c){
    if(c < 123 && c > 96){ return (char) (c-32);}
    if(c=='é'){ return 'É';}
    if(c=='è'){ return 'È';}
    if(c=='ë'){ return 'Ë';}
    if(c=='ê'){ return 'Ê';}
    if(c=='ĉ'){ return 'Ĉ';}
    if(c=='ç'){ return 'Ç';}
    if(c=='ĵ'){ return 'Ĵ';}
    if(c=='â'){ return 'Â';}
    if(c=='ĝ'){ return 'Ĝ';}

    return c;
  }
  public static int getPourcentageTraduit(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=lireUnFichier.lireUnFichier(rep+getLangue(langue)+".txt");
    }catch (Exception e) {}
      for (String s : t ) {
        if(estLigneDeTrad(s)){
          if(fini(s)){ x++;}
        }
      }
    int xFr = chargerLesTraductionsSansCommande(1).size();
    return (x*100)/xFr;
  }
  public static int getPourcentageTraduitAutomatiquement(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=lireUnFichier.lireUnFichier(rep+getLangue(langue)+".txt");
    }catch (Exception e) {}
      for (String s : t ) {
        if(s.length()>6 && s.substring(s.length()-6).equals("[auto]")){
          x++;
        }
      }
    int xFr = chargerLesTraductionsSansCommande(1).size();
    return (x*100)/xFr;
  }
  public static void affPourcentageTraduit(){
    int lentl=getTLangue().length;
    for (int i=0;i<lentl ;i++) {
      String s = "";int x=getPourcentageTraduitAutomatiquement(i); if(x>0){s=" ("+x+"% traduit automatiquement)";}
      int y = getPourcentageTraduit(i);
      if(x>0){
        System.out.println(getLangue(i)+" : "+y+"%"+s);
      }
    }
  }
  public static boolean fini(String s){
    int lens = s.length();
    for (int i=0;i<lens-1;i++ ) {
      if (s.charAt(i)==':'){ return true;} // si il y a au moins 1 char après les :
    }return false; // sinon
  }
}