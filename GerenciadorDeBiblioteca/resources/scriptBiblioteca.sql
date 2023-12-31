CREATE DATABASE Biblioteca
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

CREATE TABLE Livros (
id INT AUTO_INCREMENT,
titulo VARCHAR(50) NOT NULL,
editora VARCHAR(50) NOT NULL,
genero ENUM('ROMANCE','FICÇÃO_CIENTÍFICA','FANTASIA',
'TERROR','MISTERIO','SUSPENSE','DRAMA','COMEDIA'),
ano_de_publicao INT NOT NULL,
autor VARCHAR(30) NOT NULL,
formato ENUM ('FISICO', 'DIGITAL') NOT NULL,
idioma ENUM('PORTUGUÊS','INGLÊS') NOT NULL DEFAULT 'PORTUGUÊS',
PRIMARY KEY(id)
)DEFAULT CHARSET utf8mb4;