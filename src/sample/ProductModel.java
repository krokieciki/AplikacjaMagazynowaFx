package sample;

public class ProductModel {

    String product_id;
    String product_name;
    String price;
    String expiry_date;
    String quantity;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ProductModel(String product_id, String name, String price, String date, String quantity) {
        this.product_id = product_id;
        this.product_name = name;
        this.price = price;
        this.expiry_date = date;
        this.quantity = quantity;
    }

    public ProductModel() {

    }


}
