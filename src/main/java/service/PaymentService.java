package service;

import dao.*;
import entity.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaymentService {

    private final PaymentDAO paymentDAO;
    private final CommunalUserDAO communalUserDAO;
    private final TemplateDAO templateDAO;
    private final UserAddressDAO userAddressDAO;
    private final Map<String, String> inMemoryTemplateName = new HashMap<>();

    public PaymentService() {
        paymentDAO = new PaymentDAO();
        communalUserDAO = new CommunalUserDAO();
        templateDAO = new TemplateDAO();
        userAddressDAO = new UserAddressDAO();
    }


    public void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String[] partOfLine;
            String line;

            while ((line = reader.readLine()) != null) {
                partOfLine = line.split("\\|");
                switch (partOfLine[0]) {
                    case "REGISTRATION" -> this.convertToUserAndSaveToDB(partOfLine);
                    case "ADD_ADDRESS" -> this.convertToUserAddressAndSaveToDB(partOfLine);
                    case "ADD_TEMPLATE" -> this.convertToTemplateAndSaveToDB(partOfLine);
                    case "ADD_PAYMENT" -> this.convertToPaymentAndSaveToDB(partOfLine);
                    default -> throw new IllegalStateException("Unexpected command: " + partOfLine[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertToUserAndSaveToDB(String[] partOfLine) {
        CommunalUser communalUser = new CommunalUser();
        communalUser.setFio(partOfLine[1]);
        communalUser.setEmail(partOfLine[2]);
        communalUser.setPhoneNumber(Long.parseLong(partOfLine[3]));
        this.communalUserDAO.saveUser(communalUser);
    }

    private void convertToUserAddressAndSaveToDB(String[] partOfLine) {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(partOfLine[1]);
        userAddress.setUsersEmail(partOfLine[2]);
        this.userAddressDAO.saveAddress(userAddress);
    }

    private void convertToTemplateAndSaveToDB(String[] partOfLine) {
        Template template = new Template();
        template.setTemplateName(partOfLine[1]);
        template.setIban(partOfLine[2]);
        template.setPaymentPurpose(partOfLine[3]);
        template.setUserAddressId(Long.parseLong(partOfLine[4]));
        this.inMemoryTemplateName.put("Last Template name", template.getTemplateName());
        this.templateDAO.saveTemplate(template);
    }

    private void convertToPaymentAndSaveToDB(String[] partOfLine) {
        Payment payment = new Payment();
        payment.setTemplateName(this.inMemoryTemplateName.get("Last Template name"));
        payment.setCardNumber(Long.parseLong(partOfLine[1]));
        payment.setPaymentSum(Long.parseLong(partOfLine[2]));
        payment.setPaymentStatus(PaymentStatus.setPaymentStatus(partOfLine[3]));
        payment.setDateCreation(System.currentTimeMillis());
        payment.setDateChange(System.currentTimeMillis());
        this.paymentDAO.savePayment(payment);
    }
}
