package be.pxl.student.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
                @NamedQuery(name = "findLabelByName", query = "SELECT l from Label l where l.name = :name"),
                @NamedQuery(name = "findLabelById", query = "SELECT l from Label l where l.id = :id"),
                @NamedQuery(name = "findAllLabels", query = "SELECT l from Label l")
        })

public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Payment payment;

    public Label() {
        // JPA only
    }

    public Label(String name) {
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
