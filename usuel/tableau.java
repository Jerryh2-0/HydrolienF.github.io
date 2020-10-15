package fr.formiko.usuel;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.g;

public class tableau <T>{
// piste pour utiliser des méthodes sur des tableau sans qu'un type soit forcément donné :
// List<Objet> mulist = new ArrayList<objet>();
// déclaration des Objet
// mylist.add(objet1) etc.
  private static String f = "tableau";
  public static void erreurPosition(int i){
    erreur.erreur(g.get(f,1,"La position")+" " + i + " "+g.get(f,2,"n'existe pas dans le tableau")+".", "tableau.retir");
  }
  public static void erreurPositionCorrigé(int i){
    erreur.erreur(g.get(f,1,"La position")+" " + i + " "+g.get(f,2,"n'existe pas dans le tableau")+".", "tableau.retir",g.get(f,3,"On ajoute x en position finale."));
  }
  public static void erreurVide(){
    erreur.alerte(g.get(f,5,"Le tableau est vide !"),"tableau.");
  }
  public static void erreurElementManquant(String x){
    erreur.alerte("\""+x + "\" "+g.get(f,4,"n'est pas présent dans le tableau")+".", "tableau.retirX");
  }
//Objet
  /*public static T [] retire (T t[], T i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      erreurPosition(i);
    }
    T tr [] = new Object [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
    }
    return tr;
  }*/
//String
  //Retire
  public static String [] retirer (String t[], int i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      erreurPosition(i);
    }
    String tr [] = new String [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
      //System.out.println("On garde " + tr[j]);
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
      //System.out.println("On garde " + tr[j-1]);
    }
    return tr;
  }//public static String [] retire(String t[], int i){retirer(t,i);}

  public static String [] retirerX (String t[], String x){
    int lent = t.length;
    // On  compte le nombre de x a retirer
    int k=0;
    for (int i=0;i<lent ;i++ ) {
      if (x.equals(t[i])){ k++;}
    }
    if (k==0){ erreurElementManquant(x);}
    String tr [] = new String [lent-k];
    // On les retire
    int m=0;
    for (int j=0;j<lent; j++){
      if (!x.equals(t[j])){ // Si c'est qqchose qu'on garde :
        tr[m]=t[j]; m++;
      }//sinon j augemnte mais pas m
    }
    return tr;
  }//public static String [] retireX(String t[],String x){return retirerX(t,x);}

//AJOUTE
  public static String [] ajouterX (String t[], String x, int i){
    // Fonction qui permet d'ajouté x en position i du tableau.
    if (i<0 || i>t.length) {
      erreurPositionCorrigé(i);
      i = t.length;
    }
    int lentr = t.length+1;
    String tr [] = new String [lentr];
    for (int j=0;j<i ;j++ ) {
      tr [j] = t[j];
    }
    tr[i] = x;
    for (int j=i+1;j<lentr ;j++ ) {
      tr[j] = t[j-1];
    }
    return tr;
  }
  public static String [] ajouterX (String t[], String x){
    return ajouterX(t,x,t.length); // Par défaut on ajoute a la dernière case d'un tableau
  }//public static String [] ajouteX(String t[],String x){return ajouterX(t,x);}
  public static String [] ajouteT (String t1[], String t2[]){
    int lent1 = t1.length; int lent2 = t2.length;
    String tr [] = new String [lent1 + lent2];
    for (int i=0;i<lent1 ;i++ ) {
      tr[i] = t1[i];
    }
    for (int i=0;i<lent2 ;i++ ) {
      tr[lent1 + i] = t2[i];
    }
    return tr;
  }


  // int
  // retir
  public static int [] retire (int t[], int i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      erreurPosition(i);
    }
    int tr [] = new int [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
      //System.out.println("On garde " + tr[j]);
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
      //System.out.println("On garde " + tr[j-1]);
    }
    return tr;
  }

  public static int [] retirerX (int t[], int x){
    int lent = t.length;
    // On  compte le nombre de x a retirer
    int k=0;
    for (int i=0;i<lent ;i++ ) {
      if (x == t[i]){ k++;}
    }
    if (k==0){ erreurElementManquant(x+"");}
    int tr [] = new int [lent-k];
    // On les retire
    int m=0;
    for (int j=0;j<lent; j++){
      if (x != t[j]){ // Si c'est qqchose qu'on garde :
        tr[m]=t[j]; m++;
      }//sinon j augemnte mais pas m
    }
    return tr;
  }//public static void retirerX(int t[],int x){return retirerX(t,x);}

//AJOUTE
  public static int [] ajouteX (int t[], int x, int i){
    // Fonction qui permet d'ajouté x en position i du tableau.
    if (i<0 || i>t.length) {
      erreurPositionCorrigé(i);
      i = t.length;
    }
    int lentr = t.length+1;
    int tr [] = new int [lentr];
    for (int j=0;j<i ;j++ ) {
      tr [j] = t[j];
    }
    tr[i] = x;
    for (int j=i+1;j<lentr ;j++ ) {
      tr[j] = t[j-1];
    }
    return tr;
  }
  public static int [] ajouteX (int t[], int x){
    return ajouteX(t,x,t.length); // Par défaut on ajoute a la dernière case d'un tableau
  }
  public static int [] ajouteT (int t1[], int t2[]){
    int lent1 = t1.length; int lent2 = t2.length;
    int tr [] = new int [lent1 + lent2];
    for (int i=0;i<lent1 ;i++ ) {
      tr[i] = t1[i];
    }
    for (int i=0;i<lent2 ;i++ ) {
      tr[lent1 + i] = t2[i];
    }
    return tr;
  }



  // byte
  // retir
  public static byte [] retire (byte t[], int i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      erreurPositionCorrigé(i);
    }
    byte tr [] = new byte [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
      //System.out.println("On garde " + tr[j]);
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
      //System.out.println("On garde " + tr[j-1]);
    }

    return tr;
  }

  public static byte [] retireX (byte t[], byte x){
    int lent = t.length;
    // On  compte le nombre de x a retirer
    byte k=0;
    for (byte i=0;i<lent ;i++ ) {
      if (x == t[i]){ k++;}
    }
    if (k==0){ erreurElementManquant(x+"");}
    byte tr [] = new byte [lent-k];
    // On les retire
    byte m=0;
    for (byte j=0;j<lent; j++){
      if (x != t[j]){ // Si c'est qqchose qu'on garde :
        tr[m]=t[j]; m++;
      }//sinon j augemnte mais pas m
    }
    return tr;
  }

  //AJOUTE
  public static byte [] ajouteX (byte t[], byte x, int i){
    // Fonction qui permet d'ajouté x en position i du tableau.
    if (i<0 || i>t.length) {
      erreurPositionCorrigé(i);
      i = t.length;
    }
    int lentr = t.length+1;
    byte tr [] = new byte [lentr];
    for (int j=0;j<i ;j++ ) {
      tr [j] = t[j];
    }
    tr[i] = x;
    for (int j=i+1;j<lentr ;j++ ) {
      tr[j] = t[j-1];
    }
    return tr;
  }
  public static byte [] ajouteX (byte t[], byte x){
    return ajouteX(t,x,t.length); // Par défaut on ajoute a la dernière case d'un tableau
  }
  public static byte [] ajouteT (byte t1[], byte t2[]){
    int lent1 = t1.length; int lent2 = t2.length;
    byte tr [] = new byte [lent1 + lent2];
    for (byte i=0;i<lent1 ;i++ ) {
      tr[i] = t1[i];
    }
    for (byte i=0;i<lent2 ;i++ ) {
      tr[lent1 + i] = t2[i];
    }
    return tr;
  }

  public static boolean estDansT(int t[], int x){
    int lent = t.length;
    for(int i=0; i<lent;i++){
      if(t[i]==x){ return true;}
    }
    return false;
  }

  //AFFICHE
  /*public static void affiche (Object t[], String séparateur){
    int lent =t.length;
    if (lent==0) { erreur.alerte("Le tableau a afficher est vide.","tableau.affiche");}
    for(int i=0;i<lent;i++){
      System.out.println(t[i] + séparateur); // ca marche mal pour les objet mais il ont pas tous une class afficheToi() ;(
    }
    System.out.println();
  }
  public static void affiche (Object t[]){
    affiche(t," ");
  }*/

  // affiche str
  public static void afficher (String t[], String séparateur){
    int lent =t.length;
    if (lent==0) { erreurVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher (String t[]){
    afficher(t," ");
  }
  public static void afficher (String t[][]){
    for ( String t2 [] : t) {
      afficher(t2," ");
    }
  }

  // afficher int
  public static void afficher (int t[], String séparateur){
    int lent =t.length;
    if (lent==0) { erreurVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher (int t[]){
    afficher(t," ");
  }
  public static void afficher (int t[][]){
    if (t.length==0) { erreurVide();}
    for ( int t2 [] : t) {
      afficher(t2," ");
    }
  }
  //afficher byte
  public static void afficher(byte t[], String séparateur){
    int lent =t.length;
    if (lent==0) { erreurVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher (byte t[]){
    afficher(t," ");
  }
  //afficher Boolean
  public static void afficher(boolean t[], String séparateur){
    int lent =t.length;
    if (lent==0) { erreurVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher(boolean t[][], String s){
    for (int i=0;i<t.length ;i++ ) {
      afficher(t[i],s);
    }
  }
  public static void afficher(boolean t[]){afficher(t," ");}
  public static void afficher(boolean t[][]){afficher(t," ");}
  // autres
  public static void boucherLesCasesVide(String t[], String tDéfaut []){
    int lent = t.length;
    for (int i=0;i<lent ;i++ ) {
      if( "".equals(t[i])){
        t[i] = tDéfaut[i];
      }
    }
  }
  public static String tableauToString(String t []){
    String sr = "";
    for (String s :t ) {
      sr=sr+" "+s;
    }return sr;
  }
  public static String tableauToString(int t []){
    String sr = "";
    for (int s :t ) {
      sr=sr+" "+s;
    }return sr;
  }
}