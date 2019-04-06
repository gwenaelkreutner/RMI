package impl;

import controler.MorpionControler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JoueurImpl extends UnicastRemoteObject implements JoueurInterface {

    private int nJoueur;
    private MorpionControler morpion;

    // JOUEUR

    public JoueurImpl(int n) throws RemoteException {
        nJoueur=n;
    }

    public int getnJoueur() {
        return nJoueur;
    }

    public void nbJoueurTotale(int nbJoueur){
        morpion.nbJoueur(nbJoueur);
    }

    @Override
    public void messageAlert(String message) throws RemoteException {
        morpion.messageAlert(message);
    }

    public void envoieScore(boolean nul) throws RemoteException{
        morpion.envoieScore(nul);
    }

    public void morpion(int ligne, int colonne, char valeurMorpion, boolean disable) throws RemoteException{
        morpion.placerCase(ligne, colonne, valeurMorpion, disable);
    }

    public void tell(String st) throws RemoteException{
        System.out.println(st);
    }

    public void setGUI(MorpionControler t){
        morpion=t ;
    }
}
