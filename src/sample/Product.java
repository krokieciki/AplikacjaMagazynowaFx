package sample;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class Product
{
    private Integer productID; //zmienione z int, żeby było można nadać null
    private String productName;
    private double productPrice;
    private LocalDate expireDate;

    public Product(Integer productID, String productName, double productPrice, LocalDate expireDate)
    {
        this.productID = null;
        this.productName = productName;
        this.productPrice = productPrice;
        this.expireDate = expireDate;
    }

    public Integer getProductID() {
        return this.productID;
    }

    public void setProductName(String name) {
        this.productName = name;
    }
    public String getProductName() {
        return this.productName;
    }

    public void setPrice(double price) {
        this.productPrice = price;
    }
    public double getPrice() {
        return this.productPrice;
    }

    public void setExpireDate(LocalDate date) {
        this.expireDate = date;
    }
    public LocalDate getExpireDate() {
        return this.expireDate;
    }

    @Override
    public String toString() {
        return "Product {" +
                "id = " + productID +
                ", productName = " + productName +
                ", price = " + productPrice +
                ", expiration date = " + expireDate +
                '}';
    }

    public static Product newProduct() {
        Scanner input = new Scanner(System.in).useLocale(Locale.US);;
        System.out.print("Podaj nazwę produktu: ");
        String name = input.next();
        System.out.print("\nPodaj cenę: ");
        double price = input.nextDouble();
        System.out.print("\nPodaj datę ważności (yyyy-mm-dd): ");
        input.nextLine();
        String expire = input.nextLine();
        LocalDate date = LocalDate.parse(expire);

        return new Product(null, name, price, date);
    }
}