public class Main {
    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        gameManager.initHotel();
        gameManager.initEmployees();

        for (int i=0; i<21; i++) {
            gameManager.advanceDate();
        }
    }
}