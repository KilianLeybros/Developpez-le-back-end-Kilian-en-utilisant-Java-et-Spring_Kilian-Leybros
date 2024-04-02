# Developpez-le-back-end-Kilian-en-utilisant-Java-et-Spring_Kilian-Leybros


## Start the project

Git clone : https://github.com/KilianLeybros/Developpez-le-back-end-Kilian-en-utilisant-Java-et-Spring_Kilian-Leybros.git

Le projet est réalisé sous java 17, le jdk 17 est nécessaire pour lancer le projet.

Se rendre à la racine du projet (/chatop) et installer les dépendances grâce à gradle :
./gradlew build --refresh-dependencies

MySQL server version 8 ainsi qu'un client MySQL (MySQL Workbench, phpMyAdmin) sont aussi nécessaires pour lancer le projet,
lien de téléchargement : https://dev.mysql.com/downloads/installer/

Une fois MySQL installé :

- Assurez-vous que MySQL est bien lancé sur le port 3306.

- Créez le le schéma grace au script se trouvant dans le dossier `src/main/ressources/sql` du projet.

- Créez un utilisateur username : admin, password admin et lui attribuer les priviléges suivants: DELETE,EXECUTE,INSERT,SELECT,SHOW VIEW, UPDATE

Vous pouvez désormais lancer le projet grâce à la commande : ./gradlew clean bootRun.

## Swagger

La documentation swagger est disponible à l'url suivante : http://localhost:3001/swagger-ui/index.html#/