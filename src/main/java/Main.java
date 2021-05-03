public class Main {
    public static void main(String[] args) {

        Game  game = new Game();

        try {

            game.startGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //field.addShip(oneDeckShip.getRandomShip(1));

//        Game game = new Game();
//        game.startGame();




    }
}
