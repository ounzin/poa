package fr.poa.td1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BilletTest {

  @Test
  void billet_est_un_record() {
    assertTrue(Billet.class.isRecord());
  }

  @Test
  void un_billet_garde_ses_valeurs() {
    Billet billet = new Billet("Nuit Électro", "Alice", 45);
    assertEquals("Nuit Électro", billet.concert());
    assertEquals("Alice", billet.spectateur());
    assertEquals(45, billet.prix());
  }

  @Test
  void un_concert_ou_un_spectateur_vide_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Billet("", "Alice", 45));
    assertThrows(IllegalArgumentException.class, () -> new Billet("Nuit Électro", "  ", 45));
  }

  @Test
  void un_prix_negatif_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Billet("Nuit Électro", "Alice", -1));
  }

  @Test
  void une_invitation_coute_zero() {
    assertDoesNotThrow(() -> new Billet("Nuit Électro", "Alice", 0));
  }
}
