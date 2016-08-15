package model;

public enum MarkType {
    SOLID ("SOLID"),
    DASHED ("DASHED"),
    DOTTED ("DOTTED");
	

    private final String name;   

    private MarkType(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
