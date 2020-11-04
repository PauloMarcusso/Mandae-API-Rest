create table restaurante_usuario_responsavel(
	restaurante_id bigint NOT NULL,
    usuario_id bigint NOT NULL,

    primary key(restaurante_id, usuario_id)
    );

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_restaurante
foreign key (restaurante_id) references restaurante (id);

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_usuario
foreign key (usuario_id) references usuario (id);