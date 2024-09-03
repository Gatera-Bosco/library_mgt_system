
package lms.com.models;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;


@Entity
@Table(name="book_transactions")
public class BookTransaction implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="transaction_id", unique=true, nullable=false)
    private long transId;
    
    @Column(name="transaction_date", nullable=false)
    private Date transactionDate;
    
    @Column(name="return_date")
    private Date returnDate;
    
    @Column(name="book_returned", nullable=false)
    private boolean bookReturned=false;
    
    @Column(name="transaction_type", nullable=false)
    private String transactionType;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="client_regno")
    private Client client;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="book_id")
    private Book book;
    

    public BookTransaction() {
    }
       
    
    public BookTransaction(Date transactionDate, Date returnDate, String transactionType) {
        this.transactionDate = transactionDate;
        this.returnDate = returnDate;
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getTransId() {
        return transId;
    }

    public void setTransId(long transId) {
        this.transId = transId;
    }

    public boolean isBookReturned() {
        return bookReturned;
    }

    public void setBookReturned(boolean bookReturned) {
        this.bookReturned = bookReturned;
    }
    
    
}
