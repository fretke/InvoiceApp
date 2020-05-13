package Company;

public class Employee {
    private String name;
    private String position;
    private boolean isSelected;

    public Employee(){

    }

    public Employee(String name, String position, boolean isSelected) {
        this.name = name;
        this.position = position;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return name + " " + position;
    }
}
