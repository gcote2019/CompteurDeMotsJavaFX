package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    public static final String TITRE = "Compteur de mots";
    private TextArea textArea;
    private Text compteur;
    private int nombreDeMots = 0;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_LEFT);
        root.setSpacing(10);
        Scene scene = new Scene(root, 800, 350);

        Text titre = new Text(TITRE);
        titre.setFont(Font.font(30));
        root.getChildren().add(titre);

        textArea = new TextArea();
        root.getChildren().add(textArea);
        textArea.setWrapText(true);
        textArea.setOnKeyTyped(event -> {
            compterMots(textArea.getText());
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);
        Text text = new Text("Nombre de mots");
        text.setFont(creerPolice(false, false, 20));
        hBox.getChildren().add(text);
        compteur = new Text("0");
        compteur.setFont(creerPolice(true, false, 20));
        hBox.getChildren().add(compteur);
        Button bouton = new Button("Enlever un mot au hasard");
        bouton.setOnAction(event -> {
            enleverMotAuHasard();
        });
        hBox.getChildren().add(bouton);
        root.getChildren().add(hBox);




        stage.setTitle(TITRE);
        stage.setScene(scene);
        stage.show();
    }

    private Font creerPolice(boolean gras, boolean italique, int tailleDeLaPolice) {
        Font police = Font.getDefault();
        return Font.font(police.getFamily(),
                gras ? FontWeight.BOLD : FontWeight.NORMAL,
                italique ? FontPosture.ITALIC : FontPosture.REGULAR, tailleDeLaPolice);

    }

    private int compter(String chaine) {
        boolean detectionLettre = false;
        int compte = 0;
        for (int i = 0; i < chaine.length(); i++) {
            char ch = chaine.charAt(i);
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                if (!detectionLettre) {
                    detectionLettre = true;
                    compte++;
                }
            } else {
                detectionLettre = false;
            }
        }
        return compte;
    }
    private void compterMots(String texte) {
        nombreDeMots = compter(texte);;
        compteur.setText("" + nombreDeMots);
    }

    private Paire trouverPosition(String chaine, int positionDuMot) {
        boolean detectionLettre = false;
        int compte = 0;
        int debut = -1;
        int fin = -1;
        for (int i = 0; i < chaine.length(); i++) {
            char ch = chaine.charAt(i);
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                if (!detectionLettre) {
                    detectionLettre = true;
                    compte++;
                    if (compte == positionDuMot) {
                        debut = i;
                        fin = debut + 1;
                    }
                } else if (compte == positionDuMot){
                    fin = i + 1;
                }
            } else {
                detectionLettre = false;
            }
        }
        return new Paire(debut, fin);
    }

    private void enleverMotAuHasard() {
        if (nombreDeMots > 0) {
            String chaine = textArea.getText();
            // valeur doit être entre 1 et nombreDeMots
            int valeur = new Random().nextInt(nombreDeMots) + 1;
            Paire paire = trouverPosition(chaine, valeur);
            if (paire.x >= 0 && paire.y >= paire.x && paire.y <= chaine.length()) {
                StringBuilder sb = new StringBuilder(chaine);
                sb.delete(paire.x, paire.y);
                textArea.setText(sb.toString());
                compterMots(textArea.getText());
            }
        }
    }

}