import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Tim> timList = new ArrayList<>();

        // Memastikan jumlah tim tetap 4
        while (timList.size() < 4) {
            System.out.print("Masukkan nama tim " + (timList.size() + 1) + ": ");
            String namaTim = scanner.next();

            // Memastikan nama tim unik
            if (timList.stream().noneMatch(tim -> tim.getNama().equals(namaTim))) {
                timList.add(new Tim(namaTim));
            } else {
                System.out.println("Nama tim sudah digunakan. Gunakan nama tim lain.");
            }
        }

        System.out.println("Pilih mode turnamen:");
        System.out.println("1. Liga");
        System.out.println("2. Piala");
        int mode = scanner.nextInt();

        if (mode == 1) {
            // Mode Liga
            List<Pertandingan> pertandinganList = generatePertandinganLiga(timList);
            inputHasilLiga(scanner, pertandinganList);
            tampilkanKlasemen(timList);
        } else if (mode == 2) {
            // Mode Piala
            cupTournament(scanner, timList);
        } else {
            System.out.println("Mode tidak valid.");
        }

        scanner.close();
    }

    private static List<Pertandingan> generatePertandinganLiga(List<Tim> timList) {
        List<Pertandingan> pertandinganList = new ArrayList<>();

        for (int i = 0; i < timList.size(); i++) {
            for (int j = 0; j < timList.size(); j++) {
                if (i != j) {
                    pertandinganList.add(new Pertandingan(timList.get(i), timList.get(j)));
                }
            }
        }

        return pertandinganList;
    }

    private static List<PertandinganPiala> generateBracket(List<Tim> timList) {
        List<PertandinganPiala> bracket = new ArrayList<>();

        for (int i = 0; i < timList.size(); i += 2) {
            PertandinganPiala pertandingan = new PertandinganPiala(timList.get(i), timList.get(i + 1));
            bracket.add(pertandingan);
        }

        return bracket;
    }

    private static void inputHasilLiga(Scanner scanner, List<Pertandingan> pertandinganList) {
        // Memasukkan hasil pertandingan untuk mode Liga
        for (Pertandingan pertandingan : pertandinganList) {
            System.out.println("Hasil pertandingan antara " + pertandingan.getTimA().getNama() +
                    " dan " + pertandingan.getTimB().getNama() + ":");
            System.out.print("Masukkan gol " + pertandingan.getTimA().getNama() + ": ");
            int golA = scanner.nextInt();
            System.out.print("Masukkan gol " + pertandingan.getTimB().getNama() + ": ");
            int golB = scanner.nextInt();

            pertandingan.hasil(golA, golB);

            // Update poin tim
            if (pertandingan.getPemenang() != null) {
                pertandingan.getPemenang().tambahPoin(3);
            }
        }
    }

    private static void inputHasilPertandinganPiala(Scanner scanner, List<PertandinganPiala> bracket) {
        // Memasukkan hasil pertandingan untuk mode Piala
        for (PertandinganPiala pertandingan : bracket) {
            System.out.println("Hasil pertandingan antara " + pertandingan.getTimA().getNama() +
                    " dan " + pertandingan.getTimB().getNama() + ":");
            System.out.print("Masukkan gol " + pertandingan.getTimA().getNama() + ": ");
            int golA = scanner.nextInt();
            System.out.print("Masukkan gol " + pertandingan.getTimB().getNama() + ": ");
            int golB = scanner.nextInt();

            pertandingan.hasil(golA, golB);

            // Update poin tim
            if (pertandingan.getPemenang() != null) {
                pertandingan.getPemenang().tambahPoin(3);
            }
        }
    }

    private static Tim tentukanPemenangBracket1(PertandinganPiala pertandingan1, PertandinganPiala pertandingan2) {
        // Menentukan tim yang maju ke bracket 2 dari bracket 1
        Tim timMaju;
        if (pertandingan1.getPemenang().getNama().equals(pertandingan2.getTimA().getNama()) ||
                pertandingan1.getPemenang().getNama().equals(pertandingan2.getTimB().getNama())) {
            timMaju = pertandingan1.getPemenang();
        } else {
            timMaju = pertandingan2.getPemenang();
        }

        return timMaju;
    }

    private static Tim tentukanPemenangBracket2(List<PertandinganPiala> bracket2) {
        // Menentukan tim yang maju ke final dari bracket 2
        return bracket2.get(0).getPemenang();
    }

    private static void tampilkanKlasemen(List<Tim> timList) {
        // Menampilkan klasemen akhir dengan peringkat
        Collections.sort(timList, Comparator.comparingInt(Tim::getPoin).reversed());
        System.out.println("===== Klasemen Akhir =====");
        for (int i = 0; i < timList.size(); i++) {
            System.out.println((i + 1) + ". " + timList.get(i).getNama() + ": " + timList.get(i).getPoin() + " poin");
        }
    }

    private static void cupTournament(Scanner scanner, List<Tim> timList) {
        PertandinganPiala pertandingan1 = new PertandinganPiala(timList.get(0), timList.get(1));
        PertandinganPiala pertandingan2 = new PertandinganPiala(timList.get(2), timList.get(3));

        // Input results for the initial matchups
        List<PertandinganPiala> pertandinganList = new ArrayList<>();
        pertandinganList.add(pertandingan1);
        pertandinganList.add(pertandingan2);
        inputHasilPertandinganPiala(scanner, pertandinganList);

        // Determine Upper Bracket and Lower Bracket teams
        Tim upperBracketWinner, upperBracketLoser, lowerBracketWinner, lowerBracketLoser;

        if (pertandingan1.getPemenang() == timList.get(0)) {
            upperBracketWinner = timList.get(0);
            lowerBracketWinner = timList.get(1);
        } else {
            upperBracketWinner = timList.get(1);
            lowerBracketWinner = timList.get(0);
        }

        if (pertandingan2.getPemenang() == timList.get(2)) {
            upperBracketLoser = timList.get(2);
            lowerBracketLoser = timList.get(3);
        } else {
            upperBracketLoser = timList.get(3);
            lowerBracketLoser = timList.get(2);
        }

        // Grand Final
        PertandinganPiala grandFinal = new PertandinganPiala(upperBracketWinner, upperBracketLoser);
        List<PertandinganPiala> grandFinalList = new ArrayList<>();
        grandFinalList.add(grandFinal);
        inputHasilPertandinganPiala(scanner, grandFinalList);

        Tim juara, runnerUp, peringkat3, peringkat4;

        // Determine positions
        if (grandFinal.getPemenang() == upperBracketWinner) {
            juara = grandFinal.getPemenang();
            runnerUp = upperBracketLoser;
        } else {
            juara = upperBracketLoser;
            runnerUp = grandFinal.getPemenang();
        }

        // Determine Lower Bracket Final
        PertandinganPiala lowerBracketFinal = new PertandinganPiala(lowerBracketWinner, lowerBracketLoser);
        List<PertandinganPiala> lowerBracketFinalList = new ArrayList<>();
        lowerBracketFinalList.add(lowerBracketFinal);
        inputHasilPertandinganPiala(scanner, lowerBracketFinalList);

        // Determine Peringkat 3 dan 4
        if (lowerBracketFinal.getPemenang() == lowerBracketWinner) {
            peringkat3 = lowerBracketFinal.getPemenang();
            peringkat4 = lowerBracketLoser;
        } else {
            peringkat3 = lowerBracketLoser;
            peringkat4 = lowerBracketFinal.getPemenang();
        }

        // Display final positions
        System.out.println("===== Hasil Turnamen Cup =====");
        System.out.println("Peringkat 1: " + juara.getNama());
        System.out.println("Peringkat 2: " + runnerUp.getNama());
        System.out.println("Peringkat 3: " + peringkat3.getNama());
        System.out.println("Peringkat 4: " + peringkat4.getNama());
    }
}