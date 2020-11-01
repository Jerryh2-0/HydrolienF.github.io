package fr.formiko.usuel.conversiondetype;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.ascii;
import fr.formiko.usuel.tableau;

/**
 * {@summary Types conversions from String<br/>}
 * @author Hydrolien
 * @version 1.1
 */
public class str{
  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary get the number of char x in a String}
   *@param s String were to search.
   *@param x char to search on s.
   *@return number of char x in s.
   *@version 1.1
   */
  public static int nbrDeX(String s,char x){
    int xr=0;
    int lens = s.length();
    for (int i=0;i<lens ;i++ ) {
      if(s.charAt(i)==x){xr++;}
    }return xr;
  }
  /**
   *{@summary is subS on the String}
   *@param s String were to search.
   *@param subS String to search on s.
   *@param x 0=s should starts with subS, 1=s should contain subS, 2=s should end with subsS, 3=s should be equals to subS.
   *@return true if it contain subS
   *@version 1.2
   */
  public static boolean contient(String s,String subS, byte x){
    if(x<0 || x>4){return false;}
    if(s.length()<subS.length()){return false;}

    //if(x==3){return s.equals(subS);}
    if(x==1){return s.contains(subS);}//quelque part
    int lensubS = subS.length();
    if(x==0){
      s = s.substring(0,lensubS);//au début
    }else if(x==2){
      int lens = s.length();
      s = s.substring(lens-lensubS,lens);//a la fin
    }
    return s.equals(subS);
  }public static boolean contient(String s, String s2, int x){return contient(s,s2,iToBy(x));}
  public static boolean contient(String s,String s2){return contient(s,s2,1);}
  /**
   *{@summary add fin at the end off s, if s does not arlerdy end by fin.<br>}
   *@param s main String.
   *@param fin String to add on s.
   *@return s with fin at the end.
   *@version 1.2
   */
  public static String ajouterALaFinSiNecessaire(String s, String fin){
    if(!contient(s,fin,2)){s+=fin;}
    return s;
  }

  /**
  *{@summary Delete forbidden char in the array t.<br>}
  *@param s the String were to delete forbidden char.
  *@param t the array were forbidden char are.
  *@version 1.3
  */
  public static String filtreCharInterdit(String s, char t[]){
    if(s==null){return null;}
    String r = "";
    int len = s.length();
    for (int i=0;i<len ;i++ ) {
      char c = s.charAt(i);
      if(!tableau.contient(t,c)){ r=r+c;}
    }
    return r;
  }
  /**
  *{@summary Delete forbidden char depending of the os.<br>}
  *if os is not define windows char will be deleted.
  *@param s the String were to delete forbidden char.
  *@version 1.3
  */
  public static String filtreCharInterdit(String s){
    char w [] = {'<', '>', ':', '\"', '/', '\\', '|', '?', '*'};
    char ml [] = {':','/','\\'};
    if(Main.getOs()==null || Main.getOs().isWindows()){ return filtreCharInterdit(s,w);}
    return filtreCharInterdit(s,ml);
  }public static String sToFileName(String s){ return filtreCharInterdit(s);}
  /**
  *{@summary Transform a String to a directory name aviable on every os.<br>}
  *if last / is missing it will be add.
  *@param s the String to transform to a directory name.
  *@version 1.3
  */
  public static String sToDirectoryName(String s){
    char w [] = {'<', '>', ':', '\"', '\\', '|', '?', '*'};
    s = filtreCharInterdit(s,w);
    s = ajouterALaFinSiNecessaire(s,"/");
    return s;
  }

	//nouvelle partie :
	// conversion  :
  /**
  *{@summary From String to int}
  *return -1 if conversion fail.
  *@version 1.1
  */
  public static int sToI(String s){
    try {
      return (int) sToLThrows(s);
    }catch (Exception e) {
      erreurConversion("String To long",s);
      return -1;
    }
  }
  /**
  *{@summary From String to int}
  *Throw a Exception trows if conversion fail.
  *@version 1.1
  */
  public static long sToLThrows(String s) throws Exception {//on ne tolère que les espace les chiffres et les moins.
    int lens = s.length();String s2="";
    if(nbrDeX(s,' ')>0){
      for(int i=0;i<lens;i++){
        int asci = ascii.aToAscii(s.charAt(i));
        if((asci>=48 && asci<=57) || asci==45){// si c'est un - ou un chiffre on le garde.
          s2 = s2 + s.charAt(i);
        }else if(asci!=32){;throw new Exception();}//au 1 a char nom autorisé.
      }
      s=s2;
    }
    try {
      return Long.parseLong(s);
    }catch (Exception e) {
      throw new Exception();
    }
  }
  /**
  *{@summary From String to long}
  *return -1 if conversion fail.
  *@version 1.1
  */
  public static long sToL(String s){
    try {
      return sToLThrows(s);
    }catch (Exception e) {
      erreurConversion("String To long",s);
      return -1;
    }
  }

  /**
  *{@summary From int to String}
  *@version 1.1
  */
  public static String iToS(int x){
    return ""+x;
  }
  /**
  *{@summary From int to byte}
  *return the max or the min if conversion fail.
  *@version 1.1
  */
  public static byte iToBy(int x){
    if(x>127){ x=127;erreurConversion("int To byte",x+"");}
    if(x<-128){ x=-128;erreurConversion("int To byte",x+"");}
    return (byte) x;
  }
  /**
  *{@summary From int to boolean}
  *return true if conversion fail.
  *@version 1.1
  */
  public static boolean iToB(int b){
    if (b==0){ return false;}
    if (b==1){ return true;}
    erreurConversion("int To Boolean",b+"");
    return true;
  }
  /**
  *{@summary From String to boolean}
  *boolean can be "true", "false" or "1", "0".
  *@version 1.1
  */
  public static boolean sToB(String s){
    if("true".equals(s)){ return true;}
    if("false".equals(s)){ return false;}
    return iToB(sToI(s));
  }
  /**
  *{@summary From String to byte}
  *@version 1.1
  */
  public static byte sToBy(String s){
    return iToBy(sToI(s));
  }
  /**
  *{@summary special error for conversion}
  *return -1 if conversion fail.
  *@version 1.1
  */
  public static void erreurConversion(String xToY, String s){
    erreur.erreur(g.get("str",1,"Impossible d'effectuer une des conversions") +" "+ xToY +" "+g.get("str",2,"correctement")+" : "+s,"str.");
  }
  //tableaux
  /**
  *{@summary From int to String}
  *@version 1.1
  */
  public static int[] sToI(String ts[]){
    int lents=ts.length;
    int tr[]=new int[lents];
    for (int i=0;i<lents ;i++ ) {
      tr[i]=sToI(ts[i]);
    }
    return tr;
  }
  /**
  *{@summary From String to int}
  *file a case with -1 if conversion fail.
  *@version 1.1
  */
  public static String[] iToS(int ts[]){
    int lents=ts.length;
    String tr[]=new String[lents];
    for (int i=0;i<lents ;i++ ) {
      tr[i]=iToS(ts[i]);
    }
    return tr;
  }

	/*//anciène partie.
	public static String str(int s){
		return s+"";
	}*/
  //anciène partie.
  //Conversion de type.
  public static String str(int s){
    return String.valueOf(s);
  }
  public static String str(String s){
    return s;
  }
  public static String str(char c){
    return String.valueOf(c);
  }
  public static String str(double x){
    return String.valueOf(x);
  }
  public static String str(float x){
    return String.valueOf(x);
  }
}