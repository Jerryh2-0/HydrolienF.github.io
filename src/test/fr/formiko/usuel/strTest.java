package fr.formiko.usuel.conversiondetype;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import fr.formiko.usuel.Os;

public class strTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testNbrDeX(){
    String s = "est un E iueé";//la maj et le 'é' ne doivent pas compter
    assertEquals(2,str.nbrDeX(s,'e'));
    s = "一个小姜饼人一";
    assertEquals(2,str.nbrDeX(s,'一'));
    assertEquals(0,str.nbrDeX(s,' '));
  }
  @Test
  public void testContient(){
    assertTrue(str.contient("une grande chaine de char","chaine"));
    assertTrue(str.contient("une grande chaine de char","e c"));
    assertTrue(str.contient("une grande chaine de char","chaine",1));
    assertTrue(!str.contient("une grande chaine de char","chaine",0));
    assertTrue(!str.contient("une grande chaine de char","chaine",2));
    assertTrue(!str.contient("une grande chaine de char","chaine",3));
    assertTrue(!str.contient("une grande chaine de char","cp",1));

    assertTrue(str.contient("une grande chaine de char","u",0));
    assertTrue(!str.contient("une grande chaine de char","U",0));
    assertTrue(str.contient("une grande chaine de char","une",0));
    assertTrue(!str.contient("une grande chaine de char","Une",0));

    assertTrue(str.contient("une grande chaine de char","r",2));
    assertTrue(!str.contient("une grande chaine de char","R",2));
    assertTrue(str.contient("une grande chaine de char"," char",2));
    assertTrue(!str.contient("une grande chaine de char","  char",2));

    assertTrue(str.contient("une grande chaine de char","une grande chaine de char",3));
    assertTrue(!str.contient("une grande chaine de char","une grande chaine de chart",3));
  }
  @Test
  public void testSToI(){
    assertEquals(2,str.sToI("2"));
    assertEquals(0,str.sToI("0"));
    assertEquals(2200,str.sToI("2200"));
    assertEquals(-3,str.sToI("-3"));
    assertEquals(2678,str.sToI("2678 "));
    assertEquals(2678,str.sToI("2 678"));
    assertEquals(2678,str.sToI("    2 678"));

    assertEquals(-1,str.sToI(""));
    assertEquals(-1,str.sToI("8tte"));
    assertEquals(-1,str.sToI("8 e"));
    assertEquals(-1,str.sToI("--8"));
    assertEquals(-1,str.sToI("-8-45"));
  }
  @Test
  public void testSToLThrows(){
    try {
      assertEquals(2,str.sToLThrows("2"));
      assertEquals(21,str.sToLThrows("2   1"));
    }catch (Exception e) {

    }
    //TODO ce débrouiller pour que assertThrows marche (probablement avec junit 5 et son sous module Jupiter)
    //assertThrows(Exception.class,str.sToLThrows("t4"));
  }
  @Test
  public void testFiltreCharInterdit(){
    String s = "wexrtcyuié&'-\\^é&('-è')'*$\"";
    char t [] = {'é'};
    assertEquals("wexrtcyui&'-\\^&('-è')'*$\"",str.filtreCharInterdit(s,t));
    char t2 [] = {'\"'};
    assertEquals("wexrtcyuié&'-\\^é&('-è')'*$",str.filtreCharInterdit(s,t2));
    char t3 [] = {};
    assertEquals("wexrtcyuié&'-\\^é&('-è')'*$\"",str.filtreCharInterdit(s,t3));
    char t4 [] = {'w','x'};
    assertEquals("ertcyuié&'-\\^é&('-è')'*$\"",str.filtreCharInterdit(s,t4));
  }
  @Test
  public void testFiltreCharInterditInFile(){
    Main.setOs(new Os());
    String s = "unNomDeFichier";
    Main.getOs().setId((byte)0);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));
    Main.getOs().setId((byte)2);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));
    Main.getOs().setId((byte)-1);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));

    s = "unNomDeFichier?";
    Main.getOs().setId((byte)0);
    assertEquals("unNomDeFichier?",str.filtreCharInterdit(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));

    s = "unNomDe/Fichier";
    Main.getOs().setId((byte)0);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeFichier",str.filtreCharInterdit(s));

    s = "unNomDe/F*^ich|er";
    Main.getOs().setId((byte)0);
    assertEquals("unNomDeF*^ich|er",str.filtreCharInterdit(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeF^icher",str.filtreCharInterdit(s));

  }

  @Test
  public static void testSToDirectoryName(){
    String s = "unNomDeFichier";
    assertEquals("unNomDeFichier/",str.sToDirectoryName(s));
    s = "unNomDeFichier/";
    assertEquals("unNomDeFichier/",str.sToDirectoryName(s));
    s = "unNomD/eFichier";
    assertEquals("unNomD/eFichier/",str.sToDirectoryName(s));
    s = "unNomD/eFichier/";
    assertEquals("unNomD/eFichier/",str.sToDirectoryName(s));
    s = "unNomD/*?eFichier";
    assertEquals("unNomD/eFichier/",str.sToDirectoryName(s));
    s = "un!!??NomD/eFichier";
    assertEquals("un!!NomD/eFichier/",str.sToDirectoryName(s));
  }
}