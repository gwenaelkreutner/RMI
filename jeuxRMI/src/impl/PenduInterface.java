package impl;

import java.io.FileNotFoundException;
import java.rmi.*;

public interface PenduInterface extends Remote {

    void mot() throws RemoteException, FileNotFoundException;
    String initMot() throws RemoteException;
    String trouveLettre(String lettre) throws RemoteException;
    boolean mauvaiseLettre(String lettre) throws RemoteException;
    boolean gagne() throws RemoteException;
}