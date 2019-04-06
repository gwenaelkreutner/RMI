package impl;

import java.rmi.*;

/* Remote Interface HelloInterface pour l'application Hello */
public interface MorpionInterface extends Remote {
    void initMorpion () throws RemoteException;
    boolean placeCase(int row, int col, char valeurBouton) throws RemoteException;
    boolean morpionFull() throws RemoteException;
    boolean checkForWin() throws RemoteException;
}