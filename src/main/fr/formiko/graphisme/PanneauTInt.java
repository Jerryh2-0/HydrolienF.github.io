package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import fr.formiko.usuel.Temps;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Touches;

public class PanneauTInt extends PanneauTX{
  private int ti [];
  private int choix;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauTInt(int ti [],String desc){super();
    addKeyListener(new Touches());
    tailleBouton = Main.getTailleBoutonTX();
    this.descTI = desc;
    debug.débogage("Création d'un PanneauTInt");
    this.setLayout(new GridBagLayout());
    this.ti=ti;
    if(ti!=null){
      int nbrDeCase = ti.length;
      if (nbrDeCase > 0){Main.getPb().setDescTI(descTI);}
      else{((PanneauBouton) pb).setDescTI("");}
      x = ((int) (Math.sqrt(nbrDeCase)))+1;
      y = x;
      //if (nbrDeCase==9 || nbrDeCase==4 || nbrDeCase==16 || nbrDeCase==25){x--;} // petite correction de la ligne 2 au dessus qui ne prend pas ses cas en compte.
      if (x*x > nbrDeCase+x){ // si la dernière ligne n'est pas néssésaire
        y=x-1;
      }
      this.setSize(x*tailleBouton,y*tailleBouton);
      Bouton tB [] = new Bouton [nbrDeCase];
      Dimension dim = new Dimension(tailleBouton,tailleBouton);
      for (int i=0;i<nbrDeCase ;i++ ) {
        tB[i]=new Bouton(ti[i]+"", Main.getPp().getPj(),40+i);
        tB[i].addKeyListener(new Touches());
        tB[i].setFont(Main.getFont2());
      }
      for (Bouton b :tB){b.setPreferredSize(dim);}
      GridBagConstraints gbc = new GridBagConstraints();
      int k=0;
      for (int i=0;i<x ;i++ ) {
        gbc.gridy = i;
        for (int j=0;j<y && k < nbrDeCase;j++ ) {
          gbc.gridx = j;
          this.add(tB[k],gbc);k++;
        }
      }
      debug.débogage(k+" boutons ont été ajoutés");
      //Main.getF().repaint();
    }else{
      debug.débogage("initialisation null de pti.");
    }
  }
  public PanneauTInt(int t[],PanneauBouton pb){this(t,"null");}
  // GET SET --------------------------------------------------------------------
  public int getBoutonX(int x){ if(x > -1 && ti!=null && x < ti.length){return ti[x];}return -1;}
  // Fonctions propre -----------------------------------------------------------
  public static int getChoixId(){
    int id2 = -1;
    debug.débogage("lancement d'une boucle de choix.");
    while(id2==-1){
      id2 = Main.getPp().getPj().getPb().getChoixId();
      Temps.pause(10);
    }
    Main.getPp().getPj().getPb().setChoixId(-1);
    Main.getPp().getPj().setDescTI("");
    Main.getPp().getPj().remove(Main.getPp().getPj().getPti());
    return id2;
  }
}