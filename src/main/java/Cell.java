public class Cell {
    private boolean isShip;
    private boolean isAlive;
    private int x;
    private boolean isHidden;
    private int y;

    public void setShip(boolean ship) {
        isShip = ship;
    }

    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public Cell(int x, int y, boolean isShip, boolean isHidden){
        this.x = x;
        this.y = y;
        this.isShip = isShip;
        this.isHidden = isHidden;
    }
    public Cell(){}

    public  boolean compareCell(Cell cell){
        return this.getX() == cell.getX() && this.getY() == cell.getY();
    }


    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void delHidden(){
        this.isHidden = false;
    }


    public void addHidden(){this.isHidden = true;}

    public char render(){

        if(isHidden == true){
            return 'o';
        }

        if(isShip == true && isAlive == true) {
            return '#';

        }
        if(isShip == true && isAlive == false){
            return 'x';
        }
        else {
            return ' ';
        }

        //}? '#' : ' ';
    }

    public boolean getIsShip(){
        return  isShip;
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public boolean getIsHidden(){
        return isHidden;
    }

}
