package fr.poa.td2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class VenteLigneTest {

  private final Vente vente = new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600);

  @Test
  void une_vente_tient_en_une_ligne() {
    assertEquals("Nuit Électro;Alice;2026-11-30;3600", vente.versLigne());
  }

  @Test
  void relire_une_ligne() {
    assertEquals(vente, Vente.depuisLigne("Nuit Électro;Alice;2026-11-30;3600"));
  }

  @Test
  void une_vente_survit_a_l_aller_et_retour() {
    Vente invitation = new Vente("Jazz au parc", "Bruno", LocalDate.of(2026, 12, 1), 0);
    assertEquals(invitation, Vente.depuisLigne(invitation.versLigne()));
  }

  @Test
  void une_ligne_sans_quatre_champs_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> Vente.depuisLigne("Nuit Électro;Alice;2026-11-30"));
    assertThrows(IllegalArgumentException.class, () -> Vente.depuisLigne("Nuit Électro;Alice;2026-11-30;3600;carte"));
  }

  @Test
  void un_prix_illisible_est_refuse() {
    assertThrows(IllegalArgumentException.class, () -> Vente.depuisLigne("Nuit Électro;Alice;2026-11-30;trente-six"));
  }

  @Test
  void une_date_illisible_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> Vente.depuisLigne("Nuit Électro;Alice;30/11/2026;3600"));
  }

  @Test
  void une_ligne_qui_decrit_une_vente_invalide_est_refusee() {
    assertThrows(IllegalArgumentException.class, () -> Vente.depuisLigne("Nuit Électro; ;2026-11-30;3600"));
  }
}
