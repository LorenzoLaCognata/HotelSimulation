public class Main {
    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        gameManager.initHotel();
        gameManager.initGuests();

        gameManager.advanceDate();
        gameManager.advanceDate();
    }
}