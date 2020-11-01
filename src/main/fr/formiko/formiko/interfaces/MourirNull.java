package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary A null implementation.<br/>}
 * It print an error for all the methode of implemented class.<br/>
 * It allow to avoid error of null implementation.<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class MourirNull implements Serializable, Mourir {

  public void mourir(Creature c, int x){
    erreur.erreur("Impossible de mourir avec la créature " + c.getId());
  }
  public void supprimerDeLaCarte(Creature c){
    erreur.erreur("Impossible de supprimerDeLaCarte la créature " + c.getId());
  }
}