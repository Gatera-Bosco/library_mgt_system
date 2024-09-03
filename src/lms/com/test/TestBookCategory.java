
package lms.com.test;

import lms.com.dao.BookCategoryDao;
import lms.com.models.BookCategory;


public class TestBookCategory {
    BookCategoryDao categoryDao;
    
    public void testSaveBookCategory(){
        BookCategory category = new BookCategory("BC1234", "Programming");
        categoryDao = new BookCategoryDao();
        categoryDao.saveBookCategory(category);       
    }
}
