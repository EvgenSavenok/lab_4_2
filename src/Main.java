import java.util.Scanner;
import java.io.*;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static int inputNumber(String str, final int MAX_LIM, final int MIN_LIM) {
        int num = 0;
        boolean isIncorrect;
        do {
            System.out.println(str);
            isIncorrect = false;
            try {
                num = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Проверьте корректность введенных данных!");
                isIncorrect = true;
            }
            if (!isIncorrect && (num < MIN_LIM || num > MAX_LIM)) {
                System.out.println("Вы вышли за диапазон ввода!");
                isIncorrect = true;
            }
        } while (isIncorrect);
        return num;
    }

    public static int findNOD(int firstNumber, int secondNumber) {
        return secondNumber == 0 ? Math.abs(firstNumber) : findNOD(secondNumber, firstNumber % secondNumber);
    }

    public static String inputFilePath() {
        String path;
        boolean isIncorrect;
        do {
            isIncorrect = false;
            System.out.println("Введите путь к файлу");
            path = input.nextLine();
            File file = new File(path);
            if (!file.exists() || !file.canWrite() || !file.canRead() || !path.endsWith(".txt") || file.isDirectory()) {
                isIncorrect = true;
                System.out.println("Файл не найден, или неверный формат файла");
            }
        } while (isIncorrect);
        return path;
    }

    public static int takeNumberFromFile(String path) {
        boolean isIncorrect;
        int number = 0;
        do {
            isIncorrect = false;
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                number = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.out.println("Проверьте корректность данных в файле!");
                isIncorrect = true;
            }
            if (!isIncorrect && (number < -999 || number > 9999)) {
                System.out.println("Вы вышли за диапазон ввода!");
                isIncorrect = true;
            }
            if (isIncorrect)
                path = inputFilePath();
        } while (isIncorrect);
        return number;
    }

    public static void printNODToFile(PrintWriter fileOut, int NOD) {
        fileOut.println("Ваш НОД:");
        fileOut.println(NOD);
    }

    public static void main(String[] args) {
        String consoleChoice = "Выбор вариант ввода/вывода: 0 - консоль, 1 - файл.";
        String inputFirstNumber = "Введите первое число:";
        String inputSecondNumber = "Введите второе число:";
        int firstNumber;
        int secondNumber;
        int NOD;
        String path;
        System.out.println("Данная программа находит НОД двух чисел согласно алгоритму Евклида.");
        int num = inputNumber(consoleChoice, 1, 0);
        if (num == 0) {
            firstNumber = inputNumber(inputFirstNumber, 9999, -999);
            secondNumber = inputNumber(inputSecondNumber, 9999, -999);
        } else {
            path = inputFilePath();
            firstNumber = takeNumberFromFile(path);
            secondNumber = takeNumberFromFile(path);
            System.out.println("С файла все считано успешно!");
        }
        num = inputNumber(consoleChoice, 1, 0);
        NOD = findNOD(firstNumber, secondNumber);
        if (num == 0) {
            System.out.println("НОД равен: " + NOD);
        } else {
            path = inputFilePath();
            try (PrintWriter fileOut = new PrintWriter(path)) {
                printNODToFile(fileOut, NOD);
            } catch (Exception ignored) {}
            System.out.println("Данные успешно выведены в файл!");
        }
        input.close();
    }
}