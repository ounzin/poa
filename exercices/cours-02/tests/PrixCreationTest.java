package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PrixCreationTest {

  @Test
  void prix_est_un_record() {
    assertTrue(Prix.class.isRecord());
  }

  @Test
  void un_prix_garde_son_montant_en_centimes() {
    assertEquals(5000, new Prix(5000).centimes());
  }

  @Test
  void un_prix_nul_est_accepte() {
    assertEquals(0, new Prix(0).centimes());
  }

  @Test
  void un_prix_negatif_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Prix(-1));
  }

  @Test
  void deux_prix_de_meme_montant_sont_egaux() {
    Prix a = new Prix(5000);
    Prix b = new Prix(5000);
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }
}