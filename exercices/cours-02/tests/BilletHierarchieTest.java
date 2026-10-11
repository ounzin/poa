package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class BilletHierarchieTest {

  @Test
  void billet_est_une_interface_scellee() {
    assertTrue(Billet.class.isInterface(), "Billet doit être une interface");
    assertTrue(Billet.class.isSealed(), "Billet doit être scellée (sealed)");
  }

  @Test
  void les_quatre_types_de_billets_sont_autorises() {
    List<Class<?>> autorises = List.of(Billet.class.getPermittedSubclasses());
    assertTrue(autorises.containsAll(List.of(
        BilletStandard.class, BilletEtudiant.class, BilletVIP.class, Invitation.class)));
  }

  @Test
  void chaque_type_de_billet_est_un_record() {
    for (Class<?> type : Billet.class.getPermittedSubclasses()) {
      assertTrue(type.isRecord(), type.getSimpleName() + " doit être un record");
    }
  }

  @Test
  void chaque_billet_expose_ses_composants() {
    Prix base = new Prix(5000);
    assertEquals(base, new BilletStandard(base).prixBase());
    assertEquals("12345678", new BilletEtudiant(base, "12345678").numeroEtudiant());
    assertEquals(base, new BilletVIP(base).prixBase());
    assertEquals("Lina Martin", new Invitation("Lina Martin").beneficiaire());
  }

  @Test
  void deux_billets_de_meme_contenu_sont_egaux() {
    assertEquals(new BilletVIP(new Prix(5000)), new BilletVIP(new Prix(5000)));
  }
}