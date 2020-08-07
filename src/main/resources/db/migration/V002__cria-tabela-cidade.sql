CREATE TABLE cidade (
	id bigint auto_increment not null,
    nome_cidade varchar(80) not null,
    nome_estado varchar(80) not null,
    primary key (id)
)engine=InnoDB;