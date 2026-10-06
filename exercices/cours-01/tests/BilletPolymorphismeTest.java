package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class BilletPolymorphismeTest {

  @Test
  void le_prix_depend_du_type_reel_du_billet() {
    Concert concert = new Concert("Nuit Électro", 500);
    List<Billet> billets = List.of(
        new BilletStandard(concert, 5000),
        new BilletEtudiant(concert, 5000),
        new BilletVIP(concert, 5000));

    int total = 0;
    for (Billet billet : billets) {
      total += billet.prix();
    }
    assertEquals(16000, total);
  }
}