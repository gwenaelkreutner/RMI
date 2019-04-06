package controler;

import impl.AllumetteInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import serveur.ServeurInterface;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class AllumetteControler {

    @FXML private Label lblOrdi;
    @FXML private Label lblCompteur;

    @FXML private Rectangle allumette2;
    @FXML private Rectangle allumette10;
    @FXML private Rectangle allumette9;
    @FXML private Rectangle allumette8;
    @FXML private Rectangle allumette1;
    @FXML private Rectangle allumette7;
    @FXML private Rectangle allumette6;
    @FXML private Rectangle allumette5;
    @FXML private Rectangle allumette4;
    @FXML private Rectangle allumette3;
    @FXML private Rectangle allumette12;
    @FXML private Rectangle allumette13;
    @FXML private Rectangle allumette14;
    @FXML private Rectangle allumette15;
    @FXML private Rectangle allumette16;
    @FXML private Rectangle allumette17;
    @FXML private Rectangle allumette18;
    @FXML private Rectangle allumette19;
    @FXML private Rectangle allumette20;
    @FXML private Rectangle allumette21;
    @FXML private Rectangle allumette11;

    @FXML private Button butun1;
    @FXML private Button butun2;
    @FXML private Button butun3;
    @FXML private Button btnRestart;
    @FXML private Button btnDifficulte;
    @FXML private Button btnRetour;



    private int compteurAllumette;
    private AllumetteInterface obj;
    private ServeurInterface joueur;
    private boolean difficulte = true;

    ArrayList<Rectangle> ListALlumette = new ArrayList<>();

    // Initialisation avec 21 allumettes
    @FXML
    private void initialize() {

        for (Rectangle allumette : new Rectangle[]{allumette1, allumette2, allumette3, allumette4, allumette5, allumette6, allumette7, allumette8, allumette9, allumette10,
                allumette11, allumette12, allumette13, allumette14, allumette15, allumette16, allumette17, allumette18, allumette19, allumette20, allumette21}){
            ListALlumette.add(allumette);
        }

        compteurAllumette = 21;

        try {
            int port = 8000;
            obj = (AllumetteInterface) Naming.lookup ("rmi://localhost:"+port+"/allumette");
        } catch (Exception e) {
            System.out.println ("JeuClient exception: " + e);
        }
    }

    // Pour savoir combien d'allumettes on a voulu enlever
    public void onClickFirstBtn() throws RemoteException {
        humainJoue(1);
    }

    public void onClickSecondBtn() throws RemoteException {
        humainJoue(2);
    }

    public void onClickThirdBtn() throws RemoteException {
        humainJoue(3);
    }

    public void onClickDifficulte(){
        if (difficulte){
            difficulte = false;
            btnDifficulte.setText("Facile");
        } else {
            difficulte = true;
            btnDifficulte.setText("Difficile");
        }
    }

    // Retour
    @FXML
    void onClickBack(MouseEvent event) throws IOException {
        openWindow("/fxml/menu.fxml", event);
    }

    // Fonction restart qui va relancer le jeux et remettre 21 allumettes
    public void onClickRestart(){
        restart();
    }

    private void remoteAllumette(int nbAllumette) throws RemoteException {
        obj.comptAll(compteurAllumette);
        for (int i = 0; i < nbAllumette; i++){
            ListALlumette.get(compteurAllumette-1).setVisible(false);
            compteurAllumette--;
        }
    }

    // Enleve le nombre d'allumettes choisis avec gestion d'erreur quand il reste 3 / 2 / 1 allumettes
    private void humainJoue(int nbAllumette) throws RemoteException {
        remoteAllumette(nbAllumette);
        int nbJoueOrdi = obj.humainJoue(nbAllumette, difficulte);
        ordiJoue(nbJoueOrdi);

        if(compteurAllumette < 3)
            butun3.setDisable(true);
        if (compteurAllumette<2)
            butun2.setDisable(true);
        if (compteurAllumette == 0)
            butun1.setDisable(true);
    }

    // Detecte qui gagne et si il reste des allumettes combien en reste t'il
    private void ordiJoue(int nbJoueOrdi) throws RemoteException {
        if (nbJoueOrdi == -1){
            lblOrdi.setText("Vous avez gagné");
        } else if(compteurAllumette-nbJoueOrdi == 0){
            remoteAllumette(nbJoueOrdi);
            lblOrdi.setText("L'ordi a gagné");
        } else{
            lblOrdi.setText("L'ordi a joué : " + nbJoueOrdi);
            remoteAllumette(nbJoueOrdi);
        }
        lblCompteur.setText("Il vous reste : " + compteurAllumette + " allumettes");
    }

    private void restart(){
        for (int i = 0; i < ListALlumette.size(); i++){
            ListALlumette.get(i).setVisible(true);
        }
        compteurAllumette = 21;
        butun1.setDisable(false);
        butun2.setDisable(false);
        butun3.setDisable(false);
        lblOrdi.setText("Vous avez relance");
        lblCompteur.setText("Il vous reste : " + compteurAllumette + " allumettes");
    }

    // Ouvre une nouvelle fenêtre
    private void openWindow(String lien, MouseEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(lien));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}