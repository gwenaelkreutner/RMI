package serveur;

import impl.JoueurInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServeurInterface extends Remote {
    void playMorpion(int ligne, int colonne, char valeurBouton, boolean disable, JoueurInterface a) throws RemoteException;
    void messageAlert(String message, JoueurInterface a) throws RemoteException;
    void envoieScore(boolean nul, JoueurInterface a) throws RemoteException;
    boolean login (JoueurInterface a)throws RemoteException;
    void leave (JoueurInterface a) throws RemoteException;
    void nbJoueurTotale() throws RemoteException;
    void publish (String s)throws RemoteException;
    ArrayList getConnected() throws RemoteException;
}
