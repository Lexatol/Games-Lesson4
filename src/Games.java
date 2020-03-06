import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class Games {

    private static final char DOT_X = 'X';
    private static int stepCounter = 0;
    private static final char DOT_O = 'O';
    private static final char DEFAULT = '_';
    private static final Random rnd = new Random();
    private static int size;

    public static void main(String[] args) throws InterruptedException {
        boolean isInSize = true;
        Scanner in = new Scanner(System.in);
        while (isInSize) {
            System.out.print("Введите размер поля: ");
            size = in.nextInt();
            if (size < 3) {
                System.out.println("Размер поля не должен быть меньше трех");
            } else {
                isInSize = false;
            }
        }
        char[][] tab = new char[size][size];
        initTable(tab);
        boolean isInGame = true;

//        System.out.println("Выберите схему игры:");
//        System.out.println("1. Человек - Компьютер");
//        System.out.println("2. Человек - Человек");
//        int sch = in.nextInt();
        while (isInGame) {
            System.out.println("Введите номер строки и номер столбца для хода");
            print(tab);
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            if (isValidStep(x, y, tab)) {

                tab[x][y] = DOT_X;
                if (isWin(DOT_X, tab)) {
                    print(tab);
                    System.out.println("Вы победили");
                    return;
                }
                stepCounter++;
                if (stepCounter == size * size) {
                    print(tab);
                    System.out.println("Ничья");
                    return;
                }
                movePC(tab);
                System.out.println("Ход противника");
                for (int i = 0; i < 7; i++) {
                    System.out.print(" * ");
                    Thread.sleep(500);
                }
                System.out.println();
                if (isWin(DOT_O, tab)) {
                    print(tab);
                    System.out.println("Вы проиграли");
                    return;
                }
            }
        }
    }


    private static boolean isWin(char ch, char[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            int sx = 0, sy = 0, d1 = 0, d2 = 0;
            for (int j = 0; j < tab.length; j++) {
                sx += tab[i][j] == ch ? 1 : 0;
                sy += tab[j][i] == ch ? 1 : 0;
                d1 += tab[j][j] == ch ? 1 : 0;
                d2 += tab[j][tab.length - j - 1] == ch ? 1 : 0;
                if (sx == tab.length || sy == tab.length || d1 == tab.length
                        || d2 == tab.length) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void movePC(char[][] tab) {
        int x = 0, y = 0, sx = 0, sy = 0, d1 = 0, d2 = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                if (tab[i][j] == DEFAULT) {
                    tab[i][j] = DOT_X;
                }
                sx += tab[i][j] == size ? 1 : 0;
                sy += tab[j][i] == size ? 1 : 0;
                d1 += tab[j][j] == size ? 1 : 0;
                d2 += tab[j][tab.length - j - 1] == size ? 1 : 0;
                if (sx == tab.length || sy == tab.length || d1 == tab.length
                        || d2 == tab.length) {
                    x = i;
                    y = j;
                } else {
                    x = rnd.nextInt(tab.length);
                    y = rnd.nextInt(tab.length);
                }


            }



            if (isValidStep(x, y, tab)) {
                tab[x][y] = DOT_O;
                stepCounter++;
                return;
            }
        }

    }

    private static boolean isValidStep(int x, int y, char[][] tab) {
        int size = tab.length;
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return tab[x][y] == DEFAULT;
        }
        return false;
    }

    /*   1 2 3
     * 1 |_|_|_|
     * 2 |_|_|_|
     * 3 |_|_|_|
     *
     */
    private static void print(char[][] tab) {
        System.out.print("   ");
        for (int i = 0; i < tab.length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < tab.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < tab.length; j++) {
                System.out.print("|" + tab[i][j]);
            }
            System.out.println("|");
        }
    }

    private static void initTable(char[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                tab[i][j] = DEFAULT;
            }
        }
    }
}