create table Players(ID int primary key auto_increment,
Name Varchar);

create unique index unique_name on Players(Name);

create table Matches (
    ID int primary key auto_increment,
    Player1 int,
    Player2 int,
    Winner int,
    foreign key (Player1) references Players(ID),
    foreign key (Player2) references Players(ID),
    foreign key (Winner) references Players(ID)
)