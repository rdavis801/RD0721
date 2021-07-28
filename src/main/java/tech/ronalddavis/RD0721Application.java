package tech.ronalddavis;


import tech.ronalddavis.rentals.Checkout;
import tech.ronalddavis.rentals.RentalAgreement;

public class RD0721Application {
    public static void main(String[] args) {
        try {
            RentalAgreement agreement = Checkout.start();
            agreement.printReport();
        } catch (Exception e) {
            System.out.printf("\n\n%s\n\n", e.getMessage());
        }
    }
}
