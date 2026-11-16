package fr.poa.td2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DepotFichierTest {

  @TempDir
  Path dossier;

  private Path fichier() {
    return dossier.resolve("ventes.csv");
  }

  @Test
  void un_fichier_absent_ne_contient_aucune_vente() {
    assertTrue(new DepotFichier(fichier()).toutes().isEmpty());
  }

  @Test
  void enregistrer_ajoute_une_ligne_au_fichier() throws IOException {
    new DepotFichier(fichier()).enregistrer(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600));
    assertEquals(List.of("Nuit Électro;Alice;2026-11-30;3600"), Files.readAllLines(fichier()));
  }

  @Test
  void les_ventes_sont_relues_dans_l_ordre() {
    DepotDeVentes depot = new DepotFichier(fichier());
    depot.enregistrer(new Vente("Nuit Électro", "Bruno", LocalDate.of(2026, 11, 30), 3600));
    depot.enregistrer(new Vente("Jazz au parc", "Alice", LocalDate.of(2026, 12, 1), 2500));
    assertEquals(List.of("Bruno", "Alice"), depot.toutes().stream().map(Vente::spectateur).toList());
  }

  @Test
  void les_ventes_survivent_a_un_nouveau_depot() {
    new DepotFichier(fichier()).enregistrer(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600));
    assertEquals(List.of(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600)), new DepotFichier(fichier()).toutes());
  }

  @Test
  void les_lignes_vides_sont_ignorees() throws IOException {
    Files.writeString(fichier(), "Nuit Électro;Alice;2026-11-30;3600\n\nJazz au parc;Bruno;2026-12-01;2500\n");
    assertEquals(2, new DepotFichier(fichier()).toutes().size());
  }

  @Test
  void une_ligne_illisible_leve_une_illegal_argument_exception() throws IOException {
    Files.writeString(fichier(), "Nuit Électro;Alice;2026-11-30;3600\nligne abîmée\n");
    assertThrows(IllegalArgumentException.class, () -> new DepotFichier(fichier()).toutes());
  }

  @Test
  void un_dossier_absent_leve_une_unchecked_io_exception() {
    DepotDeVentes depot = new DepotFichier(dossier.resolve("absent").resolve("ventes.csv"));
    assertThrows(UncheckedIOException.class, () -> depot.enregistrer(new Vente("Nuit Électro", "Alice", LocalDate.of(2026, 11, 30), 3600)));
  }

  @Test
  void la_billetterie_garde_ses_ventes_d_un_jour_a_l_autre() {
    Concert nuit = new Concert("Nuit Électro", LocalDate.of(2026, 12, 30), 4500);
    Billetterie lundi = new Billetterie(new HorlogeFixe(LocalDate.of(2026, 11, 30)), new DepotFichier(fichier()));
    lundi.vendre(nuit, "Alice");
    Billetterie mardi = new Billetterie(new HorlogeFixe(LocalDate.of(2026, 12, 1)), new DepotFichier(fichier()));
    mardi.vendre(nuit, "Bruno");
    assertEquals(3600 + 4500, mardi.recette());
  }
}
