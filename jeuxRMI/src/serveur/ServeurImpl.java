package serveur;

import impl.JoueurInterface;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
public class ServeurImpl  extends UnicastRemoteObject implements ServeurInterface{

    private ArrayList<JoueurInterface> joueur = new ArrayList<>();
    private int nbJoueur = 0;
    public ServeurImpl() throws RemoteException{}

    public boolean login(JoueurInterface a) throws RemoteException{
        nbJoueur++;
        System.out.println(a.getnJoueur() + "  s'est connecté...");
        a.tell("Vous etes connectez");
        publish(a.getnJoueur()+ " viens de se connecter.");
        System.out.print(nbJoueur + "\n");
        joueur.add(a);
        return true;
    }

    public void leave(JoueurInterface a) throws RemoteException{
        nbJoueur--;
        System.out.println(a.getnJoueur() + "deconnecter....");
        for(int i=0;i<joueur.size();i++){
            if (joueur.get(i).getnJoueur() == a.getnJoueur()){
                try{
                    joueur.remove(i);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void nbJoueurTotale(){
        for(int i=0;i<joueur.size();i++){
            try{
                JoueurInterface tmp= joueur.get(i);
                tmp.nbJoueurTotale(nbJoueur);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void playMorpion(int ligne, int colonne, char valeurBouton, boolean disable, JoueurInterface a) throws RemoteException{
        for(int i=0;i<joueur.size();i++){
            if (joueur.get(i).getnJoueur() != a.getnJoueur()){
                try{
                    JoueurInterface tmp= joueur.get(i);
                    tmp.morpion(ligne, colonne, valeurBouton, disable);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void messageAlert(String message, JoueurInterface a) throws RemoteException {
        for(int i=0;i<joueur.size();i++){
            if (joueur.get(i).getnJoueur() != a.getnJoueur()){
                try{
                    JoueurInterface tmp= joueur.get(i);
                    tmp.messageAlert(message);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void envoieScore(boolean nul, JoueurInterface a) throws RemoteException {
        for(int i=0;i<joueur.size();i++){
            if (joueur.get(i).getnJoueur() != a.getnJoueur()){
                try{
                    JoueurInterface tmp= joueur.get(i);
                    tmp.envoieScore(nul);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void publish(String s) throws RemoteException{
        System.out.println(s);
        for(int i=0;i<joueur.size();i++){
            try{
                JoueurInterface tmp= joueur.get(i);
                tmp.tell(s);
            }catch(Exception ignored){
            }
        }
    }
    public ArrayList<JoueurInterface> getConnected() throws RemoteException{
        return joueur;
    }
}
