
package lms.com.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="clients")
public class Client implements Serializable{
    @Id
    @Column(name="reg_no", nullable=false, unique=true, length=6)
    private String regId;
    
    @Column(name="first_name", nullable=false, length=40)
    private String firstName;
    
    @Column(name="last_name", nullable=false, length=40)
    private String lastName;
    
    @Column(name="phone_number", nullable=false, length=17, unique=true)
    private String phoneNumber;
    
    @Column(name="email", nullable=false, unique=true, length=80)
    private String email;
    
    @Column(name="profile_pic", nullable=true)
    private byte[] photo;
    
    @Column(name="category", nullable=false)
    private String clientCategory;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="client", cascade=CascadeType.REMOVE)
    private List<BookTransaction> transactions;

    public Client() {
    }

    public Client(String regId, String firstName, String lastName, String phoneNumber, String email) {
        this.regId = regId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" + "regId=" + regId + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", photo=" + photo + ", clientCategory=" + clientCategory + '}';
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getClientCategory() {
        return clientCategory;
    }

    public void setClientCategory(String clientCategory) {
        this.clientCategory = clientCategory;
    }

    public List<BookTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BookTransaction> transactions) {
        this.transactions = transactions;
    }
    
    
    public void addTransaction(BookTransaction trans){
        this.transactions.add(trans);
        trans.setClient(this);
    }
}

