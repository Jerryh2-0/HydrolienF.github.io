package fr.formiko.formiko;
import fr.formiko.graphisme.*;import fr.formiko.usuel.*;import fr.formiko.usuel.son.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.image.*;
import fr.formiko.usuel.liste.*;
import fr.formiko.usuel.conversiondetype.str;
import java.time.LocalDateTime;
import java.util.Map;
import java.awt.Font;
import java.io.File;
import fr.formiko.usuel.test.test;
// ArrayList<?> list; permet de déclarrer une liste de tout type. Sinon mettre l'objet ou Integer a la place. on peu aussi mettre Object pour spécifier que ce sera une liste d'objet.
// diff fichier1 fichier2 permet de comparer de façon très complète, les différences entre des fichiers. On peu comparer tout le contenu de formiko avec /diff -r Formiko14 Formiko15

/**
*{@summary Launch class <br>}
*Main file have all the shortcut on getter or setter that are curently used
*@author Hydrolien
*@version 1.1
*/

/*
Commencer par {@summary Phrase qui résume bien la class}
Les descriptions sont de la forme si dessu avec la premiere phrase décris la class ou la fonction ou meme la variable.
phrase courte.
utiliser "it" ou "elle" pour expliquer le fonctionnement d'une class ou méthode.(Ou juste la 3a personne du sigulier "Return true if...")
détailler le focntionnement des méthodes.
penser a indiquer @param et @return sauf si il n'y en a pas.
on peu utiliser des balistes html dans les commentaires. La plus utile étant <br>

Mettre l'auteur Hydrolien au début de la class si j'ai tout fait, sinon 1 auteur par fonction.
Mettre param puis version a la fin.

*/

public class Main {
  /**
  *{@summary The compatible version for the options file, keys and backups.}
  *@version 1.1
  */
  private static int versionActuelle = 100;
  /**
   * Main windows
   * @version 1.1
   */
  private static Fenetre f;
  private static Options op;
  private static Chrono ch;
  private static long lon;
  private static long lonTotal;
  private static long tempsDeDébutDeJeu;
  private static Partie pa;
  private static byte niveauDeDétailDeLAffichage=3;
  /**
   * Contain the String in the chosen language.
   * @version 1.1
   */
  private static Map<String, String> map; // map.get(clé) permet d'obtenir le texte associé.
  private static Pixel pi;
  private static Map<String, Integer> key;
  private static boolean ecouteClavier;
  private static int avancementChargement;
  private static boolean affGraine=true;//tant que les espece granivore ne sont pas pleinement opérationelle.
  private static Temps tem;
  private static ThTriche trich; //écoute de commande triche dans le terminal.
  private static ThGraphisme tg;//actualise la fenetre tt avec 20 seconde de pause entre chaque actualisation.
  private static boolean retournerAuMenu;
  private static Os os;
  private static boolean tuto=false;
  private static ThScript ths;
  private static ThMusique thm;
  private static boolean premierePartie=false;

  /**
   * {@summary Lauch the game.<br>}
   * It can have some args to do special thing.<br>
   * -d - set on the debug mode.<br>
   * trad make sur that every language file is 100% translated. It can auto translate some texte if the python file and translation api are on the same folder.<br>
   * op save the Options.txt file<br>
   * Others args are not fuly usable for now.<br>
   * @param args[] It can contain -d, trad, son, op, test, supprimer
   * @version 1.1
   */
  public static void main (String [] args){
    //trad.copieTrads();quitter();
    debug.setAffLesEtapesDeRésolution(false);
    debug.setAffLesPerformances(false);
    debug.setAffG(false);
    //menuPrincipal();
    while(args.length > 0 && args[0].substring(0,1).equals("-")){//si il y a des options a "-"
      if(args[0].equals("-d")){
        debug.setAffLesEtapesDeRésolution(true);
      }else if(args[0].equals("-reload--graphics") || args[0].equals("-rg")){
        initialisation();
        getOp().setGarderLesGraphismesTourné(false);
      }else{
        erreur.alerte("une options dans la ligne de commande n'as pas été reconnue");
      }
      args = tableau.retirer(args, 0);
    }
    if(args.length>0){
      initialisation();
      if(args[0].equals("trad")){
        debug.setAffLesPerformances(true);
        débutCh();
        chargerLesTraductions.iniTLangue();
        chargerLesTraductions.créerLesFichiers();
        finCh("créerLesFichiers");débutCh();
        map = chargerLesTraductions.chargerLesTraductions(1);//chargement des langues.
        finCh("chargerLesTraductions");débutCh();
        trad.copieTrads();
        finCh("copieTrads");débutCh();
        chargerLesTraductions.affPourcentageTraduit();
        finCh("affPourcentageTraduit");débutCh();
        chargerLesTraductions.ajouterTradAuto();
        finCh("ajouterTradAuto");débutCh();
        chargerLesTraductions.affPourcentageTraduit();
        finCh("affPourcentageTraduit");
      }else if(args[0].equals("son")){
        System.out.println(Musique.getMusiqueAlleatoire());
      }else if(args[0].equals("op")){
        chargerLesTraductions.iniTLangue();
        op = chargerLesOptions.chargerLesOptions();
        op.sauvegarder();
      }else if(args[0].equals("supprimer")){
        //diff.nbrDeLigneDiff("usuel/GString.java","../Formiko108/usuel/GString.java");
        if(args.length == 4){
          String s = args[1];
          //c'est pas nésséssaire sur le terminal linux mais au cas ou
          if(s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'){
            s = s.substring(1,s.length()-1);
          }
          String f = args[2];
          byte x = str.sToBy(args[3]);
          modificationDeFichier.retirerLesLignesR(s,f,x);//3== ca doit etre une ligne complète.
        }else{
          erreur.alerte("arguments de supprimer incorecte");
        }
      }else if(args[0].equals("save")){
        pa = new Partie(0,0,new Carte(new GCase(1,1)),1.0); //nouvelle partie vide.

        pa = getPartieParDéfaut();
        pa.initialisationElément();
        //p.getGj().add(new Joueur())
        //Insecte f = new Insecte();
        //Creature f = pa.getGj().getDébut().getContenu().getFourmiliere().getGc().getDébut().getContenu();
        //ObjetAId oai = new Fourmi(new Fourmiliere(), new Espece(),(byte)0);
        //sauvegarderUnePartie.sauvegarder(f);
        sauvegarderUnePartie.sauvegarder(pa,"testSave");
        Partie p = sauvegarderUnePartie.charger("testSave");
        if(p!=null){
          System.out.println(p);
          sauvegarderUnePartie.sauvegarder(p,"testSave2");
        }else{
          System.out.println("partie nulle");
        }
      }else if(args[0].equals("test")){
        test.testAll();
      }else if(args[0].equals("trad2")){
        chargerLesTraductions.iniTLangue();
        chargerLesTraductions.créerLesFichiers();
        map = chargerLesTraductions.chargerLesTraductions(1);//chargement des langues.
        Map<String, String> mapEo = chargerLesTraductions.chargerLesTraductions(0);//chargement des langues.
        trad.copieTradBase("eo",mapEo);
        //chargerLesTraductions.ajouterTradAuto();
      }else{
        erreur.erreur("Votre options a "+(args.length)+" agruments n'as pas été reconnue");
      }
      quitter();
    }else{
      launch();
    }
  }
  /**
   * {@summary Launch in the void main if there is not other args than -something (ex : -d).<br>}
   * @version 1.1
   */
  public static void launch(){
    //premierePartie=true;
    if(premierePartie){tuto=true;}
    avancementChargement=-1;
    //on initialise ici si ça n'as pas déja été fait par une options.
    if(getOp()==null){initialisation();}
    avancementChargement=0;
    ecouteClavier=true;

    débutCh();
    pa = new Partie(0,0,new Carte(new GCase(1,1)),1.0); //nouvelle partie vide.
    finCh("chargementPartieEtCarteBlanche");débutCh();
    f = new Fenetre(); //f.requestFocus(); doit permettre a la fenetre d'écouter les touches.
    finCh("chargementFenetre");débutCh();
    ini.initialiserToutLesPaneauxVide();
    finCh("chargementPanneauVide");débutCh();
    if(getChargementPendantLesMenu()){chargementDesGraphismesAutonomes();}
    else{ini.initialiserPanneauJeuEtDépendance();ini.initialiserAutreELémentTournés();}
    finCh("chargementDesGraphismesAutonomes");
    attenteDeLancementDePartie();
    if(retournerAuMenu){
      retourAuMenu();
    }
  }
  /**
   * {@summary Wait until player launch a new game, the tutorial or Load a game.<br>}
   * @version 1.1
   */
  public static void attenteDeLancementDePartie(){
    //menu
    débutCh();
    getPm().construitPanneauMenu(3);
    finCh("chargementPanneauMenu");
    //attente
    debug.débogage("attente de lancement de la partie");
    Main.repaint();
    boolean b=false;
    while(!b && !premierePartie){Temps.pause(10);b=getPm().getLancer();}
    pa = getPm().getPartie();
    debug.débogage("lancementNouvellePartie");
    lancementNouvellePartie(pa);
  }
  /**
   * {@summary Launch a new game.<br>}
   * @param p A Partie that contain all the game value.
   * @version 1.1
   */
  public static void lancementNouvellePartie(Partie p){ //Nouveau système de lancement de partie :
    débutCh();
    getPp().removePm();//on retire le menu
    getPj().addPch();//on met le panneau de chargement au 1a plan.
    finCh("chargementPanneauChargementEtSuppressionMenu");//débutCh();
    if(premierePartie){tuto=true;}
    if(tuto){pa=getPartieTuto();}
    else if(p==null){pa=getPartieParDéfaut();}
    if(Main.getDimY()!=1080 || getPartie().getGc().getNbrY()!=9){
      getPj().dézoomer((byte)2);//on met la carte a la taille la plus grande possible pour qu'on voit tout.
    }
    //finCh("chargementDézoom");
    pa.setEnCours(true); //lance l'affichage de la Partie.
    //débutCh();
    pa.initialisationElément(); // pour l'instant ce bout de code ne marche pas ayeur qu'ici.
    débutCh();
    Main.getPb().addPz();
    finCh("ajoutPanneauZoom");débutCh();
    getPc().chargerImages(); //on a besoin du bon zoom pour effectuer cette action.
    finCh("chargementImagesDelaCarte");
    String s = g.get("chargementFini");
    if (debug.getAffLesPerformances()==true){s=s +" "+ "("+Temps.msToS(lonTotal)+")";}
    setMessageChargement(s);
    getPch().addBt();
    //affichageDeLaPageDeChargement
    boolean b=!op.getAttendreAprèsLeChargementDeLaCarte();
    if(premierePartie){b=true;}
    while(!b){Temps.pause(10);b=getPch().getLancer();}
    getPj().removePch();
    getPs().construire();
    getGj().prendreEnCompteLaDifficulté();
    if(premierePartie){tuto=true;}
    if(tuto){iniParamètreCarteTuto();}
    else{//si ce n'est pas le tuto on change la musique.
      //thm.stop();
      thm.stopThm();
      thm = new ThMusique();
      thm.start();
    }
    pa.jeu(); //lance le jeux.
  }
  /**
   * Load the default Partie.
   * @version 1.1
   */
  public static Partie getPartieParDéfaut(){
    débutCh();
    String nomCarte = "miniMonde";
    Carte mapo = new Carte(chargerCarte.chargerCarte(nomCarte));
    mapo.setCasesSombres(false);mapo.setCasesNuageuses(false);
    Partie par = new Partie(1,100,mapo,1.0);
    par.setElément(1,5,1);
    par.setVitesseDeJeu(0.2);
    finCh("chargementParamètrePartieParDéfaut");
    return par;
  }
  /**
   * {@summary create a new Partie to launch Tuto.<br>}
   * @version 1.1.
   */
  public static Partie getPartieTuto(){
    débutCh();
    String nomCarte = "tuto";
    Carte mapo = new Carte(chargerCarte.chargerCarte(nomCarte));
    mapo.setCasesSombres(false);mapo.setCasesNuageuses(false);
    Partie par = new Partie(1,5,mapo,1.0);
    par.setElément(1,0,1);
    par.setVitesseDeJeu(0.2);
    finCh("chargementParamètrePartieTuto");
    par.setAppartionInsecte(false);
    par.setAppartionGraine(false);
    return par;
  }
  /**
   * {@summary Initializes the tutorial parameters.<br>}
   * @version 1.1
   */
  public static void iniParamètreCarteTuto(){
    Fourmiliere fere = Main.getGj().getDébut().getContenu().getFere();
    CCase ccIni = Main.getPartie().getGc().getCCase(0,1);
    fere.setCc(ccIni);
    fere.getGc().getDébut().getContenu().setCCase(ccIni);
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(1,1),0,100,0);
    i.setNourritureFournie(200);
    i.setEstMort(false);
    i.setType(8);
    getGi().ajouterInsecte(i);
    ths = new ThScript("data/tuto.formiko");
    ths.start();
  }
  /**
   * Allow the player to get back to main menu.
   * @version 1.1
   */
  //TODO #9 s'arranger pour que ca marche.
  public static void retourAuMenu(){
    Carte mapo = new Carte(new GCase(1,1));
    pa = new Partie(0,0,mapo,1.0); //nouvelle partie vide.
    debug.débogage("lancement du retour au menu");
    Main.getPp().removePj();
    //Main.getPp().removePm();
    //repaint();
    ini.initialiserToutLesPaneauxVide();
    ini.initialiserPanneauJeuEtDépendance();
    getPm().construitPanneauMenu(3);
    repaint();
    debug.débogage("fin du retour au menu");
    attenteDeLancementDePartie();
  }

  // GET SET ----------------------------------------------------------
  public static byte getNiveauDeDétailDeLAffichage(){return niveauDeDétailDeLAffichage;}
  public static int getVersionActuelle(){return versionActuelle;}
  public static Espece getEspece(){return getEspeceParId(0);}
  public static Espece getEspeceParId(int id){ return getGe().getEspeceParId(id);}
  public static GEspece getGEspece(){ return getGe();}
  public static Joueur getJoueurParId(int id){ return Main.getGj().getJoueurParId(id);}
  public static Fourmiliere getFourmiliereParId(int id){ return getJoueurParId(id).getFere();}
  public static Fenetre getF(){ return f;}
  public static Options getOp(){return op;}
  public static Chrono getCh(){ return ch;}
  public static String getMap(String clé){ return map.get(clé);}
  public static int getKey(String clé){ int r = key.get(clé);if(r==-1){return -1;}return r; }
  public static Partie getPartie(){ return pa;}
  public static void setPartie(Partie p){pa=p;}
  public static Pixel getPiFond(){ return pi;}
  public static boolean getEcouteClavier(){ return ecouteClavier;}
  public static void setEcouteClavier(Boolean b){ecouteClavier=b;}
  public static int getAvancementChargement(){ return avancementChargement;}
  public static void setAvancementChargement(int x){avancementChargement=x;}
  public static boolean getAffGraine(){return affGraine;}
  public static Temps getTemps(){ return tem;}
  public static boolean getRetournerAuMenu(){return retournerAuMenu;}
  public static void setRetournerAuMenu(boolean b){retournerAuMenu=b;}
  public static Os getOs(){return os;}
  public static void setOs(Os o){os=o;}
  public static void setTuto(boolean b){tuto=b;}
  public static boolean getPremierePartie(){return premierePartie;}
  public static void setPremierePartie(boolean b){premierePartie=b;}
  //racourci
  public static boolean estWindows(){return os.getId()==1;}
  public static String get(String clé){ return g.get(clé);}
  public static Script getScript(){return ths.getScript();}
  //musique
  public static ThMusique getThm(){return thm;}
  public static Musique getMusique(){return getThm().getM();}
  public static void setMusique(Musique m){getThm().setM(m);}
  public static void setMusiqueSuivante(){getThm().setM();}
  //graphique
  public static PanneauPrincipal getPp(){ return f.getPp();}
  public static synchronized void repaint(){f.repaint();}
  public static synchronized void repaintParciel(Case c){getPc().repaintParciel(c);}
  public static PanneauJeu getPj(){ return getPp().getPj();}
  public static PanneauMenu getPm(){ return getPp().getPm();}
  public static PanneauNouvellePartie getPnp(){ return getPm().getPnp();}
  public static PanneauBouton getPb(){ return getPj().getPb();}
  public static PanneauCarte getPc(){ return getPj().getPc();}
  public static PanneauInfo getPi(){ return getPb().getPi();}
  public static PanneauZoom getPz(){ return getPb().getPz();}
  public static PanneauAction getPa(){ return getPb().getPa();}public static PanneauAction getPac(){return getPa();}
  public static Fourmi getFActuelle(){ return getPj().getFActuelle();}
  public static PanneauChargement getPch(){ return getPj().getPch();}
  public static PanneauSup getPs(){ return getPj().getPs();}
  public static PanneauEchap getPe(){ return getPj().getPe();}
  public static PanneauDialogue getPd(){ return getPj().getPd();}
  public static PanneauDialogueInf getPdi(){ return getPj().getPdi();}
  public static int getDimX(){ try {return getPp().getWidth();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getDimY(){ try {return getPp().getHeight();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getWidth(){return getDimX();}
  public static int getHeight(){return getDimY();}
  public static int getTailleElementGraphique(int x){ return math.min(getTailleElementGraphiqueY(x),getTailleElementGraphiqueX(x));}
  public static int getTailleElementGraphiqueX(int x){ return (x*getDimX())/1920;}
  public static int getTailleElementGraphiqueY(int x){ return (x*getDimY())/1080;}
  public static double getRacioEspaceLibre(){return 900.0/1080.0;}
  public static int getDimXCarte(){System.out.println(getRacioEspaceLibre());return (int)(1920.0*getRacioEspaceLibre());}
  //options
  public static byte getLangue(){ return op.getLangue();}
  public static void setLangue(int x){ op.setLangue(x);iniLangue();}
  public static int getTailleBoutonZoom(){return op.getTailleBoutonZoom();}
  public static int getTailleBoutonAction(){return op.getTailleBoutonAction();}
  public static int getTailleBoutonTX(){return op.getTailleBoutonTX();}
  public static boolean getMouvementRapide(){ return op.getMouvementRapide();}
  public static void setMouvementRapide(boolean b){ op.setMouvementRapide(b);}
  public static int getNbrMessageAfficher(){ return op.getNbrMessageAfficher();}
  public static boolean getDessinerGrille(){ return op.getDessinerGrille();}
  public static boolean getElementSurCarteOrientéAprèsDéplacement(){ return op.getElementSurCarteOrientéAprèsDéplacement();}
  public static boolean getForcerQuitter(){ return op.getForcerQuitter();}
  public static byte getBordureBouton(){ return op.getBordureBouton();}
  public static boolean getDessinerIcone(){return op.getDessinerIcone();}
  public static Font getFont1(){ return op.getFont1();}
  public static Font getFont1(double d){ return op.getFont1(d);}
  public static void setFont1(Font f){ op.setFont1(f);}
  public static Font getFont2(){ return op.getFont2();}
  public static void setFont2(Font f){ op.setFont2(f);}
  public static int getTaillePolice1(){ return op.getTaillePolice1();}
  public static int getTaillePolice2(){ return op.getTaillePolice2();}
  public static boolean getPleinEcran(){ return op.getPleinEcran();}
  public static boolean getChargementPendantLesMenu(){ return op.getChargementPendantLesMenu();}
  public static boolean getGarderLesGraphismesTourné(){ return op.getGarderLesGraphismesTourné();}
  public static int getDimLigne(){return op.getDimLigne();}
  public static int getPositionCase(){return op.getPositionCase();}
  public static byte getTailleRealiste(){return op.getTailleRealiste();}
  //partie
  public static GInsecte getGi(){return pa.getGi();}
  public static GJoueur getListeJoueur(){return pa.getGj();}
  public static GJoueur getGj(){return pa.getGj();}
  public static GCase getGc(){return pa.getGc();}
  public static void setNbrDeTour(int x){pa.setNbrDeTour(x);}
  public static void setTour(int x){pa.setTour(x);}
  public static int getNbrDeTour(){return pa.getNbrDeTour();}
  public static int getTour(){return pa.getTour();}
  public static byte getDifficulté(){return pa.getDifficulté();}
  public static void setDifficulté(byte x){pa.setDifficulté(x);}
  public static LocalDateTime getDateDeCréation(){return pa.getDateDeCréation();}
  public static int [] getTableauDesEspecesAutorisée(){ return pa.getTableauDesEspecesAutorisée();}
  public static int getNbrDeJoueur(){ return pa.getNbrDeJoueurDansLaPartie();}
  public static Carte getCarte(){ return pa.getCarte();}
  public static double getVitesseDeJeu(){return pa.getVitesseDeJeu();}
  public static GEspece getGe(){return pa.getGe();}
  //ini
  public static void initialiserElémentTournés(){ ini.initialiserElémentTournés();}
  public static void initialiserAutreELémentTournés(){ ini.initialiserAutreELémentTournés();}
  // Fonctions propre -------------------------------------------------
  /**
   * Initializes Options, key, language, time data, musique, os value. And check the integrity of the file tree.
   * @version 1.1
   */
  public static void initialisation(){
    ch = new Chrono();
    tempsDeDébutDeJeu=System.currentTimeMillis();
    os = new Os();
    setMessageChargement("vérificationsDeLArborécence");débutCh();
    if(!arbo.arborécenceIntacte()){arbo.réparationArboréscence();}
    finCh("vérificationsDeLArborécence");
    setMessageChargement("chargementDesOptions");débutCh();
    chargerLesTraductions.iniTLangue();
    iniOp();
    if(!debug.getAffLesEtapesDeRésolution()){//si elle n'ont pas été activé par "-d"
      debug.setAffLesEtapesDeRésolution(op.getAffLesEtapesDeRésolution());
    }
    debug.setAffLesPerformances(op.getAffLesPerformances());
    debug.setAffG(op.getAffG());
    finCh("chargementDesOptions");
    setMessageChargement("chargementDesTouches");débutCh();
    key = chargerLesTouches.chargerLesTouches();
    finCh("chargementDesTouches");
    setMessageChargement("chargementDesLangues");
    iniLangue();
    trich = new ThTriche();//écoute des codes triche.
    trich.start();
    débutCh();
    tem = new Temps();
    finCh("chargementDesDonnéesTemporelles");
    setMessageChargement("chargementDesEspeceDeFourmi");débutCh();
    Partie.iniGe(); // chargement des Especes.
    GIndividu.chargerLesIndividus(); // chargement de leur individu.
    finCh("chargementDesIndividuDeFourmi");débutCh();
    Insecte.setGie(); // chargement des Insectes.
    finCh("chargementDesEspeceDInsecte");débutCh();
    thm = new ThMusique("menu");
    thm.start();
    finCh("chargementDeLaMusique");débutCh();
    File f = new File(image.REP+"ressourcesPack");
    String listl [] = f.list();
    if(listl.length!=0){
      image.setREPTEXTUREPACK(image.REP+"ressourcesPack");//on déclare le ressourcesPack si il contient au moins 1 image.
    }
    finCh("initialisationDeREPTEXTUREPACK");
    //System.out.println("Os reconnu : "+os);
  }
  /**
   * Load graphics during menu time.
   * @version 1.1
   */
  public static void chargementDesGraphismesAutonomes(){
    Th thTemp = new Th(1);
    thTemp.start();
    Th thTemp2 = new Th(2);
    thTemp2.start();
  }
  /**
   * Load Options.
   * @version 1.1
   */
  public static void iniOp(){
    op = chargerLesOptions.chargerLesOptions();
  }
  /**
   * Load language.
   * @version 1.1
   */
  public static void iniLangue(){
    débutCh();
    map = chargerLesTraductions.chargerLesTraductions(getLangue());//chargement des langues.
    finCh("chargementDesLangues");
  }
  /**
   * {@summary Print on the window a message about game loading.}
   * If you tried to use it before the creating of a new PanneauChargement, mail will not appear on the window.
   * @version 1.1
   */
  public static void setMessageChargement(String s){
    //s c'est un truc du genre "chargementDesLangues"
    String s2 = g.getM(s)+"..."; //g.getM() permet d'aller chercher la traduction dans la table de hachage Map<String, String> map.
    try {
      getPch().setTexte(s2); //envoie a la fenetre le message d'avancement du chargement.
    }catch (Exception e) { // si quelque chose ce passe mal on envoie un message a la console.
      //erreur.alerte("Un message de chargement n'est pas arrivé a destination.");
    }
  }
  /**
   * Sould transforme a GCase to a Image that can be used for mini-map.<br>
   * @version 1.1
   */
  public static void gcToImage(){
    débutCh();
    Carte mapo = new Carte(chargerCarte.chargerCarte("miniMonde"));
    GCase gc = mapo.getGc();
    finCh("chargementCarteEtGc");débutCh();
    Img img = gc.getImg();
    finCh("récupérationDeLImage");débutCh();
    img.sauvegarder("carte.png");
    finCh("sauvegardeLeLImage");
    //debug.setAffLesEtapesDeRésolution(false);
  }
  public static void débutCh(){débutCh(ch);}
  public static void finCh(String s){finCh(s,ch);}
  /**
   * Start Chrono
   * @version 1.1
   */
  public static void débutCh(Chrono chTemp){ //début du Chrono.
    //if(chTemp == null){chTemp = new Chrono();}
    if(!debug.getAffLesPerformances()){ return;}
    chTemp.start();
  }
  /**
   * {@summary Stop Chrono and print a message about Chrono duration.<br>}
   * The message will be print in console only if debug.setAffLesPerformances is true.<br>
   * @version 1.1
   */
  public static void finCh(String s,Chrono chTemp){ // fin du Chrono.
    if(!debug.getAffLesPerformances()){ return;}
    String s2 = g.getM(s);
    if (s2.length()!=0){ s=s2;}
    chTemp.stop();lon = chTemp.getDuree(); lonTotal=lonTotal+lon;
    String s3 = ""; if(!chTemp.equals(ch)){s3 = " ("+g.get("actionSecondaire")+" "+ch.getId()+")";}
    debug.performances("temps pour "+ s + " : "+lon+" ms"+s3); //affichage du chrono.
  }
  /**
   * {@summary Try to exit normally.<br>}
   * Save score informations.<br>
   * Clear temporary Images.<br>
   * Save time played.<br>
   * Stop java with code 0.<br>
   * If something went wrong stop java with code 1.<br>
   * @version 1.1
   */
  public static void quitter(){
    try {
      débutCh();
      pa.enregistrerLesScores();
      finCh("enregistementDesScores");
      débutCh();
      if(getGarderLesGraphismesTourné()){image.clearPartielTemporaire();}
      else{image.clearTemporaire();}
      finCh("vidageDesFichiersImages");
      String s = "toutes les opérations longues ";
      debug.performances("temps pour "+ s + " : "+lonTotal+" ms");
      long tempsDeFinDeJeu=System.currentTimeMillis();
      long tempsJeuEcoulé = tempsDeFinDeJeu-tempsDeDébutDeJeu;
      System.out.println(g.getM("tempsJeuEcoulé")+" : "+ch.timeToHMS((tempsJeuEcoulé)/1000)+".");
      //System.out.println("\ud83d\ude00");//System.out.println("😀");
      tem.addTempsEnJeux(tempsJeuEcoulé);tem.actualiserDate2();tem.sauvegarder();
      System.out.println(g.getM("messageQuitter"));
      System.exit(0);
    }catch (Exception e) {
      System.exit(1); //une erreur a la fermeture.
    }
  }
  /**
   * {@summary Play a turn.<br>}
   * 1a updating Case resources.<br>
   * 2a Make humain player and AI play.<br>
   * 3a Make Insecte play.<br>
   * 4a Add new Insectes.<br>
   * @version 1.1
   */
  public static void tour(){
    getGc().tourCases(); //actualisation des ressources sur les cases.
    getGj().jouer();
    getGi().tourInsecte();
    if(Main.getPartie().getAppartionInsecte()){
      int nbrDInsecteRestant = math.max( getGc().getNbrDeCase()/5 -  getGi().getGiVivant().length(),0);
      int x2 = math.min( getGc().getNbrDeCase()/20, nbrDInsecteRestant);
      new Message("Ajout de "+x2+" insectes");
      getGi().ajouterInsecte((x2*9)/10); //les insectes vivants n'apparaissent pas sur des cases déja occupé.
      getGi().ajouterInsecte(x2/10);
    }
  }
}