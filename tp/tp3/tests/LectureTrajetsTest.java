package fr.poa.tp3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class LectureTrajetsTest {

  private static final String EN_TETE = "usager;jour;depart;arrivee;minutes";

  @Test
  void lire_des_lignes_valides() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE,
        "Alice;2026-11-30;Gare;Bibliothèque;12",
        "Bruno;2026-11-30;Résidence;Stade;45"));
    assertEquals(List.of(
        new Trajet("Alice", LocalDate.of(2026, 11, 30), "Gare", "Bibliothèque", 12),
        new Trajet("Bruno", LocalDate.of(2026, 11, 30), "Résidence", "Stade", 45)), resultat.trajets());
    assertTrue(resultat.lignesRejetees().isEmpty());
  }

  @Test
  void l_en_tete_n_est_pas_un_trajet() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE));
    assertTrue(resultat.trajets().isEmpty());
    assertTrue(resultat.lignesRejetees().isEmpty());
  }

  @Test
  void une_ligne_vide_est_ignoree() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE, "Alice;2026-11-30;Gare;Stade;12", "", "Bruno;2026-11-30;Gare;Stade;8"));
    assertEquals(2, resultat.trajets().size());
    assertTrue(resultat.lignesRejetees().isEmpty());
  }

  @Test
  void une_duree_illisible_rejette_la_ligne() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE, "Alice;2026-11-30;Gare;Stade;douze"));
    assertTrue(resultat.trajets().isEmpty());
    assertEquals(List.of(2), resultat.lignesRejetees());
  }

  @Test
  void une_date_illisible_rejette_la_ligne() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE, "Alice;30/11/2026;Gare;Stade;12"));
    assertEquals(List.of(2), resultat.lignesRejetees());
  }

  @Test
  void un_nombre_de_champs_incorrect_rejette_la_ligne() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE, "Alice;2026-11-30;Gare;12", "Alice;2026-11-30;Gare;Stade;12;vélo électrique"));
    assertEquals(List.of(2, 3), resultat.lignesRejetees());
  }

  @Test
  void un_trajet_qui_ne_respecte_pas_les_regles_rejette_la_ligne() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE, "Alice;2026-11-30;Gare;Stade;0", " ;2026-11-30;Gare;Stade;12"));
    assertEquals(List.of(2, 3), resultat.lignesRejetees());
  }

  @Test
  void la_lecture_continue_apres_une_ligne_rejetee() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE, "Alice;2026-11-30;Gare;Stade;douze", "Bruno;2026-11-30;Gare;Stade;8"));
    assertEquals(List.of(new Trajet("Bruno", LocalDate.of(2026, 11, 30), "Gare", "Stade", 8)), resultat.trajets());
  }

  @Test
  void les_lignes_sont_numerotees_en_comptant_l_en_tete() {
    ResultatLecture resultat = LectureTrajets.lire(List.of(EN_TETE,
        "Alice;2026-11-30;Gare;Bibliothèque;12",
        "Bruno;2026-11-31;Résidence;Stade;45",
        "",
        "Alice;2026-12-01;Bibliothèque;Gare;15",
        "Chloé;2026-12-01;Gare;Stade;soixante-dix",
        "David;2026-12-03;Gare;Résidence;31"));
    assertEquals(3, resultat.trajets().size());
    assertEquals(List.of(3, 6), resultat.lignesRejetees());
  }
}
