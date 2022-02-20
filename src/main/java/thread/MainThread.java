package thread;

import dao.CreateTable;
import service.PaymentService;


public class MainThread extends Thread {
    private final CreateTable createTable;
    private final PaymentService paymentService;

    public MainThread() {
        paymentService = new PaymentService();
        createTable = new CreateTable();
    }

    @Override
    public void run() {
        try {
            this.createTable.createTable();
            this.paymentService.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
