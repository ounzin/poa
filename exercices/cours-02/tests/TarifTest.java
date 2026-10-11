package fr.poa.cours02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TarifTest {

  @Test
  void un_billet_standard_coute_le_prix_de_base() {
    assertEquals(new Prix(5000), Tarif.prix(new BilletStandard(new Prix(5000))));
  }

  @Test
  void un_billet_etudiant_coute_30_pourcent_de_moins() {
    assertEquals(new Prix(3500), Tarif.prix(new BilletEtudiant(new Prix(5000), "12345678")));
    assertEquals(new Prix(2335), Tarif.prix(new BilletEtudiant(new Prix(3337), "12345678")));
  }

  @Test
  void un_billet_vip_coute_une_fois_et_demie_le_prix_de_base() {
    assertEquals(new Prix(7500), Tarif.prix(new BilletVIP(new Prix(5000))));
  }

  @Test
  void une_invitation_est_gratuite() {
    assertEquals(Prix.ZERO, Tarif.prix(new Invitation("Lina Martin")));
  }

  @Test
  void le_calcul_du_prix_est_hors_des_billets() {
    assertEquals(0, Billet.class.getDeclaredMethods().length,
        "Billet ne déclare aucune méthode : le prix est calculé par Tarif, avec un switch");
  }
}