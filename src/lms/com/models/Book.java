
package lms.com.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name="books")
public class Book implements Serializable {
    @Id
    @Column(name="book_id", nullable=false, unique=true, length=15)
    private String bookId;
    
    @Column(name="title", nullable=false, unique=true, length=100)
    private String title;
    
    @Column(name="book_author", nullable=false, length=80)
    private String author;
    
    @Column(name="publishing_house", nullable=false, length=100)
    private String publishingHouse;
    
    @Column(name="publication_date", nullable=false)
    private Date publicationDate;
    
    @Column(name="book_pages", nullable=false)
    private int pages;
    
    @Column(name="book_available", nullable=true)
    private boolean available=true;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="book_category")
    private BookCategory category;
    
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE, mappedBy="book")
    private List<BookTransaction> transactions;

    public Book() {
    }

    public Book(String bookId, String title, String author, String publishingHouse, Date publicationDate, int pages) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.publicationDate = publicationDate;
        this.pages = pages;
    }
    
    

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public List<BookTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BookTransaction> transactions) {
        this.transactions = transactions;
    }
    
    public void addTransaction(BookTransaction trans){
        this.transactions.add(trans);
        trans.setBook(this);
    }
    
    
    
}
