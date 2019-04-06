package impl;

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PenduImpl extends UnicastRemoteObject implements PenduInterface {

    public PenduImpl() throws RemoteException {
        super();
    }

    String mot, motTiret;
    char[] trait;
    boolean trouve, bravo;
    ArrayList<Character> listeLettre = new ArrayList<Character>();


    public void mot() throws RemoteException, FileNotFoundException {
        Scanner s = new Scanner(new File("mot.txt"));
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();
        Random r = new Random();
        int nMot = r.nextInt((list.size() -1) +1);
        mot = list.get(nMot);
        trait = new char[mot.length()];
    }

    public String initMot() throws RemoteException {
        int i = 0;
        while (i < mot.length()) {
            trait[i] = '_';
            if (mot.charAt(i) == ' ') {
                trait[i] = ' ';
            }
            i++;
        }
        String mot = String.valueOf(trait);
        return mot;
    }


    public String trouveLettre(String lettre) throws RemoteException {
        char l = lettre.charAt(0);
        listeLettre.add(l);
        int i = 0;
        System.out.println(mot);
        System.out.println(lettre);
        while (i < mot.length()) {
            if (mot.charAt(i) == ' ') {
                trait[i] = ' ';
            }
            if (mot.contains(l + "")) {
                if (mot.charAt(i) == l) {
                    trait[i] = l;
                }
            }
            i++;
        }
        System.out.println(trait);
        return motTiret = String.valueOf(trait);

    }

    public boolean mauvaiseLettre(String lettre) throws RemoteException {
        char l = lettre.charAt(0);
        listeLettre.add(l);
        int i = 0;
        while (i < mot.length()) {

            if (mot.contains(l + "")) {
                if (mot.charAt(i) == l) {
                    trouve = false;
                }
            } else {
                trouve = true;
            }

            i++;
        }
        return trouve;
    }

    public boolean gagne() throws RemoteException {
        if (!motTiret.contains("_"))
            return true;
        return false;
    }

}
