package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PrixOperationsTest {

  @Test
  void plus_additionne_deux_prix() {
    assertEquals(new Prix(7500), new Prix(5000).plus(new Prix(2500)));
  }

  @Test
  void plus_ne_modifie_aucun_des_deux_prix() {
    Prix a = new Prix(5000);
    Prix b = new Prix(2500);
    a.plus(b);
    assertEquals(new Prix(5000), a);
    assertEquals(new Prix(2500), b);
  }

  @Test
  void zero_est_neutre_pour_l_addition() {
    assertEquals(0, Prix.ZERO.centimes());
    assertEquals(new Prix(5000), Prix.ZERO.plus(new Prix(5000)));
  }

  @Test
  void pourcentage_arrondit_au_centime_inferieur() {
    assertEquals(new Prix(2335), new Prix(3337).pourcentage(70),
        "33,37 € × 70 % = 23,359 € : arrondi au centime inférieur, 23,35 €");
    assertEquals(new Prix(4999), new Prix(3333).pourcentage(150),
        "33,33 € × 150 % = 49,995 € : arrondi au centime inférieur, 49,99 €");
  }

  @Test
  void un_pourcentage_negatif_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Prix(5000).pourcentage(-10));
  }
}