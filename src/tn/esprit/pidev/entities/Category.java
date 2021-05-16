package tn.esprit.pidev.entities;

import java.util.Comparator;

public class Category {
    private int id;
    private String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static Comparator<Category> nameComparator = new Comparator<Category>() {
        @Override
        public int compare(Category o1, Category o2) {
            return (int) (o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
        }
    };
}
