package main.main50;

public class Word implements Cloneable{

    public String name;

    public Word(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println(name);
    }

    @Override
    protected Word clone()  {
        try {
            return (Word) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
