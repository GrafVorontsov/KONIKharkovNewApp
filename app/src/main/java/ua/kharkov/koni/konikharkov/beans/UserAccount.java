package ua.kharkov.koni.konikharkov.beans;

import java.util.Objects;

public class UserAccount {

    private long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String level;

    public UserAccount() {

    }

    public UserAccount(String login, String email) {
        super();
        this.login = login;
        this.email = email;
    }

    public UserAccount(String login, String email, String status, String level) {
        this.login = login;
        this.email = email;
        this.status = status;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getLevelTranslate(String level) {
        switch (level){
            case "specopt" : level = "СпецОпт"; break;
            case "opt" : level = "Опт"; break;
            case "diler" : level = "Дилер"; break;
        }
        return level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (id != that.id) return false;
        if (!Objects.equals(login, that.login)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
