package com.ouchadam.psr.helper;

import com.ouchadam.psr.read.PokemonFile;
import com.ouchadam.psr.read.SpeciesToPokedex;
import com.ouchadam.psr.read.domain.Money;
import com.ouchadam.psr.read.domain.Playtime;
import com.ouchadam.psr.read.domain.Pokemon;
import com.ouchadam.psr.read.domain.Team;
import com.ouchadam.psr.read.reader.MoneyReader;
import com.ouchadam.psr.read.reader.PlaytimeReader;
import com.ouchadam.psr.read.reader.TeamOffsets;
import com.ouchadam.psr.read.reader.TeamReader;
import com.ouchadam.psr.read.text.TextReader;
import com.ouchadam.psr.read.text.TextTerminatedException;
import com.ouchadam.psr.read.text.TextUnhandledException;

import java.io.IOException;

public class Dumper {

    public static void dumpAll(long start, long end, PokemonFile file) throws IOException {
        for (long index = start; index < end; index++) {
            int aByte = file.getInt(index);
            System.out.print(aByte + " ");
        }
    }

    public static void printAllText(long start, long end, PokemonFile file) {
        String text = "";
        TextReader textReader = new TextReader();
        for (long index = start; index < end; index++) {
            int saveByte = file.getInt(index);
            try {
                char c = textReader.toLetter(saveByte);
                text += c;
            } catch (TextTerminatedException e) {
                if (!text.isEmpty()) {
                    System.out.println(text + " ::: " + "Start : " + Long.toHexString(index - text.length()) + " End : " + Long.toHexString(index));
                    text = "";
                }
            } catch (TextUnhandledException e) {
                if (!text.isEmpty()) {
//                    System.out.println(saveByte);
                    text = "";
                }
            }
        }

    }

    public static void dumpMoney(PokemonFile file, Money wanted) {
        System.out.println("Start");
        for (int i = 0; i < file.length() - 3; i++) {
            MoneyReader moneyReader = new MoneyReader(i);
            try {
                Money money = moneyReader.read(file);
                if (money.equals(wanted)) {
                    System.out.println("Boom! : " + Integer.toHexString(i) + " : " + money.formatted());
                    System.out.println("");
                }
            } catch (Exception e) {
            }
        }
        System.out.println("End");
    }

    public static void dumpPlaytime(PokemonFile file, Playtime wanted) {
        System.out.println("Start");
        for (int i = 0; i < file.length() - 3; i++) {
            PlaytimeReader moneyStateReader = new PlaytimeReader(i);
            try {
                Playtime playtime = moneyStateReader.read(file);
                if (playtime.equals(wanted)) {
                    System.out.println("Boom! : " + Integer.toHexString(i) + " : " + playtime.formatted());
                    System.out.println("");
                }
            } catch (Exception e) {
            }
        }
        System.out.println("End");
    }

    public static void dumpPokemon(PokemonFile file, String name, int level) {
        System.out.println("Start");
        TextReader textReader = new TextReader();
        SpeciesToPokedex speciesToPokedex = new SpeciesToPokedex();
        for (int i = 0; i < file.length() - 3; i++) {
            TeamReader moneyStateReader = new TeamReader(textReader, speciesToPokedex, new TeamOffsets(i));
            try {
                Team team = moneyStateReader.read(file);
                Pokemon pokemon = team.pokemons.get(0);
                if (pokemon.getName().equals(name) && pokemon.getLevel() == level) {
                    System.out.println("Boom! : " + Integer.toHexString(i));
                    System.out.println("");
                    System.out.println(team);
                }
            } catch (Exception e) {
            }
        }
        System.out.println("End");
    }

}
