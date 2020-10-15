package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.tableau;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import fr.formiko.usuel.liste.GString;

public class lireUnFichier {

  public static String [] lireUnFichier(String nomDuFichier) {
    String tr [] = new String [0];
    try {
      BufferedReader lecteurAvecBuffer = null;
      String ligne;

      try {
        lecteurAvecBuffer = new BufferedReader(new FileReader(nomDuFichier, StandardCharsets.UTF_8));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le chargement du fichier "+ nomDuFichier+" a échoué.");
  	    e.printStackTrace();
      }
      while ((ligne = lecteurAvecBuffer.readLine()) != null){
        tr = tableau.ajouterX(tr, ligne); // La c'est couteux en opération.
      }
      lecteurAvecBuffer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
    debug.débogage(tr.length + " "+g.get("lireUnFichier",1,"lignes on été trouvée dans le fichier"));
    return tr;
  }
  //sert a parcourir un fichier en ajoutant chaque ligne 1 a 1 dans une liste chainé GString.
  public static GString lireUnFichierGs(String nomDuFichier){
    GString gs= new GString();
    try {
      BufferedReader lecteurAvecBuffer = null;
      String ligne;

      try {
        lecteurAvecBuffer = new BufferedReader(new FileReader(nomDuFichier, StandardCharsets.UTF_8));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le chargement du fichier "+ nomDuFichier+" a échoué.");
        e.printStackTrace();
      }
      while ((ligne = lecteurAvecBuffer.readLine()) != null){
        gs.add(ligne);
      }
      lecteurAvecBuffer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
    debug.débogage(gs.length() + " "+g.get("lireUnFichier",1,"lignes on été trouvée dans le fichier"));
    return gs;
  }

}