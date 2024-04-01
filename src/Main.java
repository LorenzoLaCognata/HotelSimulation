public class Main {
    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        gameManager.initHotel();

        for (int i=0; i<10; i++) {
            gameManager.advanceDate();
        }

    }
}