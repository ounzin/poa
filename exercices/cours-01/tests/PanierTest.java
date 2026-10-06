package fr.poa.cours01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PanierTest {

  @Test
  void un_panier_vide_vaut_zero() {
    assertEquals(0, new Panier().total());
  }

  @Test
  void le_total_additionne_billets_et_tshirts() {
    Concert concert = new Concert("Nuit Électro", 500);
    Panier panier = new Panier();
    panier.ajouter(new BilletStandard(concert, 5000));
    panier.ajouter(new BilletEtudiant(concert, 5000));
    panier.ajouter(new TShirt());
    assertEquals(11000, panier.total());
  }

  @Test
  void un_meme_article_peut_etre_ajoute_deux_fois() {
    Panier panier = new Panier();
    TShirt tshirt = new TShirt();
    panier.ajouter(tshirt);
    panier.ajouter(tshirt);
    assertEquals(5000, panier.total());
  }
}