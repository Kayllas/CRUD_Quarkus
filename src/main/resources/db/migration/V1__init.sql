-- Flyway V1: create tables and constraints
create table if not exists categorias (
  id bigserial primary key,
  nome varchar(120) not null unique
);

create table if not exists produtos (
  id bigserial primary key,
  nome varchar(160) not null,
  preco numeric(15,2) not null check (preco >= 0),
  ativo boolean not null default true,
  categoria_id bigint not null references categorias(id),
  constraint uk_produto_nome_categoria unique (nome, categoria_id)
);

create index if not exists idx_produtos_categoria on produtos(categoria_id);
create index if not exists idx_produtos_nome on produtos(lower(nome));
