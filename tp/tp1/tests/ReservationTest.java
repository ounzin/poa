package fr.poa.tp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReservationTest {

  private final Etudiant lea = new Etudiant("Léa Morel", "12345678");
  private final Creneau matin = new Creneau(new Horaire(10, 0), new Horaire(11, 0));

  @Test
  void reservation_est_un_record() {
    assertTrue(Reservation.class.isRecord());
  }

  @Test
  void une_reservation_associe_un_joueur_et_un_creneau() {
    Reservation reservation = new Reservation(lea, matin);
    assertSame(lea, reservation.joueur());
    assertSame(matin, reservation.creneau());
  }

  @Test
  void le_joueur_et_le_creneau_sont_obligatoires() {
    assertThrows(NullPointerException.class, () -> new Reservation(null, matin));
    assertThrows(NullPointerException.class, () -> new Reservation(lea, null));
  }

  @Test
  void le_prix_d_une_reservation_suit_le_tarif() {
    assertEquals(new Montant(750), new Reservation(lea, matin).prix());
  }

  @Test
  void deux_reservations_identiques_sont_egales() {
    assertEquals(new Reservation(lea, matin), new Reservation(lea, matin));
  }
}