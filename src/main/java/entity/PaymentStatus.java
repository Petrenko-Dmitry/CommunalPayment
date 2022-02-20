package entity;

import java.util.Locale;
import java.util.Random;

public enum PaymentStatus {
    NEW,
    PAID,
    FAILED;

    public static PaymentStatus setPaymentStatus(String status) {
        PaymentStatus paymentStatus;
        switch (status.toUpperCase(Locale.ROOT)) {
            case "NEW" -> paymentStatus = PaymentStatus.NEW;
            case "PAID" -> paymentStatus = PaymentStatus.PAID;
            case "FAILED" -> paymentStatus = PaymentStatus.FAILED;
            default -> throw new IllegalStateException("Unexpected value: " + status.toUpperCase(Locale.ROOT));
        }
        return paymentStatus;
    }

    public static void setRandomStatus(Payment payment) {
        PaymentStatus[] statuses = PaymentStatus.values();
        int length = statuses.length;
        int randomIndex;
        randomIndex = new Random().nextInt(length);
        payment.setPaymentStatus(statuses[randomIndex]);
    }

    public static void setStatusNew(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.NEW);
    }
}
