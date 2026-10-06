# Installation

Objectif : à la fin de ce guide, vous avez votre propre dépôt de travail, et `./mvnw test` y affiche `BUILD SUCCESS`.

```mermaid
flowchart LR
    A[Installer les outils] --> B[Créer votre dépôt]
    B --> C[./mvnw test]
    C --> D[BUILD SUCCESS]
```

| Outil                    | Rôle                                                          |
| ------------------------ | ------------------------------------------------------------- |
| JDK 25 (Temurin)         | Compiler et exécuter du Java                                  |
| Git                      | Versionner le code                                            |
| GitHub CLI (`gh`)        | Se connecter à GitHub et créer votre dépôt depuis le terminal |
| VS Code ou IntelliJ IDEA | Écrire et lancer le code                                      |

Maven n'est **pas** à installer : le dépôt contient le Maven Wrapper (`mvnw`), qui télécharge la bonne version de Maven au premier lancement.

## 1. JDK 25 (Temurin)

### macOS

Installez le JDK, au choix :

- l'installeur `.pkg` sur https://adoptium.net/temurin/releases/?version=25 (puce Apple : `aarch64` ; processeur Intel : `x64`) ;
- ou Homebrew : `brew install --cask temurin@25`.

Si un autre JDK est déjà configuré dans vos fichiers de démarrage (par exemple `openjdk@17` installé avec Homebrew), repérez les lignes concernées et mettez-les en commentaire avec `#` :

```bash
grep -n -E "JAVA_HOME|openjdk" ~/.zshrc ~/.zprofile 2>/dev/null
```

Faites ensuite pointer `JAVA_HOME` et la commande `java` sur le JDK 25 :

```bash
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 25)' >> ~/.zshrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.zshrc
```

Ouvrez un nouveau terminal pour que ces réglages s'appliquent.

### Linux (Debian, Ubuntu)

```bash
sudo apt install -y wget apt-transport-https gpg
wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list
sudo apt update
sudo apt install -y temurin-25-jdk
```

Si plusieurs JDK sont installés, choisissez le 25 avec `sudo update-alternatives --config java`. Puis :

```bash
echo 'export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))' >> ~/.bashrc
source ~/.bashrc
```

Autres distributions : https://adoptium.net/installation/linux/

### Windows

1. Téléchargez l'installeur `.msi` sur https://adoptium.net/temurin/releases/?version=25 (Windows, x64, JDK).
2. Dans l'écran « Custom Setup », activez **Set JAVA_HOME variable** (« Will be installed on local hard drive »). Laissez « Add to PATH » activé.
3. Fermez et rouvrez le terminal : les variables d'environnement ne sont lues qu'à son ouverture.

### Vérifier

```bash
java -version
```

La première ligne doit contenir `25`, par exemple `openjdk version "25.0.4.1"`. Vérifiez aussi `JAVA_HOME` : `echo $JAVA_HOME` (macOS, Linux) ou `echo $env:JAVA_HOME` (PowerShell).

## 2. Git

- macOS : `xcode-select --install` (ou `brew install git`)
- Linux : `sudo apt install -y git`
- Windows : https://git-scm.com/downloads/win (options par défaut ; installe aussi Git Bash)

Déclarez votre identité une seule fois. Utilisez l'adresse de votre compte GitHub, pour que vos commits vous soient attribués :

```bash
git config --global user.name "Prénom Nom"
git config --global user.email "vous@exemple.fr"
```

## 3. GitHub CLI

Un compte GitHub est nécessaire : votre travail est sauvegardé dans un dépôt privé à votre nom, et le projet se fait aussi sur GitHub. Créez un compte sur https://github.com si vous n'en avez pas. `gh` vous connecte à GitHub depuis le terminal et crée votre dépôt.

- macOS : `brew install gh`
- Linux : `sudo apt install -y gh` (version plus récente : https://github.com/cli/cli/blob/trunk/docs/install_linux.md)
- Windows : `winget install --id GitHub.cli -e`

Puis connectez-vous :

```bash
gh auth login
```

Réponses : `GitHub.com` → `HTTPS` → `Yes` (authentifier Git) → `Login with a web browser`. Vérifiez avec `gh auth status`.

## 4. Éditeur

**VS Code** : installez l'extension _Extension Pack for Java_ (Microsoft), puis _File > Open Folder_ sur le dossier `poa`. Si le JDK 25 n'est pas détecté : `Ctrl+Shift+P` (`Cmd+Shift+P` sur macOS) → _Java: Configure Java Runtime_.

**IntelliJ IDEA** : _File > Open_ sur le dossier `poa` (le projet Maven est détecté via `pom.xml`). Dans _File > Project Structure > Project_, choisissez le SDK 25.

Dans les deux cas, les fichiers doivent être enregistrés en **UTF-8**. C'est le réglage par défaut ; dans IntelliJ, il se trouve dans _Settings > Editor > File Encodings_.

## 5. Créer votre dépôt de travail

Votre travail sera sauvegardé dans **votre** dépôt GitHub, privé, et vous continuerez à recevoir les nouveautés du dépôt du cours.

Placez-vous dans un dossier de travail, de préférence sans espace ni accent dans le chemin. Dans la dernière commande, remplacez `NOM-Prenom` par vos nom et prénom, sans accent. Puis :

```bash
git clone https://github.com/ounzin/poa.git
cd poa
git config pull.rebase false
git remote rename origin prof
gh repo create poa-NOM-Prenom --private --source=. --remote=origin --push
```

Ce que font ces commandes :

- `git clone` copie le dépôt du cours, avec son historique ;
- `git config pull.rebase false` : lors d'une mise à jour, si vous avez des commits, Git fusionne au lieu de s'arrêter sur une erreur ;
- `git remote rename origin prof` : le dépôt du cours s'appelle désormais `prof` ;
- `gh repo create` crée votre dépôt privé sur GitHub, l'enregistre sous le nom `origin` et y envoie le contenu.

Vérifiez :

```bash
git remote -v
```

```
origin  https://github.com/VOTRE-COMPTE/poa-NOM-Prenom.git (fetch)
origin  https://github.com/VOTRE-COMPTE/poa-NOM-Prenom.git (push)
prof    https://github.com/ounzin/poa.git (fetch)
prof    https://github.com/ounzin/poa.git (push)
```

N'utilisez ni « Fork » ni « Use this template ». Un fork d'un dépôt public est public : vos TP seraient visibles de tous. Un dépôt créé par « Use this template » n'a pas d'historique commun avec celui du cours : chaque mise à jour provoquerait des conflits.

## 6. Vérifier l'installation

Depuis le dossier `poa` :

```bash
./mvnw -v
```

La sortie doit contenir `Apache Maven 3.9.15` et `Java version: 25`. Si la version de Java n'est pas 25, Maven utilise un autre JDK : voir les pièges ci-dessous.

```bash
./mvnw test
```

Le premier lancement télécharge Maven et JUnit (connexion nécessaire). La fin attendue :

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Sous Windows : `.\mvnw.cmd -v` et `.\mvnw.cmd test` dans PowerShell, `mvnw -v` et `mvnw test` dans l'invite de commandes.

L'installation est terminée. La routine de chaque cours est décrite dans [README.md](README.md).

## 7. Pièges courants

| Symptôme                                                                  | Cause                                                        | Solution                                                                                                                                                                                                                                                    |
| ------------------------------------------------------------------------- | ------------------------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `release version 25 not supported`                                        | Maven utilise un JDK plus ancien                             | `./mvnw -v` montre le JDK utilisé (ligne `Java version`). Corrigez `JAVA_HOME` (étape 1) : une ancienne définition dans `~/.zshrc`, `~/.zprofile`, `~/.bashrc` ou dans une variable utilisateur Windows peut l'écraser. Ouvrez ensuite un nouveau terminal. |
| `JAVA_HOME ... invalid directory` ou `not defined correctly`              | `JAVA_HOME` pointe vers `bin/` ou vers un dossier supprimé   | `JAVA_HOME` doit désigner le dossier du JDK, celui qui **contient** `bin/`.                                                                                                                                                                                 |
| `package org.junit.jupiter.api does not exist`                            | Un test est dans `src/main/java`                             | JUnit n'est disponible que dans `src/test/java` (dépendance de portée `test`). Déplacez le fichier.                                                                                                                                                         |
| `unmappable character (0xE9) for encoding UTF-8`                          | Fichier enregistré dans un autre encodage que UTF-8          | Réenregistrez-le en UTF-8. VS Code : clic sur l'encodage dans la barre d'état → _Save with Encoding_ → UTF-8. IntelliJ : _File > File Properties > File Encoding_.                                                                                          |
| `fatal: Need to specify how to reconcile divergent branches`              | `pull.rebase` non configuré                                  | `git config pull.rebase false`, puis relancez la commande.                                                                                                                                                                                                  |
| `Your local changes to the following files would be overwritten by merge` | Vous avez modifié un fichier fourni, sans le commiter        | `git restore <fichier>`, puis relancez `git pull --no-edit prof main`.                                                                                                                                                                                      |
| `CONFLICT (content): Merge conflict in <fichier>`                         | Vous avez modifié et commité un fichier fourni               | Reprenez la version du cours : `git checkout --theirs <fichier>`, puis `git add <fichier>` et `git commit --no-edit`.                                                                                                                                       |
| Un éditeur (souvent vim) s'ouvre pendant `git pull`                       | `--no-edit` oublié                                           | Tapez `:wq` puis Entrée.                                                                                                                                                                                                                                    |
| `The current branch main has no upstream branch` lors de `git push`       | Le lien avec votre dépôt n'est pas enregistré                | `git push -u origin main` (une seule fois).                                                                                                                                                                                                                 |
| `./mvnw: Permission denied`                                               | Le script a perdu son droit d'exécution                      | `chmod +x mvnw`                                                                                                                                                                                                                                             |
| `'.' n'est pas reconnu en tant que commande interne`                      | Syntaxe macOS/Linux tapée dans l'invite de commandes Windows | `mvnw test` (invite de commandes) ou `.\mvnw.cmd test` (PowerShell).                                                                                                                                                                                        |
