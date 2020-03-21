import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBUtil dbUtil = new DBUtil();
        List<Address> addresses = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Enter a new Entry in Database
        System.out.println("Save Persons and Addresses\n" +
                "----------------------------------");
        System.out.println("Enter First Name: ");
        String firstName = scanner.next();
        System.out.println("Enter Last Name: ");
        String lastName = scanner.next();
        Person p = new Person(firstName, lastName);
        scanner.nextLine();
        System.out.println("Enter Zip Code: ");
        String zip_code = scanner.nextLine();
        System.out.println(zip_code);
        System.out.println("Enter Locality: ");
        String locality = scanner.nextLine();
        System.out.println(locality);
        System.out.println("Enter Street: ");
        String street = scanner.nextLine();
        System.out.println(street);
        System.out.println("Enter Country: ");
        String country = scanner.nextLine();
        System.out.println(country);
        dbUtil.saveData(p, new Country(country), new Address(zip_code, locality, street));

        // Load from Database


        System.out.println("Getting access to data via person object");
        for (Person person : dbUtil.loadPersons()) {
            for (Address address : person.getAddressList()) {
                System.out.println(person + "  " + address + "  " + address.getCountry());
            }
        }

        System.out.println("\n----------------------------------------------------------------" +
                "Getting access to data via address object");

        for (Address address : dbUtil.loadAddresses()) {
            System.out.println(address + "  " + address.getPerson() + "  " + address.getCountry());
        }


        System.out.println("\n----------------------------------------------------------------" +
                "Getting access to data via country object");

        for (Country country1 : dbUtil.loadCountries()) {
            for (Address address : country1.getAddress()) {
                System.out.println(country1 + "  " + address + "  " + address.getPerson());
            }
        }

    }

}
