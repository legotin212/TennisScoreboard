
create table Players(ID int primary key GENERATED ALWAYS AS IDENTITY,Name Varchar(255) NOT NULL );

create unique index unique_name on Players(Name);

create table Matches (
    ID int primary key GENERATED ALWAYS AS IDENTITY,
    Player1 int NOT NULL ,
    Player2 int NOT NULL ,
    Winner int,
    FOREIGN KEY (Player1) references Players(ID),
    FOREIGN KEY (Player2) references Players(ID),
    FOREIGN KEY (Winner) references Players(ID)
);