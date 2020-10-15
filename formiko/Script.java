package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.Temps;

public class Script {
  private boolean cmdSuivante;
  private boolean ecouteClic;
  private String s;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Script(String s){
    this.s=s;
  }
  // GET SET --------------------------------------------------------------------
  public boolean getCmdSuivante(){return cmdSuivante;}
  public void setCmdSuivante(boolean b){cmdSuivante=b;}
  public boolean getEcouteClic(){return ecouteClic;}
  public void setEcouteClic(boolean b){ecouteClic=b;}
  // Fonctions propre -----------------------------------------------------------
  public void script(){
    String t [] = chargerScript();
    executerScript(t);
  }
  private String [] chargerScript(){
    String t [] = s.split(".");
    if(t.length > 0 && !t[t.length-1].equals("formiko")){//on ajoute la terminaison du fichier si besoin.
      s=s+".formiko";
    }
    String ts [] = lireUnFichier.lireUnFichier(s);
    return ts;
  }
  private void executerScript(String t[]){
    debug.débogage("Lancement d'1 script a "+t.length+" commandes");
    setCmdSuivante(true);
    setEcouteClic(true);
    for (String s : t ) {
      while(!cmdSuivante){Temps.pause(10);}
      setEcouteClic(true);//au cas ou la commande juste avant ai désactivé l'écoute clic.
      debug.débogage("Commande : "+s);
      try {
        triche.commande(s);
      }catch (Exception e) {
        erreur.alerte("la commande suivant n'as pas été reconnue : "+s,"script.executerScript");
      }
    }
  }
}