package controler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import impl.PenduInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class PenduControler {

    @FXML private Pane pnl;
    @FXML private Line l1;
    @FXML private Line l2;
    @FXML private Line l3;
    @FXML private Line l4;
    @FXML private Line l5;
    @FXML private Circle l6;
    @FXML private Line l7;
    @FXML private Line l8;
    @FXML private Line l9;
    @FXML private Line l10;
    @FXML private Line l11;
    @FXML Label lblmot;
    @FXML Label lblErreur;
    @FXML Button btnJouer;
    @FXML Button btnOk;
    @FXML Button btnRejoue;
    @FXML Button btnRetour;
    @FXML TextField edtLettre;

    int compteur = 0;
    private PenduInterface obj;
    ArrayList<Shape> ListTrait = new ArrayList<>();
    ArrayList<String> listeLettre = new ArrayList<String>();

    // Initialise avec les traits qui correspond aux barres pour le dessin du pendu. Ils sont tous en visible false
    @FXML
    private void initialize() throws RemoteException {
        ListTrait.add(l1);
        ListTrait.add(l2);
        ListTrait.add(l3);
        ListTrait.add(l4);
        ListTrait.add(l5);
        ListTrait.add(l6);
        ListTrait.add(l7);
        ListTrait.add(l8);
        ListTrait.add(l9);
        ListTrait.add(l10);
        ListTrait.add(l11);
        try {
            int port = 8000;
            obj = (PenduInterface) Naming.lookup("rmi://localhost:" + port + "/pendu");
            obj.mot();
        } catch (Exception e) {
            System.out.println("JeuClient exception: " + e);
        }
    }

    // Affiche le pannel afin de pouvoir jouer et prend aléatoirement un mot dans un fichier texte
    public void onClickJouer() throws RemoteException {
        pnl.setVisible(true);
        btnOk.setDisable(false);
        lblmot.setText(obj.initMot());
        btnJouer.setVisible(false);
        btnRejoue.setVisible(true);

    }

    // Choix de la lettre avec gestion des erreurs (Si une lettre a déja était choisis ou si le joueur entre plusieurs lettres d'un coup. Si toutes les lettres sont trouvées, lbl -> gagné
    public void onClickOk() throws RemoteException {

        String lettre = edtLettre.getText();
        if (lettre.length() > 1) {
            edtLettre.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("pendu");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez rentrer qu'une seule lettre");
            alert.showAndWait();
        } else {

            lblmot.setText(obj.trouveLettre(lettre));
            lblErreur.setText("bien joue");
            if (listeLettre.contains(lettre)) {
                lblErreur.setText("Lettre déja utilisé");
                edtLettre.clear();
            } else if (obj.mauvaiseLettre(lettre)) {
                edtLettre.clear();
                affiPendu();
            }
            listeLettre.add(lettre);
            edtLettre.clear();
            if (obj.gagne()) {
                lblErreur.setText("bravo tu as gagne");
                edtLettre.clear();
                btnOk.setDisable(true);
            }

        }
    }

    // Si la lettre choisis n'est pas dans le mot du pendu, on affiche une barre du dessin du pendu (avec un compteur jusqu'a ce que tout le dessin soit visible -> perdu)
    private void affiPendu() {

        lblErreur.setText("Aie raté");
        ListTrait.get(compteur).setVisible(true);
        compteur++;
        if (compteur == 11) {
            lblErreur.setText("perdu");
            btnOk.setDisable(true);
        }
    }

    @FXML
    void onClickBack(MouseEvent event) throws IOException {
        openWindow("/fxml/menu.fxml", event);
    }

    // Recommence le jeux avec le dessin du pendu en visible false et reprends un autre mot dans le fichier texte
    public void onClickRejoue() throws RemoteException, FileNotFoundException {
        obj.mot();
        rejoue();

    }

    private void rejoue() throws RemoteException {
        for (int i = 0; i < ListTrait.size(); i++) {
            ListTrait.get(i).setVisible(false);
        }
        compteur = 0;
        edtLettre.clear();
        listeLettre.clear();
        lblErreur.setText("Vous avez relance");
        lblmot.setText(obj.initMot());
        btnOk.setDisable(false);
        btnJouer.setDisable(true);
        btnRejoue.setDisable(false);
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
