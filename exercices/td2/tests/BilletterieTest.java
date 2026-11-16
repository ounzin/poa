package fr.poa.td2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class BilletterieTest {

  private static final Concert NUIT = new Concert("Nuit Électro", LocalDate.of(2026, 12, 30), 4500);
  private final DepotDeVentes depot = new DepotEnMemoire();

  private Billetterie billetterieLe(int annee, int mois, int jour) {
    return new Billetterie(new HorlogeFixe(LocalDate.of(annee, mois, jour)), depot);
  }

  @Test
  void un_concert_invalide_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> new Concert(" ", LocalDate.of(2026, 12, 30), 4500));
    assertThrows(IllegalArgumentException.class, () -> new Concert("Nuit Électro", LocalDate.of(2026, 12, 30), -1));
  }

  @Test
  void a_moins_de_trente_jours_le_prix_est_plein() {
    assertEquals(4500, billetterieLe(2026, 12, 1).prix(NUIT));
    assertEquals(4500, billetterieLe(2026, 12, 30).prix(NUIT));
  }

  @Test
  void a_trente_jours_ou_plus_la_prevente_retire_vingt_pour_cent() {
    assertEquals(3600, billetterieLe(2026, 11, 30).prix(NUIT));
    assertEquals(3600, billetterieLe(2026, 10, 1).prix(NUIT));
  }

  @Test
  void vendre_renvoie_la_vente_du_jour() {
    assertEquals(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600), billetterieLe(2026, 11, 30).vendre(NUIT, "Alice"));
  }

  @Test
  void vendre_enregistre_la_vente_dans_le_depot() {
    billetterieLe(2026, 12, 10).vendre(NUIT, "Bruno");
    assertEquals(List.of(new Vente("Nuit Électro", "Bruno", LocalDate.of(2026, 12, 10), 4500)), depot.toutes());
  }

  @Test
  void le_jour_du_concert_on_peut_encore_vendre() {
    assertEquals(4500, billetterieLe(2026, 12, 30).vendre(NUIT, "Chloé").prix());
  }

  @Test
  void un_concert_passe_ne_se_vend_plus() {
    assertThrows(IllegalStateException.class, () -> billetterieLe(2026, 12, 31).vendre(NUIT, "David"));
    assertTrue(depot.toutes().isEmpty());
  }

  @Test
  void la_recette_additionne_les_ventes_du_depot() {
    depot.enregistrer(new Vente("Jazz au parc", "Emma", LocalDate.of(2026, 11, 2), 2500));
    Billetterie billetterie = billetterieLe(2026, 11, 30);
    billetterie.vendre(NUIT, "Alice");
    billetterie.vendre(NUIT, "Bruno");
    assertEquals(2500 + 3600 + 3600, billetterie.recette());
  }

  @Test
  void une_lambda_peut_servir_d_horloge() {
    Billetterie billetterie = new Billetterie(() -> LocalDate.of(2026, 12, 20), depot);
    assertEquals(4500, billetterie.prix(NUIT));
  }

  @Test
  void les_dependances_absentes_sont_refusees() {
    assertThrows(NullPointerException.class, () -> new Billetterie(null, depot));
    assertThrows(NullPointerException.class, () -> new Billetterie(new HorlogeSysteme(), null));
  }
}
