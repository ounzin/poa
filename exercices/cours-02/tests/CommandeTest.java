package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CommandeTest {

  private final Concert concert = new Concert("NE-2026", "Nuit Électro", 500);
  private final Billet standard = new BilletStandard(new Prix(5000));

  @Test
  void commande_est_un_record() {
    assertTrue(Commande.class.isRecord());
  }

  @Test
  void le_total_additionne_les_prix_des_billets() {
    Commande commande = new Commande(concert, List.of(
        new BilletStandard(new Prix(5000)),
        new BilletEtudiant(new Prix(5000), "12345678"),
        new Invitation("Lina Martin")));
    assertEquals(new Prix(8500), commande.total());
  }

  @Test
  void une_commande_vide_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> new Commande(concert, List.of()));
  }

  @Test
  void un_billet_null_est_refuse() {
    assertThrows(NullPointerException.class, () -> new Commande(concert, Arrays.asList(standard, null)));
  }

  @Test
  void le_concert_est_obligatoire() {
    assertThrows(NullPointerException.class, () -> new Commande(null, List.of(standard)));
  }

  @Test
  void modifier_la_liste_d_origine_ne_change_pas_la_commande() {
    List<Billet> billets = new ArrayList<>();
    billets.add(standard);
    Commande commande = new Commande(concert, billets);
    billets.add(new BilletVIP(new Prix(5000)));
    assertEquals(1, commande.billets().size());
  }

  @Test
  void la_liste_des_billets_n_est_pas_modifiable() {
    Commande commande = new Commande(concert, List.of(standard));
    assertThrows(UnsupportedOperationException.class,
        () -> commande.billets().add(new BilletVIP(new Prix(5000))));
  }
}