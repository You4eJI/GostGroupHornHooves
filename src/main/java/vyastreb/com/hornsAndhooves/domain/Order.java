package vyastreb.com.hornsAndhooves.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "furniture", nullable = false)
    private String furniture;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date creationDate = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dueDate;

    @Column(name = "assigned_employee")
    private String employeeName;

    @Column(name = "department")
    private String department;
}
