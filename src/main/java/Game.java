import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

    private boolean playersTurn;
    private String youName;
    private int youScope;
    private int foreignScope;

    private Scanner scanner;

    private int countMyOneDecShips = 0;
    private int countMyTwoDecShips = 0;
    private int countMyThreeDecShips = 0;
    private int countMyFourDecShips = 0;

    private final int MAX_SIZE_ONE_DECK_SHIPS = 4;
    private final int MAX_SIZE_TWO_DECK_SHIPS = 3;
    private final int MAX_SIZE_THREE_DECK_SHIPS = 2;
    private final int MAX_SIZE_FOUR_DECK_SHIPS = 1;

    private int countForeignOneDecShips = 0;
    private int countForeignTwoDecShips = 0;
    private int countForeignThreeDecShips = 0;
    private int countForeignFourDecShips = 0;


    private Field field;


    public void infoGame() {
        System.out.println("У вас в консоле есть два поля, первое - ваше, второе - врага. Статистика показывает кол-во ваших очков");
        System.out.println("(Кол-во клеток, в которые вы попали) и статистика вашего врага. При уничтожении всех кораблей игра заканчивается");
        System.out.println("Обозначения: # - клетка корабля, 0 - значит,что противник стрелял,но не попал по вашему кораблю");
        System.out.println("x - значит,что противник попал в клетку с кораблем. У противника: 0 - закрытое поле, отсутствие символа");
        System.out.println("в клетке - промах, значок x - попадание");
    }

    public void foreignAutoComplite() {
        while (countForeignFourDecShips != MAX_SIZE_FOUR_DECK_SHIPS) {

            if (field.addForeignShip(oneDeckShip.getRandomShip(4))) {
                countForeignFourDecShips++;
            }

        }

        while (countForeignThreeDecShips != MAX_SIZE_THREE_DECK_SHIPS) {

            if (field.addForeignShip(oneDeckShip.getRandomShip(3))) {
                countForeignThreeDecShips++;
            }


        }

        while (countForeignTwoDecShips != MAX_SIZE_TWO_DECK_SHIPS) {

            if (field.addForeignShip(oneDeckShip.getRandomShip(2))) {
                countForeignTwoDecShips++;
            }


        }

        while (countForeignOneDecShips != MAX_SIZE_ONE_DECK_SHIPS) {

            if (field.addForeignShip(oneDeckShip.getRandomShip(1))) {
                countForeignOneDecShips++;
            }


        }
    }

    public void welcome() {
        System.out.println("Приветствую в говноигре под названием МОРСКОЙ БОЙ,написанной умственно отсталым паралимпийцем");
        System.out.println("Введите ваш ник. Не вводите слишком длинный ник, а то поле уедет и все сломается :( ");
        this.youName = this.scanner.next();
        System.out.println("Привет, " + youName);
        field.initField(youName);


    }

    public void refreshFieldGame() {
        field.renderField(youScope, foreignScope);
    }

    public void startGame() throws InterruptedException {

        welcome();
        infoGame();
        System.out.println("Для начала игры введите 1, Программа автоматически сгенерирует ваше поле и поле противника. Что-бы спрятать весь этот ужас и больше никогда не возвращаться");
        System.out.println("можно просто закрыть это окошко и даже вводить ничего не надо :)");
        int i = scanner.nextInt();
        if (i == 1) {
            autoComplite();
            foreignAutoComplite();
            refreshFieldGame();
            while (true) {
                if (playersTurn) {
                    int x;
                    int y;
                    System.out.println("Ваш ход. Введите координату x:");

                    while (!(scanner.hasNextInt() && (x = scanner.nextInt()) <= 9 && x >= 0)) {

                        System.out.println("Ввод не валиден, введите число от 0 до 9");
                        System.out.println("Ваш ход. Введите координату x:");
                        scanner.nextLine();

                    }

                    System.out.println("Координата y:");
                    while (!(scanner.hasNextInt() && (y = scanner.nextInt()) <= 9 && y >= 0)) {
                        System.out.println("Ввод не валиден, введите число от 0 до 9");
                        System.out.println("Ваш ход. Введите координату y:");
                        scanner.nextLine();
                    }

                    System.out.println("Стреляем в (" + x + ",  " + y + ")");
                    ShotStatus youShotStatus = field.fireOnForeignShips(x, y);
                    if (youShotStatus == ShotStatus.HITTING) {
                        System.out.println("Вы попали");
                        youScope++;
                    } else if (youShotStatus == ShotStatus.REPEATSHOT) {
                        System.out.println("Вы туда уже стреляли");
                    } else if (youShotStatus == ShotStatus.MISS) {
                        System.out.println("Мимо :(");
                        playersTurn = !playersTurn;
                    }

                    TimeUnit.SECONDS.sleep(2);
                } else if (playersTurn == false) {
                    System.out.println("Ход компьютера");
                    Random rnd = new Random();
                    int x = rnd.nextInt(10);
                    int y = rnd.nextInt(10);
                    ShotStatus botShotStatus = field.fireOnMyShips(x, y);
                    System.out.println("Компьютер стреляет :" + x + " " + y);
                    if (botShotStatus == ShotStatus.HITTING) {
                        System.out.println("Компьютер попал");
                        foreignScope++;
                    } else if (botShotStatus == ShotStatus.REPEATSHOT) {
                        System.out.println("Стрельнул туда-же");
                    } else if (botShotStatus == ShotStatus.MISS) {
                        System.out.println("Компьютер промахнулся");
                        playersTurn = !playersTurn;
                    }

                    TimeUnit.SECONDS.sleep(4);
                }
                refreshFieldGame();
                if (youScope == 20) {
                    System.out.println("Вы победили");
                    break;
                }
                if (foreignScope == 20) {
                    System.out.println("Увы,победил компьютер");
                    break;
                }

            }
            System.out.println("Игра закончена");


        }


    }

    public Game() {
        this.field = new Field(0);
        this.scanner = new Scanner(System.in);
        this.youScope = 0;
        this.foreignScope = 0;
        this.playersTurn = true;

    }


    public void autoComplite() {

        while (countMyFourDecShips != MAX_SIZE_FOUR_DECK_SHIPS) {

            if (field.addShip(oneDeckShip.getRandomShip(4))) {
                countMyFourDecShips++;
            }


        }

        while (countMyThreeDecShips != MAX_SIZE_THREE_DECK_SHIPS) {

            if (field.addShip(oneDeckShip.getRandomShip(3))) {
                countMyThreeDecShips++;
            }


        }

        while (countMyTwoDecShips != MAX_SIZE_TWO_DECK_SHIPS) {

            if (field.addShip(oneDeckShip.getRandomShip(2))) {
                countMyTwoDecShips++;
            }


        }

        while (countMyOneDecShips != MAX_SIZE_ONE_DECK_SHIPS) {

            if (field.addShip(oneDeckShip.getRandomShip(1))) {
                countMyOneDecShips++;
            }


        }
    }
}
