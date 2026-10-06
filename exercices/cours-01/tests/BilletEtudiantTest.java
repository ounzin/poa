package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BilletEtudiantTest {

  @Test
  void un_billet_etudiant_coute_30_pourcent_de_moins() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(3500, new BilletEtudiant(concert, 5000).prix());
  }

  @Test
  void le_prix_est_arrondi_au_centime_inferieur() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(2335, new BilletEtudiant(concert, 3337).prix(),
        "33,37 € × 0,7 = 23,359 € : arrondi au centime inférieur, 23,35 €");
  }

  @Test
  void le_calcul_reste_exact_au_centime() {
    Concert concert = new Concert("Nuit Électro", 500);
    assertEquals(455, new BilletEtudiant(concert, 650).prix(),
        "6,50 € × 0,7 = 4,55 € exactement : un calcul en double donne 4,54 €");
  }
}