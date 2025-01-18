package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Matches")

@NoArgsConstructor
@Setter
@ToString
@Getter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "Player1", referencedColumnName = "ID")
    private  Player player1;

    @ManyToOne
    @JoinColumn(name = "Player2", referencedColumnName = "ID")
    private  Player player2;

    @ManyToOne
    @JoinColumn(name ="Winner", referencedColumnName = "ID")
    private  Player winner;
}
