# Programmation Objet Avancée (Java)

Ce dépôt contient tout le matériel du cours : les supports, les exercices de chaque cours, les énoncés des TP et le sujet du projet. Vous en faites **votre propre dépôt privé**, une seule fois, puis vous y recevez les nouveautés au fil des séances.

## Pour commencer

Suivez [INSTALLATION.md](INSTALLATION.md) : installation des outils et création de votre dépôt. Vous êtes prêt quand `./mvnw test` affiche `BUILD SUCCESS`.

## Organisation du dépôt

```
poa/
├── README.md             ce fichier
├── INSTALLATION.md       installation, création de votre dépôt, pièges courants
├── pom.xml               configuration Maven : Java 25, JUnit
├── mvnw, mvnw.cmd, .mvn/ Maven Wrapper : Maven s'installe tout seul
├── cours/                supports de cours (PDF)
├── exercices/cours-XX/   énoncé et tests des exercices du cours XX
├── tp/tpX/               énoncé et tests fournis du TP X
├── projet/               sujet et cahier des charges du projet
└── src/                  VOTRE travail
    ├── main/java/fr/poa/ votre code (cours01, tp1…)
    └── test/java/fr/poa/ les tests que vous y copiez
```

Les dossiers `cours/`, `exercices/`, `tp/` et `projet/` se remplissent au fil des séances.

## Vos deux dépôts distants

| Nom      | Dépôt                            | Sert à                                          | Commande                       |
| -------- | -------------------------------- | ----------------------------------------------- | ------------------------------ |
| `prof`   | `github.com/ounzin/poa` (public) | recevoir les nouveaux supports, exercices et TP | `git pull --no-edit prof main` |
| `origin` | votre dépôt privé                | sauvegarder votre travail                       | `git push`                     |

## Les règles

- `src/` est à vous : je n'y ajoute jamais rien, à part `InstallationTest.java`, présent dès le départ.
- Ne modifiez aucun fichier fourni (tout ce qui est hors de `src/`) : la mise à jour suivante provoquerait un conflit.
- Les tests de `exercices/` et `tp/` ne sont pas compilés tant qu'ils restent là : copiez-les dans `src/test/java/`.
- Tout `src/` est compilé d'un bloc : un seul fichier qui ne compile pas bloque **tous** les tests. Laissez toujours `src/` compilable, au besoin avec des classes et des méthodes vides.

## Routine d'un cours

```mermaid
flowchart LR
    A[git pull prof] --> B[Lire l'énoncé]
    B --> C[Copier le test]
    C --> D[./mvnw test : rouge]
    D --> E[Coder dans src/main/java]
    E --> F[./mvnw test : vert]
    F --> G[git commit, git push]
```

1. Récupérer les nouveautés :

```bash
   git pull --no-edit prof main
```

2. Ouvrir l'énoncé du cours, par exemple `exercices/cours-01/enonce.md`.
3. Copier le test indiqué par la question.
   macOS / Linux :

```bash
   mkdir -p src/test/java/fr/poa/cours01
   cp exercices/cours-01/tests/BilletVIPTest.java src/test/java/fr/poa/cours01/
```

Windows (PowerShell) :

```powershell
   New-Item -ItemType Directory -Force src\test\java\fr\poa\cours01
   Copy-Item exercices\cours-01\tests\BilletVIPTest.java src\test\java\fr\poa\cours01\
```

4. Lancer `./mvnw test`. Un test qui ne compile pas est un test rouge : créez d'abord la classe et les méthodes demandées, vides.
5. Coder dans `src/main/java/fr/poa/cours01/` jusqu'à `BUILD SUCCESS`.
6. Sauvegarder votre travail :

```bash
   git add src
   git commit -m "Cours 1 : exercices"
   git push
```

Pour lancer une seule classe de test : `./mvnw test -Dtest=BilletVIPTest`.

| Terminal                    | Commande          |
| --------------------------- | ----------------- |
| macOS, Linux, Git Bash      | `./mvnw test`     |
| Windows PowerShell          | `.\mvnw.cmd test` |
| Windows Invite de commandes | `mvnw test`       |

## Les TP

Les TP sont individuels et ne sont pas traités en cours.

1. L'énoncé est dans `tp/tpX/enonce.md`, et les tests fournis dans `tp/tpX/tests/`, à copier dans `src/test/java/fr/poa/tpX/`.
2. Votre code va dans `src/main/java/fr/poa/tpX/`. Les noms de classes et de méthodes sont imposés par l'énoncé : respectez-les à la lettre.
3. Chaque fichier commence par cet en-tête, avant la ligne `package` :

```java
   /*
    * NOM Prénom — N° étudiant : 12345678
    */
```

4. Vérifiez que `./mvnw test` affiche `BUILD SUCCESS`. La correction ajoute d'autres tests : si votre code empêche la compilation, la note de l'exercice est 0.

### Rendu

Chaque TP est rendu sous la forme d'un zip du dossier `src/main/java/fr/poa/tpX/`, nommé `TPx_NOM_Prenom_NumEtudiant.zip` et envoyé par mail. Les trois TP doivent être reçus **avant le début de la séance 8** (soutenances du projet).

macOS / Linux, depuis le dossier `poa` (sous Linux, `sudo apt install zip` si besoin) :

```bash
cd src/main/java/fr/poa
zip -r ~/TP1_NOM_Prenom_12345678.zip tp1
cd -
```

Windows (PowerShell), depuis le dossier `poa` :

```powershell
Compress-Archive -Path src\main\java\fr\poa\tp1 -DestinationPath $HOME\TP1_NOM_Prenom_12345678.zip
```

## Évaluation

| Élément                      | Poids |
| ---------------------------- | ----- |
| 3 TP individuels             | 40 %  |
| Projet en groupe (AssurAuto) | 60 %  |

## Programme

| Séance | Contenu                                                                                               | Travail                                          |
| ------ | ----------------------------------------------------------------------------------------------------- | ------------------------------------------------ |
| S1     | Installation, rappel POO                                                                              | Sujet du projet, groupes                         |
| S2     | Modéliser : records, sealed, pattern matching, immutabilité, equals/hashCode, composition, exceptions | Publication du TP1                               |
| S3     | Génériques et collections                                                                             | Projet : modèle et répartition validés           |
| S4     | Lambdas et streams                                                                                    | Publication du TP2                               |
| S5     | Conception : SOLID, patterns, injection par constructeur, doublures de test                           | Projet : jalon 1                                 |
| S6     | Architecture en couches, JDBC (H2), fichiers                                                          | Publication du TP3, avenant du projet            |
| S7     | Concurrence                                                                                           | Atelier projet final                             |
| S8     | Soutenances                                                                                           | Projet rendu la veille, TP reçus avant la séance |
