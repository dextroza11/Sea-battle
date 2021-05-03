import java.util.ArrayList;
import java.util.Locale;

public class Field {


    private String name;
    private static char[] abc = {' ', 'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И'};
    private static int length = 10;
    private static int height = 10;
    private Cell[][] cells;
    private Cell[][] foreignCells;
    private ArrayList<Ship> ships;
    private ArrayList<Ship> foreignShips;

    public Field(int space) {

        ships = new ArrayList<Ship>();
        foreignShips = new ArrayList<Ship>();
        this.cells = new Cell[length][height];
        this.foreignCells = new Cell[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j, false, false);
                foreignCells[i][j] = new Cell(i, j, false, true);

            }

        }
    }

    public void initField(String name) {
        this.name = name;
    }



    public ShotStatus fireOnMyShips(int x, int y) {

        if (cells[x][y].getIsShip() && cells[x][y].getIsAlive()) {
            cells[x][y].setAlive(false);
            return ShotStatus.HITTING;
        }

        if ((cells[x][y].getIsShip() && !cells[x][y].getIsAlive()) || cells[x][y].getIsHidden()) {
            return ShotStatus.REPEATSHOT;
        } else {
            cells[x][y].addHidden();
            return ShotStatus.MISS;
        }
    }

    public ShotStatus fireOnForeignShips(int x, int y) {
        if (foreignCells[x][y].getIsShip() && foreignCells[x][y].getIsAlive()) {
            foreignCells[x][y].setAlive(false);
            foreignCells[x][y].delHidden();

            return ShotStatus.HITTING;                           //1 - попали
        }

        if ((foreignCells[x][y].getIsShip() && !foreignCells[x][y].getIsAlive()) || !foreignCells[x][y].getIsHidden()) {

            return ShotStatus.REPEATSHOT;                       // уже стреляли
        }

        // if(cells[x][y].getIsAlive())
        else {
            foreignCells[x][y].delHidden();
            return ShotStatus.MISS;                       // не попали
        }

    }

    public boolean addShip(Ship ship) {
        for (Ship shipEl : ships) {
            if (shipEl.crossingOffShips(ship)) {
                return false;
            }
        }

        ships.add(ship);
        for (Cell cell : ship.getCell()) {
            cells[cell.getX()][cell.getY()].setShip(true);

            cells[cell.getX()][cell.getY()].setAlive(true);

        }

        return true;

    }

    public boolean addForeignShip(Ship ship) {
        for (Ship shipEl : foreignShips) {
            if (shipEl.crossingOffShips(ship)) {
                return false;
            }
        }

        foreignShips.add(ship);
        for (Cell cell : ship.getCell()) {
            foreignCells[cell.getX()][cell.getY()].setShip(true);
            foreignCells[cell.getX()][cell.getY()].setAlive(true);

        }

        return true;

    }

    public void renderABC(int space) {
        for (int i = 0; i < space; i++) {
            System.out.print(' ');

        }
        System.out.print(" ");
        for (char el : abc) {
            System.out.print(el + " ");
        }
        //System.out.println();

    }

    public void renderStr(Cell[] str, int j) {

        for (int i = 0; i < length; i++) {
            System.out.print(str[i].render() + " ");

        }


    }

    public void showStatistic(int i, int myScope,int foreignScope) {

        if (i == 0)
            System.out.print("---Cтатистика---  ");
            //16 + 2
        else if (i == 1) {
            int size = 18;

            if( name.length() % 2 != 0)
                name += " ";

            int l = (18 - name.length()) / 2;
            for (int j = 0; j < l - 2; j++) {
                System.out.print('-');

            }
            System.out.print(" " + name.toUpperCase(Locale.ROOT) + " ");
            for (int j = 0; j < l - 2; j++) {
                System.out.print('-');

            }
            System.out.print("  ");

        } else if (i == 3)
            System.out.print("---Ваши очки:---  ");

        else if (i == 5)
            System.out.print("------ " + myScope + "." + " ------  ");

        else if (i == 7)
            System.out.print("---Очки бота:---  ");


        else if (i == 9)
            System.out.print("------ " + foreignScope + "." + " ------  ");


        else
            System.out.print("----------------  ");

    }

    public void renderField(int myScope, int foreignScope) {
        renderABC(17);
        renderABC(0);
        System.out.println();

        for (int i = 0; i < length; i++) {
            showStatistic(i, myScope, foreignScope);
            System.out.print(i + " ");
            renderStr(cells[i], i);
            System.out.print(" " + i + " ");
            renderStr(foreignCells[i], i);
            System.out.println();


        }
    }

    public void showAllShips() {

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (foreignCells[i][j].getIsHidden()) {

                    foreignCells[i][j].delHidden();
                }
            }

        }

    }

    public void hiddenAllShips() {

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                foreignCells[i][j].addHidden();
            }

        }


    }

}
