CREATE TABLE item_pedido(

	id bigint not null auto_increment,
    quantidade smallint(6) not null,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao varchar(255) null,
    
    produto_id bigint not null,
    pedido_id bigint not null,
    
    primary key(id),
    unique key uk_item_pedido_produto(pedido_id, produto_id),
    
    constraint fk_item_pedido_produto foreign key(produto_id) references produto(id),
    constraint fk_pedido_item_pedido foreign key(pedido_id) references pedido(id)
    
)engine=InnoDB;