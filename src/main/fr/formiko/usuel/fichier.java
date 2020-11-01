package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.read;
import fr.formiko.usuel.exception.FichierDejaPresentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class fichier{

  // CONSTRUCTEUR -----------------------------------------------------------------

  // GET SET -----------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public static void affichageDesLecteurALaRacine (File f){
    System.out.println("Affichage des lecteurs à la racine du PC : ");
    for(File file : f.listRoots()){
      System.out.println(file.getAbsolutePath());
      try {
        int i = 1;
        //On parcourt la liste des fichiers et répertoires
        for(File nom : file.listFiles()){
          //S'il s'agit d'un dossier, on ajoute un "/"
          System.out.print("\t\t" + ((nom.isDirectory()) ? nom.getName()+"/" : nom.getName()));

          if((i%4) == 0){
            System.out.print("\n");
          }
          i++;
        }
        System.out.println("\n");
      } catch (NullPointerException e) {
        //L'instruction peut générer une NullPointerException
        //s'il n'y a pas de sous-fichier !
      }
    }
  }
  public static void fichierCiblePeuAvoirCeNom (String nom) throws FichierDejaPresentException {
    File f = new File(nom);
    if (f.exists()) { throw new FichierDejaPresentException (nom);}
  }
  public static void copierUnFichier(String nomDuFichierACopier){
    String nomDuFichierCible = read.getString("Nom du nouveau fichier","Copie de " + nomDuFichierACopier);
    copierUnFichier(nomDuFichierACopier, nomDuFichierCible);
  }
  public static void copierUnFichier(String nomDuFichierACopier, String nomDuFichierCible){
    try {
      fichierCiblePeuAvoirCeNom(nomDuFichierCible);
    } catch (FichierDejaPresentException e){
      e.printStackTrace();
    }
    /*if (f.exists()){ // permet d'éviter : qu'un fichier soit écrabouiller et que le fichier x soit copié dans le fichier x.
      erreur.erreur("Le nom du nouveau fichier existe déjà ! Il ne faudrait pas l'écraser !","fichier.copierUnFichier",true);
    }*/
    // Nous déclarons nos objets en dehors du bloc try/catch
    FileInputStream fis = null;
    FileOutputStream fos = null;

    try {
       // On instancie nos objets :
       // fis va lire le fichier
       // fos va écrire dans le nouveau !
       fis = new FileInputStream(new File(nomDuFichierACopier));
       fos = new FileOutputStream(new File(nomDuFichierCible));

       // On crée un tableau de byte pour indiquer le nombre de bytes lus à chaque tour de boucle
       byte[] buf = new byte[8];

       // On crée une variable de type int pour y affecter le résultat de
       // la lecture (Vaut -1 quand c'est fini)
       int n = 0;

       // Tant que l'affectation dans la variable est possible, on boucle
       // Lorsque la lecture du fichier est terminée l'affectation n'est
       // plus possible ! On sort donc de la boucle
       while ((n = fis.read(buf)) >= 0) {
          // On écrit dans notre 2a fichier avec l'objet adéquat
          fos.write(buf);
          if (debug.getAffLesEtapesDeRésolution()){
          // On affiche ce qu'a lu notre boucle au format byte et au format char
          for (byte bit : buf) {
            System.out.print("\t" + bit + "(" + (char) bit + ")");
          }
          System.out.println("");
          }
          //Nous réinitialisons le buffer à vide
          //au cas où les derniers byte lus ne soient pas un multiple de 8
          //Ceci permet d'avoir un buffer vierge à chaque lecture et ne pas avoir de doublon en fin de fichier
          buf = new byte[8];
       }
       debug.débogage("Copie terminée !");

    } catch (FileNotFoundException e) {
       // Cette exception est levée si l'objet FileInputStream ne trouve aucun fichier
       e.printStackTrace();
    } catch (IOException e) {
       // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
       e.printStackTrace();
    } finally {
       // On ferme nos flux de données dans un bloc finally pour s'assurer
       // que ces instructions seront exécutées dans tous les cas même si
       // une exception est levée !
       try {
          if (fis != null)
             fis.close();
       } catch (IOException e) {
          e.printStackTrace();
       }

       try {
          if (fos != null)
             fos.close();
       } catch (IOException e) {
          e.printStackTrace();
       }
    }
  }
}