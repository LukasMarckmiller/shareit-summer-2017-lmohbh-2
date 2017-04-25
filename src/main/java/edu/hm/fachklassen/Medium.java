package edu.hm.fachklassen;

/*
*ShareIt
* Date: 12.04.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/
abstract public class Medium {
    private final String title;

    public Medium(String title){
        if(title == null)
            throw new IllegalArgumentException("Invalid Titel!");
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public String toString(){
        return getTitle();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Medium medium = (Medium) o;

        return title.equals(medium.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
