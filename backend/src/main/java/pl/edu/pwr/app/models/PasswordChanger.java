package pl.edu.pwr.app.models;

public class PasswordChanger {

    private User user;
    private String oldPassword;
    private String newPassword;

    public PasswordChanger(){}

    public PasswordChanger(User user, String oldPassword, String newPassword) {
        this.user = user;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
