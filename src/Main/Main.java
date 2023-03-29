package Main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));
    }

    public static String calc(String input) throws Exception {
        String[] arguments = input.split(" ");
        if (arguments.length != 3) throw new Exception("Неверный формат строки");
        switch (arguments[1]) {
            case "+": {
                return sum(arguments[0], arguments[2]);
            }
            case "-": {
                return subtraction(arguments[0], arguments[2]);
            }
            case "*": {
                return multiply(arguments[0], arguments[2]);
            }
            case "/": {
                return division(arguments[0], arguments[2]);
            }
            default:
                throw new Exception("Неверная арифметическая операция");
        }

    }

    private static String sum (String a, String b) throws Exception {
        if (isValidInteger(a) && isValidInteger(b)) {
            return String.valueOf(Integer.parseInt(a) + Integer.parseInt(b));
        }
        else if (isValidRomeNumber(a) && isValidRomeNumber(b)) {
            int result = convertRomeNumberToInteger(a) + convertRomeNumberToInteger(b);
            return convertIntegerToRomeNumber(result);
        }
        throw new Exception("Неверный формат строки");
    }

    private static String subtraction (String a, String b) throws Exception {
        if (isValidInteger(a) && isValidInteger(b)) {
            return String.valueOf(Integer.parseInt(a) - Integer.parseInt(b));
        }
        else if (isValidRomeNumber(a) && isValidRomeNumber(b)) {
            int result = convertRomeNumberToInteger(a) - convertRomeNumberToInteger(b);
            if (result < 1) throw new Exception("Результат меньше 1");
            return convertIntegerToRomeNumber(result);
        }
        throw new Exception("Неверный формат строки");
    }

    private static String division (String a, String b) throws Exception {
        if (isValidInteger(a) && isValidInteger(b)){
            return String.valueOf(Integer.parseInt(a) / Integer.parseInt(b));
        } else if (isValidRomeNumber(a) && isValidRomeNumber(b)) {
            int result = convertRomeNumberToInteger(a) / convertRomeNumberToInteger(b);
            return convertIntegerToRomeNumber(result);
        }
        throw new Exception("Неверный формат строки");
    }

    private static String multiply (String a, String b) throws Exception {
        if (isValidInteger(a) && isValidInteger(b)) {
            return String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
        }
        else if (isValidRomeNumber(a) && isValidRomeNumber(b)) {
            int result = convertRomeNumberToInteger(a) * convertRomeNumberToInteger(b);
            return convertIntegerToRomeNumber(result);
        }
        throw new Exception("Неверный формат строки");
    }

    private enum RomeNumber {
        I(1), V(5), X(10), L(50), C(100);

        int digit;
        RomeNumber(int digit) {
            this.digit = digit;
        }

        public int getDigit() {
            return digit;
        }

    }

    private static boolean isValidInteger(String text) throws Exception {
        boolean valid = false;
        try {
            if (Integer.parseInt(text) > 0 && Integer.parseInt(text) <= 10) valid = true;
            else throw new Exception("Одно или оба числа меньше 1 или больше 10");
        } catch (NumberFormatException e) {

        }
        return valid;
    }

    private static boolean isValidRomeNumber(String text) throws Exception {
        boolean valid = false;
        try {
            if (convertRomeNumberToInteger(text) <= 10) valid = true;
            else throw new Exception("Одно или оба числа больше 10");
        } catch (IllegalArgumentException e) {

        }
        return valid;
    }

    private static int[] digitToArrayOfIntegers(int digit) {
        String text = String.valueOf(digit);
        int[] draftArray = new int[text.length()];
        int count = 0;
        for (int i = 0; i<text.length(); i++) {
            draftArray[i] = Integer.parseInt(String.valueOf(text.charAt(i)));
        }

        for (int i = 0; i< draftArray.length; i++) {
            draftArray[i] = draftArray[i] * (int) Math.pow(10, draftArray.length - i - 1);
            if (draftArray[i] == 0) count++;
        }
        for (int i = 0; i < draftArray.length-1; i++) {
            int temp;
            if (draftArray[i]==0) {
                temp = draftArray[i+1];
                draftArray[i+1] = 0;
                draftArray[i] = temp;
            }
        }
        int[] result = new int[draftArray.length - count];
        for (int i = 0; i<result.length; i++) {
            result[i] = draftArray[i];
        }
        return result;
    }

    private static String convertIntegerToRomeNumber(int digit) {

        RomeNumber[] romeNumbers = RomeNumber.values();
        String result = "";
        for (int tempDigit: digitToArrayOfIntegers(digit)) {

            for (int i = romeNumbers.length - 1; i >= 0; i--) {
                if (tempDigit / romeNumbers[i].getDigit() == 1 && tempDigit % romeNumbers[i].getDigit() == 0) {
                    result += romeNumbers[i].name();
                    break;
                }
                if (tempDigit / romeNumbers[i].getDigit() == 1 && (tempDigit % romeNumbers[i].getDigit()) / romeNumbers[i-1].getDigit() == 4) {
                    result += romeNumbers[i-1].name() + romeNumbers[i+1].name();
                    break;
                }
                if (tempDigit / romeNumbers[i].getDigit() == 1 && (tempDigit % romeNumbers[i].getDigit()) / romeNumbers[i-1].getDigit() != 4) {
                    result += romeNumbers[i];
                    int x = tempDigit % romeNumbers[i].getDigit() / romeNumbers[i-1].getDigit();
                    for(int j = 0; j < x; j++) {
                        result += romeNumbers[i-1];
                    }
                    break;
                }
                if (tempDigit / romeNumbers[i].getDigit() == 4) {
                    result += romeNumbers[i].name() + romeNumbers[i+1];
                    break;
                }
                if (tempDigit / romeNumbers[i].getDigit() >= 1 && tempDigit / romeNumbers[i].getDigit() < 4) {
                    int x = tempDigit / romeNumbers[i].getDigit();
                    for (int j = 0; j < x; j++) {
                        result += romeNumbers[i];
                    }
                    break;
                }
            }
        }
        return result;
    }

    private static int convertRomeNumberToInteger(String text) {
        RomeNumber[] romeNumbers = RomeNumber.values();
        for (int i = 0; i < romeNumbers.length; i++) {
            if (romeNumbers[i].name().equals(text)) return romeNumbers[i].getDigit();
        }
        String[] array = text.split("");
        int result = RomeNumber.valueOf(array[array.length-1]).getDigit();
        for (int i = array.length - 2; i>=0; i--) {

            if (RomeNumber.valueOf(array[i]).getDigit() < RomeNumber.valueOf(array[i+1]).getDigit()) {
                result -= RomeNumber.valueOf(array[i]).getDigit();
            }
            else  result += RomeNumber.valueOf(array[i]).getDigit();
        }
        return result;
    }
    
}