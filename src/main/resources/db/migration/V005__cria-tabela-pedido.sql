CREATE TABLE pedido (
	id bigint auto_increment not null,
    subtotal decimal (10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,
    
    endereco_cidade_id bigint(20) not null,
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60) null,
    endereco_bairro varchar(60) not null,
    
    status VARCHAR(10) not null,
    data_criacao datetime not null,
    data_confirmacao datetime null,
    data_cancelamento datetime null,
    data_entrega datetime null,
    
    restaurante_id bigint not null,
	forma_pagamento_id bigint not null,
    usuario_cliente_id bigint not null,
    
    PRIMARY KEY(id),
    
    constraint fk_pedido_restaurante_id foreign key(restaurante_id) references restaurante(id),
    constraint fk_pedido_forma_pagamento_id foreign key(forma_pagamento_id) references forma_pagamento(id),
    constraint fk_pedido_cliente_id foreign key(usuario_cliente_id) references usuario(id)
)engine=InnoDB;