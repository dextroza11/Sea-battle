import java.util.Random;

public class oneDeckShip extends Ship {
    private static int countOneDecShip = 0;

    public oneDeckShip(Cell[] cell) {
        super(cell);
        countOneDecShip++;

    }

    public oneDeckShip() {
        super();
        countOneDecShip++;

    }


    public static Ship getRandomShip(int length) {
//        if (length > 4 || length <=0) {
//            throw new Exception(" недопустимая длина корабля");
//        }
        boolean isHorizontal = new Random().nextBoolean();
        int x, y;
        //int x = new Random().nextInt(10 - length);
        //int y = new Random().nextInt(10 - length);
        if (isHorizontal) {
            x = new Random().nextInt(10 - length);
            y = new Random().nextInt(10);

        } else {
            x = new Random().nextInt(10);
            y = new Random().nextInt(10 - length) + length;

        }
        Cell[] localCells = new Cell[length];
        if (isHorizontal) {
            for (int i = 0; i < length; i++) {
                localCells[i] = new Cell(x + i, y, true,true);
            }

        } else {
            for (int i = 0; i < length; i++) {
                localCells[i] = new Cell(x , y - i, true, true);
            }
        }


        return new Ship(localCells);

    }

}
