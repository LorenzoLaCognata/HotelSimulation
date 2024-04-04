import IO.Log;

public class Main {
    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        Log log = new Log();
        gameManager.initHotel();

        for (int i=0; i<10; i++) {
            gameManager.advanceDate();
        }

        gameManager.financialSummary();

    }
}