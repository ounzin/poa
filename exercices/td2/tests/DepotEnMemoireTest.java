package fr.poa.td2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class DepotEnMemoireTest {

  private static final LocalDate JOUR = LocalDate.of(2026, 11, 30);
  private final DepotDeVentes depot = new DepotEnMemoire();

  @Test
  void une_vente_garde_ses_valeurs() {
    Vente vente = new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600);
    assertEquals("Nuit Électro", vente.concert());
    assertEquals("Alice", vente.spectateur());
    assertEquals(JOUR, vente.jour());
    assertEquals(3600, vente.prix());
  }

  @Test
  void une_vente_invalide_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> new Vente("Nuit Électro", " ", JOUR, 3600));
    assertThrows(IllegalArgumentException.class, () -> new Vente("Nuit Électro", "Alice", JOUR, -1));
    assertThrows(NullPointerException.class, () -> new Vente("Nuit Électro", "Alice", null, 3600));
  }

  @Test
  void depot_de_ventes_est_une_interface() {
    assertTrue(DepotDeVentes.class.isInterface());
  }

  @Test
  void un_depot_neuf_est_vide() {
    assertTrue(depot.toutes().isEmpty());
  }

  @Test
  void les_ventes_restent_dans_l_ordre_d_enregistrement() {
    depot.enregistrer(new Vente("Nuit Électro", "Bruno", JOUR, 4500));
    depot.enregistrer(new Vente("Jazz au parc", "Alice", JOUR, 2500));
    assertEquals(List.of("Bruno", "Alice"), depot.toutes().stream().map(Vente::spectateur).toList());
  }

  @Test
  void la_liste_des_ventes_n_est_pas_modifiable() {
    depot.enregistrer(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600));
    assertThrows(UnsupportedOperationException.class, () -> depot.toutes().clear());
  }

  @Test
  void une_liste_deja_renvoyee_ne_change_plus() {
    List<Vente> avant = depot.toutes();
    depot.enregistrer(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600));
    assertTrue(avant.isEmpty());
    assertEquals(1, depot.toutes().size());
  }
}
