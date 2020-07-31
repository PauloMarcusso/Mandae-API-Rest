insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2,'Brasileira');

insert into estado(id, nome) values (1, 'Tocantins');
insert into estado(id, nome) values (2, 'Pernambuco');

insert into cidade(nome, estado_id) values('Goianorte', 1);
insert into cidade(nome, estado_id) values('Recife', 2);

insert into restaurante (nome, taxa_frete, data_cadastro, data_atualizacao, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro ) values ('Restaurante do Paulo', 5.0, utc_timestamp, utc_timestamp, 1, 1, '011110111', 'Rua São Paulo', '100', 'Centro');
insert into restaurante (nome, taxa_frete, data_cadastro, data_atualizacao, cozinha_id ) values ('Restaurante 0800', 4.90, utc_timestamp, utc_timestamp, 1);
insert into restaurante (nome, taxa_frete, data_cadastro, data_atualizacao, cozinha_id ) values ('HEEELOO ITS ME', 2.90, utc_timestamp, utc_timestamp, 2);

insert into forma_pagamento(id, descricao) values (1, 'cartão de débito');
insert into forma_pagamento(id, descricao) values (2, 'cartão de credito');
insert into forma_pagamento(id, descricao) values (3, 'dinheiro');

insert into permissao(nome, descricao) values ('administrador', 'pode tudo');
insert into permissao(nome, descricao) values ('usuario', 'pode nada');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3);
