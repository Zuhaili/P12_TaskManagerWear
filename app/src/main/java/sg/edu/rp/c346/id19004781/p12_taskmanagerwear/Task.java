package sg.edu.rp.c346.id19004781.p12_taskmanagerwear;



import java.io.Serializable;

class Task implements Serializable {
    private int id;
    private String name;
    private String desc;

    public Task(int id, String name, String desc) {
        this.id = id;
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return id + " " + name + "\n" + desc;
    }
}