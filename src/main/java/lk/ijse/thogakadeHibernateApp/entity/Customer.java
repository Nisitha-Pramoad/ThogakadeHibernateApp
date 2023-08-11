package lk.ijse.thogakadeHibernateApp.entity;


import lk.ijse.thogakadeHibernateApp.embedded.Address;
import lk.ijse.thogakadeHibernateApp.embedded.MobileNo;
import lk.ijse.thogakadeHibernateApp.embedded.NameIdentifier;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customer")
public class Customer {

    @Id // Tells hibernate that this is the primary key of this entity
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id", nullable = false, length = 50) // defines how the column name should be generated in database
    private int id;
    private NameIdentifier nameIdentifier;
    private Address CustomerAddress;
    @ElementCollection
    @CollectionTable(name = "customer_mobile_nos",
            joinColumns = @JoinColumn(name = "customer_id"))
    private List<MobileNo> phoneNos = new ArrayList<>();
    @CreationTimestamp
    private Timestamp createdDateTime;

    public Customer() {

    }

    public Customer(int id, NameIdentifier nameIdentifier, Address customerAddress, List<MobileNo> phoneNos, Timestamp createdDateTime) {
        this.id = id;
        this.nameIdentifier = nameIdentifier;
        CustomerAddress = customerAddress;
        this.phoneNos = phoneNos;
        this.createdDateTime = createdDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NameIdentifier getNameIdentifier() {
        return nameIdentifier;
    }

    public void setNameIdentifier(NameIdentifier nameIdentifier) {
        this.nameIdentifier = nameIdentifier;
    }

    public Address getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        CustomerAddress = customerAddress;
    }

    public List<MobileNo> getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(List<MobileNo> phoneNos) {
        this.phoneNos = phoneNos;
    }

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", nameIdentifier=" + nameIdentifier +
                ", CustomerAddress=" + CustomerAddress +
                ", phoneNos=" + phoneNos +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
