package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JoueurInterface extends Remote {
    void tell (String name)throws RemoteException;
    int getnJoueur()throws RemoteException;
    void nbJoueurTotale(int nbJoueur) throws RemoteException;
    void envoieScore(boolean nul) throws RemoteException;
    void messageAlert(String message) throws RemoteException;
    void morpion(int ligne, int colonne, char valeurMorpion, boolean disable) throws RemoteException;
}
