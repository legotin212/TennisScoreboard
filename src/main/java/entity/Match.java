package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Matches")

@NoArgsConstructor
@Setter
@Getter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "Player1", referencedColumnName = "ID", table = "Players")
    private  Player player1;

    @ManyToOne
    @JoinColumn(name = "Player2", referencedColumnName = "ID", table = "Players")
    private  Player player2;

    @ManyToOne
    @JoinColumn(name ="Winner", referencedColumnName = "ID", table = "Players")
    private  Player winner;
}
