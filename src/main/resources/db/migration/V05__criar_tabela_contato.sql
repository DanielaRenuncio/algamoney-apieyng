CREATE TABLE contato (
  codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_pessoa BIGINT(20) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	telefone VARCHAR(20) NOT NULL,
  FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into contato (codigo, codigo_pessoa, nome, email, telefone) values (1, 1, 'Marcos Henrique', 'marcos@algamoney.com', '00 0000-0000');
insert into contato (codigo, codigo_pessoa, nome, email, telefone) values (2, 2, 'Maria Luiza', 'marialuiza@algamoney.com', '00 0000-0000');
insert into contato (codigo, codigo_pessoa, nome, email, telefone) values (3, 3, 'Dani Cristina', 'danicristina@algamoney.com', '00 0000-0000');
insert into contato (codigo, codigo_pessoa, nome, email, telefone) values (4, 4, 'Pedro Augusto', 'pedroaugusto@algamoney.com', '00 0000-0000');
insert into contato (codigo, codigo_pessoa, nome, email, telefone) values (5, 5, 'Tiago Luiz', 'tiagoluiz@algamoney.com', '00 0000-0000');
