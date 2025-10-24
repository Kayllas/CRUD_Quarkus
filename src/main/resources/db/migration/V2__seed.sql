-- Categorias
insert into categorias (nome) values ('Eletrônicos') on conflict do nothing;
insert into categorias (nome) values ('Livros') on conflict do nothing;

-- Produtos
insert into produtos (nome, preco, ativo, categoria_id)
select 'Fone Bluetooth', 199.90, true, c.id from categorias c where c.nome='Eletrônicos'
on conflict do nothing;

insert into produtos (nome, preco, ativo, categoria_id)
select 'Teclado Mecânico', 499.00, true, c.id from categorias c where c.nome='Eletrônicos'
on conflict do nothing;

insert into produtos (nome, preco, ativo, categoria_id)
select 'Clean Architecture', 120.00, true, c.id from categorias c where c.nome='Livros'
on conflict do nothing;
