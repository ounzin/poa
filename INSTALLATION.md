# Installation

Objectif : à la fin de ce guide, `./mvnw test` affiche `BUILD SUCCESS`.

```mermaid
flowchart LR
    A[Installer les outils] --> B[Cloner le dépôt poa]
    B --> C[./mvnw test]
    C --> D[BUILD SUCCESS]
```

| Outil                    | Rôle                                     |
| ------------------------ | ---------------------------------------- |
| JDK 25 (Temurin)         | Compiler et exécuter du Java             |
| Git                      | Versionner le code                       |
| GitHub CLI (`gh`)        | Se connecter à GitHub depuis le terminal |
| VS Code ou IntelliJ IDEA | Écrire et lancer le code                 |

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

Pour les autres distributions : https://adoptium.net/installation/linux/

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

Déclarez votre identité une seule fois. Utilisez l'adresse de votre compte GitHub, pour que vos commits vous soient attribués : cela compte pour le projet.

```bash
git config --global user.name "Prénom Nom"
git config --global user.email "vous@exemple.fr"
```

## 3. GitHub CLI

Le dépôt `poa` est public : le cloner ne demande aucun compte. En revanche, le projet se fait dans un dépôt **privé** de groupe. `gh` vous connecte à GitHub depuis le terminal pour pouvoir y pousser votre code. Créez un compte sur https://github.com si vous n'en avez pas.

- macOS : `brew install gh`
- Linux : `sudo apt install -y gh` (version plus récente : https://github.com/cli/cli/blob/trunk/docs/install_linux.md)
- Windows : `winget install --id GitHub.cli -e`

Connectez-vous ensuite :

```bash
gh auth login
```

Répondez dans l'ordre : `GitHub.com` → `HTTPS` → `Yes` (authentifier Git) → `Login with a web browser`. Vérifiez avec `gh auth status`.

## 4. Éditeur

**VS Code** : installez l'extension _Extension Pack for Java_ (Microsoft), puis faites _File > Open Folder_ sur le dossier `poa`. Si le JDK 25 n'est pas détecté : `Ctrl+Shift+P` (`Cmd+Shift+P` sur macOS) → _Java: Configure Java Runtime_.

**IntelliJ IDEA** : faites _File > Open_ sur le dossier `poa` (le projet Maven est détecté grâce à `pom.xml`). Dans _File > Project Structure > Project_, choisissez le SDK 25.

Dans les deux cas, les fichiers doivent être enregistrés en **UTF-8**. C'est le réglage par défaut ; dans IntelliJ, il se trouve dans _Settings > Editor > File Encodings_.

## 5. Cloner le dépôt

Placez-vous dans un dossier de travail, de préférence sans espace ni accent dans le chemin, puis :

```bash
git clone https://github.com/ounzin/poa.git
cd poa
git config pull.rebase false
```

La dernière commande règle le comportement de `git pull` si vous avez fait des commits locaux : Git fusionne au lieu de s'arrêter sur une erreur.

## 6. Vérifier l'installation

Depuis le dossier `poa` :

```bash
./mvnw -v
```

La sortie doit contenir `Apache Maven 3.9.15` et `Java version: 25`. Si la version de Java n'est pas 25, Maven utilise un autre JDK : voir les pièges ci-dessous.

```bash
./mvnw test
```

Le premier lancement télécharge Maven et JUnit, il faut donc une connexion. La fin attendue :

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Sous Windows : `.\mvnw.cmd -v` et `.\mvnw.cmd test` dans PowerShell, `mvnw -v` et `mvnw test` dans l'invite de commandes.

## 7. Pièges courants

| Symptôme                                                                  | Cause                                                                        | Solution                                                                                                                                                                                                                                                    |
| ------------------------------------------------------------------------- | ---------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `release version 25 not supported`                                        | Maven utilise un JDK plus ancien                                             | `./mvnw -v` montre le JDK utilisé (ligne `Java version`). Corrigez `JAVA_HOME` (étape 1) : une ancienne définition dans `~/.zshrc`, `~/.zprofile`, `~/.bashrc` ou dans une variable utilisateur Windows peut l'écraser. Ouvrez ensuite un nouveau terminal. |
| `JAVA_HOME ... invalid directory` ou `not defined correctly`              | `JAVA_HOME` pointe vers `bin/` ou vers un dossier supprimé                   | `JAVA_HOME` doit désigner le dossier du JDK, celui qui **contient** `bin/`.                                                                                                                                                                                 |
| `package org.junit.jupiter.api does not exist`                            | Un test a été placé dans `src/main/java`                                     | JUnit n'est disponible que dans `src/test/java` (dépendance de portée `test`). Déplacez le fichier.                                                                                                                                                         |
| `unmappable character (0xE9) for encoding UTF-8`                          | Fichier enregistré dans un autre encodage que UTF-8                          | Réenregistrez-le en UTF-8. VS Code : clic sur l'encodage dans la barre d'état → _Save with Encoding_ → UTF-8. IntelliJ : _File > File Properties > File Encoding_.                                                                                          |
| `fatal: Need to specify how to reconcile divergent branches`              | Commits locaux, et `pull.rebase` non configuré                               | `git config pull.rebase false`, puis `git pull`.                                                                                                                                                                                                            |
| `Your local changes to the following files would be overwritten by merge` | Vous avez modifié un fichier fourni (hors `src/`)                            | `git restore <fichier>`, puis `git pull`.                                                                                                                                                                                                                   |
| `./mvnw: Permission denied`                                               | Le script a perdu son droit d'exécution (dossier copié au lieu d'être cloné) | `chmod +x mvnw`                                                                                                                                                                                                                                             |
| `'.' n'est pas reconnu en tant que commande interne`                      | Syntaxe macOS/Linux tapée dans l'invite de commandes Windows                 | `mvnw test` (invite de commandes) ou `.\mvnw.cmd test` (PowerShell).                                                                                                                                                                                        |
