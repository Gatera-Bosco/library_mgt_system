
package lms.com.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="book_category")
public class BookCategory implements Serializable{
    @Id
    @Column(name="code", nullable=false, unique=true, length=5)
    private String code;
    
    @Column(name="name", nullable=false, length=100, unique=true)
    private String name;
    
    @OneToMany(cascade={CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval=false,
            mappedBy="category", fetch=FetchType.EAGER)
    private List<Book> books;
    

    public BookCategory() {
    }

    public BookCategory(String code, String name) {
      this.code = code;
      this.name = name;
    }

    @Override
    public String toString() {
        return "BookCategory{" + "code=" + code + ", name=" + name + '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addBook(Book book){
        this.books.add(book);
        book.setCategory(this);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
  
}
