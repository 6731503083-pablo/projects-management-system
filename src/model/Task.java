public class Task{

    private int id;
    private String name;
    private String description;
    private String status;
    private Project project;

    public Task(int id, String name, String description, String status, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.project = project;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void updateTaskStatus(String status) {
        this.status = status;
    }

}