package thread;

import dao.PaymentDAO;
import entity.Payment;
import entity.PaymentStatus;

import java.util.List;
import java.util.TimerTask;

public class CheckNewPayment extends TimerTask {
    private final PaymentDAO paymentDAO;
    private static final long THREE_SECOND_IN_MILLI = 3000;

    public CheckNewPayment() {
        this.paymentDAO = new PaymentDAO();
    }

    @Override
    public void run() {
        List<Payment> paymentWithNewStatus = this.paymentDAO.findPaymentWithNewStatus();

        paymentWithNewStatus.forEach(payment -> {

            if (payment.getDateChange() - payment.getDateCreation() < THREE_SECOND_IN_MILLI) {
                PaymentStatus.setStatusNew(payment);
            } else {
                PaymentStatus.setRandomStatus(payment);
            }
            payment.setDateChange(System.currentTimeMillis());
        });
        this.paymentDAO.saveListToDB(paymentWithNewStatus);

    }
}
