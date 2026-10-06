package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VendableTest {

  @Test
  void vendable_est_une_interface() {
    assertTrue(Vendable.class.isInterface());
  }

  @Test
  void un_tshirt_est_vendable() {
    Vendable tshirt = new TShirt();
    assertEquals(2500, tshirt.prix());
  }

  @Test
  void un_billet_est_vendable() {
    Concert concert = new Concert("Nuit Électro", 500);
    Vendable billet = new BilletEtudiant(concert, 5000);
    assertEquals(3500, billet.prix());
  }
}