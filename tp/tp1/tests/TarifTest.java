package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TarifTest {

  private final Abonne hugo = new Abonne("Hugo Petit", "AB-0042");
  private final Etudiant lea = new Etudiant("Léa Morel", "12345678");
  private final Invite sami = new Invite("Sami Diallo", hugo);

  private static Creneau creneau(int heureDebut, int minuteDebut, int heureFin, int minuteFin) {
    return new Creneau(new Horaire(heureDebut, minuteDebut), new Horaire(heureFin, minuteFin));
  }

  @Test
  void un_abonne_ne_paie_rien() {
    assertEquals(Montant.ZERO, Tarif.prix(hugo, creneau(10, 0, 12, 0)));
  }

  @Test
  void un_etudiant_paie_7_50_par_heure() {
    assertEquals(new Montant(750), Tarif.prix(lea, creneau(10, 0, 11, 0)));
    assertEquals(new Montant(1125), Tarif.prix(lea, creneau(10, 0, 11, 30)));
  }

  @Test
  void le_prix_est_arrondi_au_centime_inferieur() {
    assertEquals(new Montant(562), Tarif.prix(lea, creneau(10, 0, 10, 45)),
        "7,50 € × 45 / 60 = 5,625 € : arrondi au centime inférieur, 5,62 €");
  }

  @Test
  void un_invite_paie_10_50_par_heure_avant_midi() {
    assertEquals(new Montant(1575), Tarif.prix(sami, creneau(10, 0, 11, 30)));
  }

  @Test
  void un_invite_paie_14_par_heure_l_apres_midi() {
    assertEquals(new Montant(1400), Tarif.prix(sami, creneau(14, 0, 15, 0)));
  }

  @Test
  void le_tarif_est_calcule_hors_des_joueurs() {
    assertEquals(1, Joueur.class.getDeclaredMethods().length,
        "Joueur ne déclare que nom() : le tarif est calculé par Tarif, avec un switch");
  }
}