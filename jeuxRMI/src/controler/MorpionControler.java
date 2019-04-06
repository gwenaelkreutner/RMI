package controler;

import impl.JoueurImpl;

import impl.MorpionInterface;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import serveur.ServeurInterface;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;

public class MorpionControler {

    @FXML private Button btnOne;
    @FXML private Button btnTwo;
    @FXML private Button btnThree;
    @FXML private Button btnFour;
    @FXML private Button btnFive;
    @FXML private Button btnSix;
    @FXML private Button btnSeven;
    @FXML private Button btnEight;
    @FXML private Button btnNine;
    @FXML private Button btnRetour;
    @FXML private Button btnPlay;

    @FXML private Label lblResult;
    @FXML private Label lblGagne;
    @FXML private Label lblNul;
    @FXML private Label lblLose;
    @FXML private GridPane gridPane;

    private int port = 8000;
    private int nbJoueur = 0;
    private int nbWin = 0, nbLose = 0, nbNull = 0;

    private char valeurBouton = '-';
    private char currentPlayer;

    private JoueurImpl joueur;
    private MorpionInterface morpion;
    private ServeurInterface server;

    // Initialise un compteur avec les joueurs connectés. Dès qu'un joueur vient, il obtient un id
    @FXML private void initialize() throws RemoteException {
        try {
            server = (ServeurInterface) Naming.lookup("rmi://localhost:"+port+"/joueur");
            morpion = (MorpionInterface) Naming.lookup("rmi://localhost:"+port+"/morpion");
            Random r = new Random();
            int idJoueur = r.nextInt((99999 - 10000) + 1);
            joueur = new JoueurImpl(idJoueur);
            joueur.setGUI(this);
            server.login(joueur);
            nbJoueur = server.getConnected().size();
            server.nbJoueurTotale();
            morpion.initMorpion();
            currentPlayer = currentPlayer(nbJoueur);
            lancerJeu();
            /*
            Timeline time = new Timeline(
                    new KeyFrame(Duration.millis(500),event -> {
                    lancerJeu();
                    })
            );
            time.setCycleCount(Timeline.INDEFINITE);
            time.setAutoReverse(true);
            time.play();
            */
        }
        catch (Exception e) {
            System.out.println ("JeuClient exception: " + e);
        }
    }

    @FXML
    void onClickPlay(MouseEvent event) throws RemoteException {
        relancer();
    }

    // Envoie le morpion au serveur
    private void sendMorpion(int ligne, int colonne, char vBouton, boolean disable){
        try {
            ((Button)gridPane.getChildren().get(colonne+ligne*3)).setText(String.valueOf(valeurBouton));
            server.playMorpion(ligne, colonne, vBouton, disable, joueur);
            gridPane.setDisable(disable);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Recois du joueurInterface le morpion avec la case jouée
    public void placerCase(int ligne, int colonne, char vBouton, boolean disable){
        Platform.runLater(()->{
            ((Button)gridPane.getChildren().get(colonne+ligne*3)).setText(String.valueOf(vBouton));
            ((Button)gridPane.getChildren().get(colonne+ligne*3)).setDisable(disable);
            gridPane.setDisable(!disable);
        });
    }

    // Compteur indiquant le nombre de joueurs
    public void nbJoueur(int nbJoueurTotale){
        nbJoueur = nbJoueurTotale;
        Platform.runLater(()->{
            lblResult.setText("Joueur(s) : " + nbJoueurTotale);
            lancerJeu();
        });
    }

    // Compteur avec le nombre de défaite et de victoire pour les différents joueurs se mettant a jour auyomatiquement
    public void envoieScore(boolean nul){
        Platform.runLater(()->{
            if (nul){
                nbNull += 1;
                lblNul.setText("Nul : " + nbNull);
            }
            else{
                nbLose += 1;
                lblLose.setText("Defaite : " + nbLose);
            }
            gridPane.setDisable(true);
        });
    }

    public void messageAlert(String message){
        Platform.runLater(()->{
            alert(message);
            gridPane.setDisable(true);
        });
    }

    @FXML
    void onClickBack(MouseEvent event) throws IOException {
        server.leave(joueur);
        server.nbJoueurTotale();
        relancer();
        openWindow("/fxml/menu.fxml", event);
    }


    @FXML
    void onClickCase(MouseEvent event){
        valeurBouton = currentPlayer;
        try {
            // Récupère la position de la case sur laquelle le joueur a cliqué
            Button boutonClique = (Button)event.getSource();
            int x = gridPane.getRowIndex(boutonClique);
            int y = gridPane.getColumnIndex(boutonClique);
            boutonClique.setDisable(true);
            // On mets une croix ou un rond sur la case qui a été choisis
            morpion.placeCase(x,y,valeurBouton);
            sendMorpion(x,y, valeurBouton, true);
            // On disable le pannel afin que le joueur qui a joué ne peux plus jouer
            gridPane.setDisable(true);
            // on verifie si il a gagné
            if (morpion.checkForWin()){
                nbWin += 1;
                gridPane.setDisable(true);
                lblGagne.setText("Victoire : " + nbWin);
                server.envoieScore(false, joueur);
                //server.messageAlert("Vous avez perdu",  joueur);
                //alert("Vous avez gagné");
            }else if (morpion.morpionFull()){
                server.envoieScore(true, joueur);
                nbNull += 1;
                lblNul.setText("Nul : " + nbNull);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //
    private char currentPlayer(int nbJoueur){
        if (nbJoueur % 2 == 0)
            return 'X';
        else
            return 'O';
    }

    private void openWindow(String lien, MouseEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(lien));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Morpion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //lancer si nbJoueur superieur 1
    private void lancerJeu(){
        try {
            if (nbJoueur > 1)
                gridPane.setDisable(false);
            else
                gridPane.setDisable(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void relancer() throws RemoteException {
        valeurBouton = '-';
        morpion.initMorpion();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridPane.getChildren().get(i+j*3).setDisable(false);
                sendMorpion(i,j,'-', false);
            }
        }
        valeurBouton = currentPlayer;
        lancerJeu();
    }
}
