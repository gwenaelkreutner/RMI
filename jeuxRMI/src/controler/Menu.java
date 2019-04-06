package controler;

import impl.JoueurInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;


public class Menu {
    @FXML private Button btnMorpion;
    @FXML private Button btnAllumette;
    @FXML private Button btnPendu;

    private JoueurInterface obj;


    @FXML
    void onClickAllumette(MouseEvent event) throws IOException {
        openWindow("/fxml/allumette.fxml", event);
    }

    @FXML
    void onClickMorpion(MouseEvent event) throws IOException {
        openWindow("/fxml/morpion.fxml", event);
    }

    @FXML
    void onClickPendu(MouseEvent event) throws IOException {
        openWindow("/fxml/pendu.fxml", event);
    }

    public void openWindow(String lien, MouseEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(lien));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
