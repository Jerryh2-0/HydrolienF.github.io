package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to died<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class MourirFourmi implements Serializable, Mourir {
  public void mourir(Creature c, int r){
    c.setEstMort(true);
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi)c;
      Message.messageMort(f,r);
      supprimerDeLaCarte(f);
      if (f.getStade() != 0){
        // TODO les oeuf ou les larves (ou les nymphes) sont mangeable par les autres fourmis.
        //f.getCCase().getContenu().getGc().ajouter(new Insecte(f.getCCase(),10 + (int) getNourriture()/3,0,0,0));
      }
    }else{
      erreur.erreurType("Fourmi","MourirFourmi.Mourir");
    }
  }
  public void supprimerDeLaCarte(Creature c){
    Fourmi f = (Fourmi)c;
    supprimerDeLaCarte(f);
  }
  private void supprimerDeLaCarte(Fourmi f){
    f.getFere().nbrFourmisMortePlus1();
    f.getCCase().getContenu().getGc().retirer(f);
    f.getFere().getGc().retirer(f);
  }
}