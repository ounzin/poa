package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MontantTest {

  @Test
  void montant_est_un_record() {
    assertTrue(Montant.class.isRecord());
  }

  @Test
  void un_montant_garde_ses_centimes() {
    assertEquals(1250, new Montant(1250).centimes());
  }

  @Test
  void un_montant_negatif_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Montant(-1));
  }

  @Test
  void zero_vaut_zero_centime() {
    assertEquals(0, Montant.ZERO.centimes());
  }

  @Test
  void plus_additionne_sans_modifier_les_montants() {
    Montant a = new Montant(1250);
    Montant b = new Montant(750);
    assertEquals(new Montant(2000), a.plus(b));
    assertEquals(new Montant(1250), a);
    assertEquals(new Montant(750), b);
  }

  @Test
  void to_string_affiche_des_euros() {
    assertEquals("12,50 €", new Montant(1250).toString());
  }
}