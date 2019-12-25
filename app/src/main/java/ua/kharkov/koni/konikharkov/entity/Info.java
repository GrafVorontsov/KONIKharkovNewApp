package ua.kharkov.koni.konikharkov.entity;

public class Info {
    private String icon;
    private String text;

    public Info(String icon, String text) {
        this.icon = icon;
        this.text = text;
    }
    public Info(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}