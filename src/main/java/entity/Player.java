package entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Players")

@NoArgsConstructor
@AllArgsConstructor
@Setter

@ToString
public class Player {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Name")

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
