package serveur;

import impl.AllumetteImpl;
import impl.MorpionImpl;
import impl.PenduImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Serveur {
    public static void main(String[] argv) {

        int port = 8000;

        try {
            ServeurInterface  joueur = new ServeurImpl();
            LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://localhost:"+port+"/joueur", joueur);
            Naming.rebind("rmi://localhost:" + port + "/morpion", new MorpionImpl());
            Naming.rebind("rmi://localhost:" + port + "/allumette", new AllumetteImpl());
            Naming.rebind("rmi://localhost:" + port + "/pendu", new PenduImpl());
            System.out.println("Serveur prêt");
        } catch (Exception e) {
            System.out.println("Echec du serveur" + e);
        }
    }
}
