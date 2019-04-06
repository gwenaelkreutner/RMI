package impl;

import java.rmi.*;
import java.rmi.server.*;

public class MorpionImpl extends UnicastRemoteObject implements MorpionInterface {

    private char[][] morpion;
    private char currentPlayer;


    public MorpionImpl() throws RemoteException {
        super();
        morpion = new char[3][3];
    }

    //initialiser le morpion
    public void initMorpion() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                morpion[i][j] = '-';
            }
        }
    }

    //verifier si le morpion est compler
    public boolean morpionFull() {
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (morpion[i][j] == '-') {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

    //Verifier les victoire pour les diagonales, colonnes et ligne
    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(morpion[i][0], morpion[i][1], morpion[i][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(morpion[0][i], morpion[1][i], morpion[2][i])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(morpion[0][0], morpion[1][1], morpion[2][2])) || (checkRowCol(morpion[0][2], morpion[1][1], morpion[2][0])));
    }

    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    //placer la case dans le morpion
    public boolean placeCase(int row, int col, char valeurBouton) {
        currentPlayer = valeurBouton;
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (morpion[row][col] == '-') {
                    morpion[row][col] = currentPlayer;
                    return true;
                }
            }
        }

        return false;
    }
}