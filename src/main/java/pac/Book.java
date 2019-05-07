/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;


import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "book") 

public class Book  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private int id;
    
    public Book(){}
    
    public Book(int id, String title, String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    public int getId() { 
      return id; 
    }  
    
    @XmlElement 
    public void setId(int id) { 
        this.id = id; 
    } 
    
    public String getTitle() {
        return title;
    }
    
   @XmlElement 
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
   @XmlElement 
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", author=" + author + ", id=" + id + '}';
    }
    
    
}


