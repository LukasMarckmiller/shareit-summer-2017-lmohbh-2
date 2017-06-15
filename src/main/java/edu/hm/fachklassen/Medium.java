package edu.hm.fachklassen;

/*
*ShareIt
* Date: 12.04.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/

import javax.persistence.*;
import java.io.Serializable;

/**
 * Medium representation.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Medium implements Serializable{

    @Id
    @GeneratedValue()
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "TITLE")
    private String title;

    /**
     * Required by Hibernate
     */
    public Medium()
    {
        this("");
    }

    /**
     * Creates new Medium with given Titel.
     *
     * @param title of the Medium
     */
    public Medium(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Invalid Titel!");
        }
        this.title = title;
    }

    /**
     * Get Titel of the Medium.
     *
     * @return Titel
     */
    public String getTitle() {
        return title;
    }

    public Medium setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        return getTitle();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medium medium = (Medium) o;

        return title.equals(medium.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
