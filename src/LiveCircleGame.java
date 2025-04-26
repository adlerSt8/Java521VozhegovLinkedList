import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;

//TODO: Задача. “Слова/Города” (Живой круг)
// Исправить все ошибки в коде, чтобы игра работала верно.
// Игра в слова/города, люди называют слова по кругу,
// если человек <дальше вы придумываете условия, при которых люди будут выбывать по какой-то причине,
// пока не останется один человек – победитель>

/*
    1. Добавлены условия для выбывания игроков. Теперь у игрока есть 10 секунд, чтобы написать слово.
    2. Обработка "мягкого и твердого знака" и буквы "ы" в конце слова.
    3. Объявление победителя.
 */

public class LiveCircleGame {
    public static void main(String[] args) {
        LinkedList<String> players = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        String winner = "";
        char[] letters = {'ь', 'ъ', 'ы'};

        // Заполнение списка участников
        System.out.println("Введите имена участников игры (для завершения введите \"конец\"):");
        String name;
        while (!(name = scanner.nextLine()).equalsIgnoreCase("конец")) {
            players.add(name);
        }

        // Игровой процесс
        System.out.println("Игра начинается!");
        String lastWord = "";

        while (!players.isEmpty()) {
            String currentPlayer = players.poll(); // Получаем текущего игрока и убираем его из списка
            String inputWord;

            char lastChar = 0;

            // Просим текущего игрока назвать слово, начинающееся с последней буквы предыдущего слова
            if (!lastWord.isEmpty()) {
                lastChar = lastWord.charAt(lastWord.length() - 1);

                //Проверка символа, если слово оканчивается на 'ь', 'ъ', 'ы'
                for (char ch : letters) {
                    if (ch == lastChar && lastWord.length() > 1) {
                        lastChar = lastWord.charAt(lastWord.length() - 2);
                        break;
                    }
                }

                System.out.println(currentPlayer + ", назовите слово, начинающееся с буквы \"" + lastChar + "\":");
            } else {
                System.out.println("У вас есть 10 секунд, чтобы назвать слово");
                System.out.println(currentPlayer + ", назовите слово:");
            }

            LocalTime startTime = LocalTime.now();

            // Проверка слова
            while (true) {
                inputWord = scanner.nextLine().toLowerCase();

                if (lastWord.isEmpty() || inputWord.charAt(0) == lastChar) {
                    lastWord = inputWord;
                    break;
                } else {
                    System.out.println("Неверное слово! Попробуйте ещё раз:");
                }
            }

            LocalTime endTime = LocalTime.now();
            Duration difference = Duration.between(startTime, endTime);

            if (difference.toSeconds() <= 10) {
                players.addLast(currentPlayer);
            } else {
                System.out.println("К сожалению, прошло больше 10 секунд. Вы выбываете.");
            }

            if (players.size() == 1) {
                winner = players.get(0);
                break;
            }
        }

        // Победитель
        System.out.println("Победитель: " + winner + " !");
        System.out.println(lastWord + " - это последнее слово в игре!");
    }
}
