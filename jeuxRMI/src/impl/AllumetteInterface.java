package impl;

import java.rmi.*;

/* Remote Interface HelloInterface pour l'application Hello */
public interface AllumetteInterface extends Remote {
    int humainJoue(int Nb, boolean difficulte) throws RemoteException;
    int ordiJoue(boolean difficulte) throws RemoteException;

    void comptAll(int compteurAllumette) throws RemoteException;
}