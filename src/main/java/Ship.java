public class Ship {
    private Cell[] cell;
    private boolean isHorisontal;


    public Ship(Cell[] cell) {
        this.cell = cell;

    }

    public Ship() {
    }


    public boolean crossingOffShips(Ship ship) {



        Cell[] cellsOfShipTwo = ship.getCell();
        int min = cellsOfShipTwo.length > cell.length ? cell.length : cellsOfShipTwo.length;
        for (int i = 0; i < min; i++) {
            if (cell[i].compareCell(cellsOfShipTwo[i])) {
                return true;
            }

        }

        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cellsOfShipTwo.length; j++) {
                int x = cell[i].getX();
                int y = cell[i].getY();
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if ((x + k == cellsOfShipTwo[j].getX() && y + l == cellsOfShipTwo[j].getY())) {
                            return true;
                        }

                    }

                }

            }

        }
        return false;
    }


    public Cell[] getCell() {
        return cell;
    }
}
