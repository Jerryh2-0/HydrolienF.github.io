package fr.formiko.usuel.math;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

/**
 * {@summary Do math operation.<br/>}
 * @author Hydrolien
 * @version 1.1
 */
public class math {
  //int
  /**
  *{@summary return maximum}
  *@return maximum of 2 int
  *@version 1.1
  */
  public static int max(int x, int y){
    if (y>x){ return y;}
    return x;
  }
  /**
  *{@summary return maximum}
  *@return maximum of int t []
  *@version 1.1
  */
  public static int max(int t[]){
    int lent = t.length;
    int xMax = t[0];
    for (int i=0; i<lent-1;i++){
      xMax = max(t[i+1],xMax);
    }
    return xMax;
  }
  /**
  *{@summary return maximum}
  *@return maximum of int t [][]
  *@version 1.1
  */
  public static int max(int t[][]){
    int lent = t.length;
    int xMax = max(t[0]);
    for (int i=0; i<lent-1;i++){
      int xMax2 = max(t[i+1]);
      xMax = max(xMax,xMax2);
    }
    return xMax;
  }
  /**
  *{@summary return minimum}
  *@return minimum of 2 int
  *@version 1.1
  */
  public static int min(int x, int y){
    if (y<x){ return y;}
    return x;
  }
  /**
  *{@summary return minimum}
  *@return minimum of int t []
  *@version 1.1
  */
  public static int min(int t[]){
    int lent = t.length;
    int xMin = t[0];
    for (int i=0; i<lent-1;i++){
      xMin = min(t[i+1],xMin);
    }
    return xMin;
  }
  /**
  *{@summary return minimum}
  *@return minimum of int t [][]
  *@version 1.1
  */
  public static int min(int t[][]){
    int lent = t.length;
    int xMin = min(t[0]);
    for (int i=0; i<lent-1;i++){
      int xMin2 = min(t[i+1]);
      xMin = min(xMin,xMin2);
    }
    return xMin;
  }
  /**
  *{@summary return absolute value}
  *@return absolute value of an int.
  *@version 1.1
  */
  public static int valAbs(int x){
    if (x<0) { return x*-1;}
    return x;
  }
  //double
  /**
  *{@summary return maximum}
  *@return maximum of double
  *@version 1.1
  */
  public static double max(double x, double y){
    if (y>x){ return y;}
    return x;
  }
  /**
  *{@summary return maximum}
  *@return maximum of double
  *@version 1.1
  */
  public static double max(double t[]){
    int lent = t.length;
    double xMax = t[0];
    for (int i=0; i<lent-1;i++){
      xMax = max(t[i+1],xMax);
    }
    return xMax;
  }
  /**
  *{@summary return minimum}
  *@return minimum of double
  *@version 1.1
  */
  public static double min(double x, double y){
    if (y<x){ return y;}
    return x;
  }
  /**
  *{@summary return minimum}
  *@return minimum of double
  *@version 1.1
  */
  public static double min(double t[]){
    int lent = t.length;
    double xMin = t[0];
    for (int i=0; i<lent-1;i++){
      xMin = min(t[i+1],xMin);
    }
    return xMin;
  }
  /**
  *{@summary return absolute value}
  *@return absolute value of double
  *@version 1.1
  */
  public static double valAbs(double x){
    if (x<0) { return x*-1;}
    return x;
  }

  // somme :
  /**
  *{@summary return the sum from 0 to k }
  *@return sum from 0 to k
  *@version 1.1
  */
  public static int sommeDe0AXSwitch(int k){
    int mem [] = new int [2];
    int ic = 0;
    while(true){
      switch (ic++){
        case 0: mem[0] = 0; break;
        case 1: mem[1] = 0; break;
        case 2: if (mem[0]>=k+1) ic+=3; break;
        case 3: mem[1]+=mem[0]; break;
        case 4: mem[0]++; break;
        case 5: ic-=4; break;
        case 6: return mem[1];
      }
    }
  }
  /**
  *{@summary return the sum from 0 to k }
  *@return sum from 0 to k or -1 if k &lt; 0
  *@version 1.1
  */
  public static int sommeDe0AX(int k){
    int somme = 0;
    for (int i=1;i<=k;i++ ) {
      somme+=i;
    }
    return somme;
  }
  /*public static int factorielle(int k){
    if (k <= 0) {
      erreur.erreur("Impossible de calculé n! si n n'est pas positif","math.factorielle");
      return -1;
    }
    if (k == 1){
      return 1;
    } else {
      return factorielle(k-1)*k;piNoir/20
    }
  }
  public static int factorielleSwitch(int k){
    if (k <= 0) {
      erreur.erreur("Impossible de calculé n! si n n'est pas positif","math.factorielleSwitch");
      return -1;
    }
    int mem [] = new int [2];
    int ic = 0;
    while(true){
      switch (ic++){
        case 0: mem[0] = 1; break;
        case 1: mem[1] = 1; break;
        case 2: if (mem[0]>=k+1) ic+=3;break; // test de sortie de boucle
        case 3: mem[1]*=mem[0]; break;
        case 4: mem[0]++; break;
        case 5: ic-=4; break;
        case 6: return mem[1];
      }
    }
  }*/
  /**
  *{@summary return k! }
  *@return k! or -1 if k &lt; 0
  *@version 1.1
  */
  public static long factorielle(int k){
    if(k==0){return 0;}
    if (k < 0) {
      erreur.erreur("Impossible de calculé n! si n n'est pas positif n="+k,"math.factorielleSwitchLong");
      return -1;
    }
    long mem [] = new long [2];
    int ic = 0;
    while(true){
      switch (ic++){
        case 0: mem[0] = 1; break;
        case 1: mem[1] = 1; break;
        case 2: if (mem[0]>=k+1) ic+=3;break; // test de sortie de boucle
        case 3: mem[1]*=mem[0]; break;
        case 4: mem[0]++; break;
        case 5: ic-=4; break;
        case 6: return mem[1];
      }
    }
  }
  /*public static long factorielleNMoinsK(int max, int n){
    if (max <= 0 ) {
      erreur.erreur("Impossible de calculé n! si n n'est pas positif","math.factorielleSwitchLong");
      return -1;
    }
    if (n < max){
      erreur.erreur("Impossible de calculé un un factorielle négatif");
    }
    long mem [] = new long [2];
    int ic = 0;
    while(true){
      switch (ic++){
        case 0: mem[0] = max; break;
        case 1: mem[1] = 1; break;
        case 2: if (mem[0]>=max+1) ic+=3;break; // test de sortie de boucle
        case 3: mem[1]*=mem[0]; break;
        case 4: mem[0]++; break;
        case 5: ic-=4; break;
        case 6: return mem[1];
      }
    }
  }*/
  /**
  *{@summary n choose k}
  *@return n choose k
  *@version 1.1
  */
  public static int kParmiN(int k, int n){
    // k parmi n = n!/(k! * (n-k)!) c'est la formule simple.
    // k parmi n = n! - k! / (n-k)! OU k! echange avec (n-k)! c'est la fourmule économe en calcule.
    if (k < 0 || n < 0){erreur.erreur("nombre dégatif","math.kParmiN");return -1;}
    if(k>n){erreur.erreur("k<n","math.kParmiN");return -1;}
    if(n==0){return 0;}
    long xr;
    xr = factorielle(n)/(factorielle(k)*factorielle(n-k));
    /*System.out.println("xr = "+xr);
    int max = max(k,n-k);
    int min = min(k,n-k);
    System.out.println("fact min = " + factorielle(min));
    System.out.println("factorielleNMoinsK = " + factorielleNMoinsK(max,n));
    xr = (factorielleNMoinsK(max,n)) / factorielle(min);*/
    return (int) xr;
  }
  /**
  *{@summary Syracuse suite}
  *@return Syracuse suite
  *@version 1.1
  */
  public static int syracuse(int n){
    if (n <= 0) {
      erreur.erreur("Impossible de calculé syracuse si n n'est pas positif","math.syracuse");
      return -1;
    }
    // u¨0 = n et u¨(i+1) = u¨i/2 si pair
    //            u¨(i+1) = 3*u¨i+1 sinon
    int i =0;
    while (n != 1){
      i++;
      if (n%2 == 0 ){
        n = n/2;
      }else{
        n = 3*n + 1;
      }
    }
    return i;
  }
  /**
  *{@summary Syracuse suite}
  *@return Syracuse suite
  *@version 1.1
  */
  public static int syracuseSwitch(int n){
    if (n <= 0) {
      erreur.erreur("Impossible de calculé syracuse si n n'est pas positif","math.syracuseSwitch");
      return -1;
    }
    int i=0;
    int ic = 0;
    while(true){
      switch (ic++) {
        case 0 : i = 0; break;
        case 1 : if(n==1) ic+=2;break;
        case 2 : if(n%2 == 0) {n = n/2; ic-=2; i++; } break;
        case 3 : n = 3*n+1; ic-=3; i++; break;
        case 4 : return i;
        default : System.out.println("Sortie en cours de boucle");return i;
      }
    }
  }

}