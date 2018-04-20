package govinmaxim.githubclient.model;

public class UpdateInfo {

    private String name;
    private String blog;
    private String company;
    private String location;
    private boolean hireable;
    private String bio;

    public void setName(String name) {
        this.name = name;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHireable(boolean hireable) {
        this.hireable = hireable;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
