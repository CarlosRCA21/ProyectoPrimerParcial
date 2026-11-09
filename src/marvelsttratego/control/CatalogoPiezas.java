package marvelsttratego.control;

import marvelsttratego.modelo.Agente;
import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.Mistico;
import marvelsttratego.modelo.Pieza;
import marvelsttratego.modelo.PiezaBomba;
import marvelsttratego.modelo.PiezaMovil;
import marvelsttratego.modelo.PiezaTierra;
import marvelsttratego.modelo.Scout;

public class CatalogoPiezas {

    public static final int TOTAL_PIEZAS = 40;

    private static final int[] RANGOS = {
        10, 9, 8, 8, 7, 7, 7, 6,
        6, 6, 6, 5, 5, 5, 5, 4,
        4, 4, 4, 3, 3, 3, 3, 3,
        2, 2, 2, 2, 2, 2, 2, 2,
        1
    };

    private static final String[] NOMBRES_HEROES = {
        "Mr. Fantastic", "Captain America", "Professor X", "Nick Fury",
        "Spider-Man", "Wolverine", "Namor", "Daredevil",
        "Silver Surfer", "Hulk", "Iron Man", "Thor",
        "Human Torch", "Cyclops", "Invisible Woman", "Ghost Rider",
        "Punisher", "Blade", "Thing", "Emma Frost",
        "She-Hulk", "Giant Man", "Beast", "Colossus",
        "Gambit", "Spider-Girl", "Ice Man", "Storm",
        "Phoenix", "Dr. Strange", "Elektra", "Nightcrawler",
        "Black Widow"
    };

    private static final String[] NOMBRES_VILLANOS = {
        "Dr. Doom", "Galactus", "Kingpin", "Magneto",
        "Apocalypse", "Green Goblin", "Venom", "Bullseye",
        "Omega Red", "Onslaught", "Red Skull", "Mystique",
        "Mysterio", "Dr. Octopus", "Deadpool", "Abomination",
        "Thanos", "Black Cat", "Sabretooth", "Juggernaut",
        "Rhino", "Carnage", "Mole Man", "Lizard",
        "Mr. Sinister", "Sentinel 1", "Ultron", "Sandman",
        "Leader", "Viper", "Sentinel 2", "Electro",
        "Black Widow"
    };

    public Pieza[] crearPiezas(Bando bando) {
        String[] nombres = NOMBRES_VILLANOS;
        String nombreBomba = "Pumpkin Bomb";
        if (bando == Bando.HEROES) {
            nombres = NOMBRES_HEROES;
            nombreBomba = "Nova Blast";
        }

        Pieza[] piezas = new Pieza[TOTAL_PIEZAS];
        for (int i = 0; i < RANGOS.length; i++) {
            if (RANGOS[i] == Agente.RANGO) {
                piezas[i] = new Agente(nombres[i], bando);
            } else if (RANGOS[i] == Mistico.RANGO) {
                piezas[i] = new Mistico(nombres[i], bando);
            } else if (RANGOS[i] == Scout.RANGO) {
                piezas[i] = new Scout(nombres[i], bando);
            } else {
                piezas[i] = new PiezaMovil(nombres[i], RANGOS[i], bando);
            }
        }
        for (int i = RANGOS.length; i < TOTAL_PIEZAS - 1; i++) {
            piezas[i] = new PiezaBomba(nombreBomba, bando);
        }
        piezas[TOTAL_PIEZAS - 1] = new PiezaTierra(bando);
        return piezas;
    }
}
