package sample;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "biblioteka")
@XmlAccessorType(XmlAccessType.FIELD)

public class ForXml {
    @XmlElement(name = "knjiga")

    private List<ScientificWork> knjige = null;


    public List<ScientificWork> getEmployees() {
        return knjige;
    }

    public void setKnjige(List<ScientificWork> employees) {
        this.knjige = employees;
    }
}