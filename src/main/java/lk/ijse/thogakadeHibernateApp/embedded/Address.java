package lk.ijse.thogakadeHibernateApp.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String addressLine1;
    private String AddressLine2;

    public Address() {
    }

    public Address(String addressLine1, String getAddressLine2) {
        this.addressLine1 = addressLine1;
        this.AddressLine2 = getAddressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String getAddressLine2) {
        this.AddressLine2 = getAddressLine2;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", getAddressLine2='" + AddressLine2 + '\'' +
                '}';
    }
}
