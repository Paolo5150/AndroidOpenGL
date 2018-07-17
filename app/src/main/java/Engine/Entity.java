package Engine;

public abstract class Entity {

    protected String name;
    protected String ID;
    protected String type;

    public Entity(String name,String type)
    {

        this.name = name;
        this.type = type;
        GenerateID();
    }

    protected void GenerateID()
    {
    ID = type + "_" + System.currentTimeMillis() + Utils.GetRandomFloat();
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
