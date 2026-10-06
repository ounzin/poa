package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class BilletStandardTest {

  @Test
  void billet_est_une_classe_abstraite() {
    assertTrue(Modifier.isAbstract(Billet.class.getModifiers()));
  }

  @Test
  void le_constructeur_de_billet_est_protege() throws NoSuchMethodException {
    int modificateurs = Billet.class.getDeclaredConstructor(Concert.class, int.class).getModifiers();
    assertTrue(Modifier.isProtected(modificateurs));
  }

  @Test
  void un_billet_standard_est_un_billet() {
    Concert concert = new Concert("Nuit Électro", 500);
    Billet billet = new BilletStandard(concert, 5000);
    assertSame(concert, billet.concert());
    assertEquals(5000, billet.prixBase());
  }

  @Test
  void le_prix_standard_est_le_prix_de_base() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(5000, new BilletStandard(concert, 5000).prix());
  }
}