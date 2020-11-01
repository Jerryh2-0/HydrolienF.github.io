package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanneauChamp extends Panneau {
  private Champ c;
  private BoutonV b;
  private int tailleBouton=20;
  private boolean validé=false;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauChamp(String défaut){
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    Dimension dimC = new Dimension(500,tailleBouton);
    Dimension dimB = new Dimension(tailleBouton*2,tailleBouton);
    c = new Champ(défaut);
    b = new BoutonV();
    c.setPreferredSize(dimC); b.setPreferredSize(dimB);
    gbc.gridy = 0; gbc.gridx = 0;
    this.add(c,gbc);
    gbc.gridx = 1;
    this.add(b,gbc);
  }
  public PanneauChamp(){ this("");}
  // GET SET --------------------------------------------------------------------
  public boolean getValidé(){ return validé;}
  public String getTexte(){ return c.getText();}
  // Fonctions propre -----------------------------------------------------------


  class BoutonV extends JButton implements MouseListener{
    public BoutonV(){
      super("OK");
      this.addMouseListener(this);
    }
    //Méthode appelée lors du clic de souris
    public void mouseClicked(MouseEvent event) {
      debug.débogage("Un bouton a été cliqué, l'action \"validé\" vas être effectué.");
      validé=true;
    }
    //Méthode appelée lors du survol de la souris
    public void mouseEntered(MouseEvent event) { }

    //Méthode appelée lorsque la souris sort de la zone du bouton
    public void mouseExited(MouseEvent event) { }

    //Méthode appelée lorsque l'on presse le bouton gauche de la souris
    public void mousePressed(MouseEvent event) { }

    //Méthode appelée lorsque l'on relâche le clic de souris
    public void mouseReleased(MouseEvent event) { }
  }

}