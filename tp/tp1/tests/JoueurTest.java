package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class JoueurTest {

  private final Abonne hugo = new Abonne("Hugo Petit", "AB-0042");

  @Test
  void joueur_est_une_interface_scellee() {
    assertTrue(Joueur.class.isInterface(), "Joueur doit être une interface");
    assertTrue(Joueur.class.isSealed(), "Joueur doit être scellée (sealed)");
  }

  @Test
  void les_trois_types_de_joueurs_sont_autorises() {
    List<Class<?>> autorises = List.of(Joueur.class.getPermittedSubclasses());
    assertTrue(autorises.containsAll(List.of(Abonne.class, Etudiant.class, Invite.class)));
  }

  @Test
  void chaque_type_de_joueur_est_un_record() {
    for (Class<?> type : Joueur.class.getPermittedSubclasses()) {
      assertTrue(type.isRecord(), type.getSimpleName() + " doit être un record");
    }
  }

  @Test
  void chaque_joueur_donne_son_nom() {
    List<Joueur> joueurs = List.of(
        hugo,
        new Etudiant("Léa Morel", "12345678"),
        new Invite("Sami Diallo", hugo));
    List<String> noms = new ArrayList<>();
    for (Joueur joueur : joueurs) {
      noms.add(joueur.nom());
    }
    assertEquals(List.of("Hugo Petit", "Léa Morel", "Sami Diallo"), noms);
  }

  @Test
  void un_numero_etudiant_compte_huit_chiffres() {
    assertEquals("12345678", new Etudiant("Léa Morel", "12345678").numeroEtudiant());
    assertThrows(IllegalArgumentException.class, () -> new Etudiant("Léa Morel", "1234"));
  }

  @Test
  void un_invite_a_toujours_un_parrain() {
    assertSame(hugo, new Invite("Sami Diallo", hugo).parrain());
    assertThrows(NullPointerException.class, () -> new Invite("Sami Diallo", null));
  }
}