package entity;

import javax.persistence.*;

@Entity
@Table(name = "Players")
public class Player {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Name")
    private String name;
}
