package impl;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

public class AllumetteImpl extends UnicastRemoteObject implements AllumetteInterface {

    public AllumetteImpl() throws RemoteException {
        super();
    }

    // Jeu des Allumettes
    int p = 3; // Nombre maximum d'allumettes autorisées
    int nbAll;

    @Override
    public void comptAll(int compteurAllumette){
        nbAll = compteurAllumette;
    }

    @Override
    public int humainJoue(int Nb, boolean difficulte) throws RemoteException {
        nbAll = nbAll - Nb; // On retire le Nb d'allumettes
        System.out.println(nbAll); // et on affiche les allumettes
        // S'il n'y a plus d'allumettes, le joueur a gagné !
        // Et on lui dit Bravo puis on recharge la page (nouvelle partie)
        if(nbAll==0){
            return -1;
        }
        // Sinon, l'ordinateur joue à son tour
        if(nbAll!=0){
            return ordiJoue(difficulte);
        }
        return nbAll;// Si le jeu n'est pas fini, au PC de jouer
    }

    @Override
    public int ordiJoue(boolean difficulte) throws RemoteException {
        double Nall;
        if (difficulte){
            // On calcule le modulo à 4 (il faut toujours laisser une allumette de plus
            // que le maximum autorisé afin de pouvoir gagner donc 4 (p+1)
            Nall = nbAll - (Math.floor(nbAll/(p+1))*(p+1)); // ou Nall = N % 4
            // Si ce modulo est égal à zéro, on tire au hasard entre 1 et 3 allumettes
            if(Nall==0) {
                Nall = 1 + Math.floor(Math.random() * p);
            }
        } else{
            Random rand = new Random();
            Nall = rand.nextInt((p - 1) + 1) + 1;
        }
        nbAll = (int) (nbAll - Nall); // On retire le Nb d'allumettes
        System.out.println(nbAll); // et on affiche les allumettes
        // S'il n'y a plus d'allumettes, l'ordinateur a gagné
        if(nbAll==0) {
            return (int) Nall;
        } else {
            return (int) Nall;
        }

    }
}