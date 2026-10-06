package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BilletVIPTest {

  @Test
  void un_billet_vip_coute_une_fois_et_demie_le_prix_de_base() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(7500, new BilletVIP(concert, 5000).prix());
  }

  @Test
  void le_prix_est_arrondi_au_centime_inferieur() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(4999, new BilletVIP(concert, 3333).prix(),
        "33,33 € × 1,5 = 49,995 € : arrondi au centime inférieur, 49,99 €");
  }
}