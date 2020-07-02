insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2,'Brasileira');

insert into restaurante (nome, taxa_frete, cozinha_id ) values ('Restaurante do Paulo', 5.0, 1);
insert into restaurante (nome, taxa_frete, cozinha_id ) values ('Restaurante 0800', 4.90, 1);
insert into restaurante (nome, taxa_frete, cozinha_id ) values ('HEEELOO ITS ME', 2.90, 2);

insert into forma_pagamento(descricao) values ('debito');
insert into forma_pagamento(descricao) values ('credito');
insert into forma_pagamento(descricao) values ('dinheiro');

insert into permissao(nome, descricao) values ('administrador', 'pode tudo');
insert into permissao(nome, descricao) values ('usuario', 'pode nada');