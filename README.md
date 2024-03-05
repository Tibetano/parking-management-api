# Parking Management 
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Tibetano/parking-management-api/blob/main/LICENSE) 






# Sobre o projeto
Parking Management  é uma api backend desenvolvida para o gerenciamento de vagas de estacionamentos de carros e motos.

A aplicação consiste em gerenciar as vagas para estacionamentos em estabelecimentos registrados na api, reservando vagas para carros e/ou motos sob demanda de acordo com a disponibilidade das vagas, que podem ser monitoradas através de recursos de análise disponibilizadas pela api. Os dados dos estabelecimentos, usuários e veículos são coletados por api's front ends externas que podem ser web ou mobile.

## Recursos disponíveis

- **Estacionar veículos:** Permite ao funcionário reservar uma vaga para um vaículo(carro/moto) em um estabelecimento específico.
- **Liberar veículos:** Permite ao funcionário liberar uma vaga ocupada por um vaículo(carro/moto) em um estabelecimento específico.
- **Total de Entradas de veículos:** Permite ao funcionário verificar o número total de veículos(carros e motos) que entraram no estacionamento durante um período específico.
- **Total de Saídas de veículos:** Permite ao funcionário verificar o número total de veículos(carros e motos) que saíram do estacionamento durante o mesmo período.
- **Tempo Médio de Permanência dos Carros:** Permite ao funcionário verificar a média do tempo que os carros permaneceram estacionados no estacionamento antes de sair.
- **Tempo Médio de Permanência das Motos:** Permite ao funcionário verificar a média do tempo que as motos permaneceram estacionadas no estacionamento antes de sair.
- **Taxa de Ocupação do Estacionamento:** Permite ao funcionário verificar a porcentagem da capacidade total do estacionamento que está sendo utilizada durante o período especificado.
- **Entradas de veículos por Hora:** Permite ao funcionário verificar o número de veículos(carros e motos) que entraram no estacionamento em cada hora específica do dia.
- **Saídas de veículos por Hora:** Permite ao funcionário verificar o número de veículos(carros e motos) que saíram do estacionamento em cada hora específica do dia.

## Modelo conceitual da API
![Modelo Conceitual](https://raw.githubusercontent.com/Tibetano/assets/main/Diagrama-classes.png)

## Modelo conceitual da representação dos dados
![Modelo ERX](https://raw.githubusercontent.com/Tibetano/assets/main/DIagrama-MERX.png)

# Tecnologias utilizadas
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- PostgreSQL

# Como executar o projeto

Pré-requisitos: Java 17+

```bash
# clonar repositório
git clone https://github.com/Tibetano/parking-management-api

# Subir aplicação através do docker

docker-compose up -d --build

# Parar container
docker-compose down


```



# Autor

Lucas Soares Maciel

https://www.linkedin.com/in/lucas-soares-developer


