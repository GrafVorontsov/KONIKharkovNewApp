package ua.kharkov.koni.konikharkov;

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



/*
        function info($info){
                $infos = explode(";", $info);

        echo "<table>";
        foreach($infos as $info){
            echo "<tr><td>" . $info . "</td></tr>";
        }
        echo "</table>";
}*/

/*
<h3>Notes</h3>;
<p><span class="icon">N</span>Top groove is standard</p>;
<p><span class="icon">N3</span>bottom groove 15 mm lower</p>;
<p><span class="icon"><span class="koni2">2</span></span>Number of grooves</p>;
<p><span class="icon"><span class="koni2">o</span></span>Spring seat adjustment. The figure in the circle = number of grooves.</p>;
<p><span class="icon"><span class="koni2">f</span></span>Externally adjustable by means of a knob or pin without removing the shock absorbers from the car.</p>;
<p><span class="icon"><span class="koni2">a</span></span>Gas-charged shock absorber.</p>
* */