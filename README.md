
# Parking Management 

<p align="center">
<a href="https://github.com/Tibetano/parking-management-api/blob/main/LICENSE" style="text-decoration: none; color: inherit;">  <img src="http://img.shields.io/static/v1?label=License&message=MIT&color=green&style=for-the-badge"/>  </a>
<a href="https://www.example.com" style="text-decoration: none; color: inherit;">  <img src="http://img.shields.io/badge/Java-21-green?style=for-the-badge&logo=java"/>  </a>
<a href="https://www.example.com" style="text-decoration: none; color: inherit;">  <img alt="GitHub" src="https://img.shields.io/static/v1?label=GitHub&message=deploy&color=blue&style=for-the-badge&logo=github"/>  </a>
<a href="https://www.example.com" style="text-decoration: none; color: inherit;">  <img alt="Docker" src="https://img.shields.io/static/v1?label=Docker&message=container&color=blue&style=for-the-badge&logo=docker"/>  </a>
<a href="https://www.example.com" style="text-decoration: none; color: inherit;">  <img src="http://img.shields.io/static/v1?label=VERSAO&message=0.0.1&color=GREEN&style=for-the-badge"/>  </a>
</p>

# Sobre o projeto
Parking Management  é uma api backend desenvolvida para o gerenciamento de vagas de estacionamentos de carros e motos.

A aplicação consiste em gerenciar as vagas para estacionamentos em estabelecimentos registrados na api, reservando vagas para carros e/ou motos sob demanda de acordo com a disponibilidade das vagas, que podem ser monitoradas através de recursos de análise disponibilizadas pela api. Os dados dos estabelecimentos, usuários e veículos são coletados por api's front ends externas que podem ser web ou mobile.

> **Nota:** Este projeto foi desenvolvido como solução para o teste de vaga backend disponível em "https://github.com/fcamarasantos/backend-test-java", no entanto, não foi submetido para a vaga, sendo desenvolvido com intuito de autoavaliação de conhecimento. 

## Recursos disponíveis

 1. **Estacionar veículos:** Permite ao funcionário reservar uma vaga para um vaículo (carro/moto) em um estabelecimento específico.
 2. **Liberar veículos:** Permite ao funcionário liberar uma vaga ocupada por um vaículo (carro/moto) em um estabelecimento específico.
 3. **Consultas:** Permite aos usuários acessarem informações referentes às entidades da api conforme suas permissões.  
 3.1. **Total de Entradas de veículos:** Informa o número total de veículos (carros/motos) que entraram no estacionamento durante um período específico.  
 3.2. **Total de Saídas de veículos:** Informa o número total de veículos (carros/motos) que saíram do estacionamento durante o mesmo período.  
 3.3. **Tempo Médio de Permanência dos veículos:** Informa a média do tempo que os veículos (carros/motos) permaneceram estacionados no estacionamento antes de sair.  
 3.4. **Taxa de Ocupação do(s) Estacionamento(s):** Informa a porcentagem da capacidade total do estacionamento que está sendo utilizada durante o período especificado.  
 3.5. **Entradas de veículos por Hora:** Informa o número de veículos (carros/motos) que entraram no estacionamento em cada hora específica do dia.  
 3.6. **Saídas de veículos por Hora:** Informa o número de veículos (carros/motos) que saíram do estacionamento em cada hora específica do dia.
 4. **CRUD para usuários (comuns e administradores):** Permite aos usuários efetuarem operações CRUD sobre sí próprios.
 5. **CRUD para estabelecimentos:** Permite aos usuários efetuarem operações CRUD sobre os estabelecimentos.
 6. **CRUD para veículos:** Permite aos usuários efetuarem operações CRUD sobre veículos.

> **Nota:** Algumas permissões de acesso para os recursos da api podem ser visualizadas abaixo no tópico **Exemplos de uso**. 

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
- Docker

# Como executar o projeto

Pré-requisitos: Java 21, Maven, PostgreSQL(database='fcamarafinal', username='postgres', password=123456).

```bash
# Clonar repositório
git clone https://github.com/Tibetano/parking-management-api

## Executar o projeto via terminal
# Navegar até o diretório raiz do projento onde se encontra o arquivo "pom.xml"
cd caminho/do/seu/projeto

# Compilar e executar o projeto springboot via Maven
mvn spring-boot:run

## Executar o projeto via Docker
# Subir aplicação via Docker
docker compose up --build -d

# Derrubar a aplicação do Docker
docker compose down
```

# Exemplos de uso

<table>
	<thead>
		<tr>  
			<th>Recurso</th> 
			<th>Endpoint</th> 
			<th>Tipo da requisição</th>
			<th>Corpo da requisição</th>
			<th>Permissão</th>  
		</tr> 
	</thead>
	<tbody>
		<tr>  
			<td>1</td>  
			<td>/v1/parkingRecord</td>  
			<td>POST</td>
			<td><pre>{<br/>  "cnpj":"ex_cnpj",<br/>  "plate":"ex_plate"<br/>}</pre></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>2</td>  
			<td>/v1/parkingRecord/<b>ex_placa</b></td>  
			<td>PUT</td>
			<td><pre></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>3.1</td>
			<td>/v1/parkingRecord/input/<b>ex_cnpjEstablishment</b></td>
			<td>GET</td>
			<td></td>
			<td>admin</td>
		</tr> 
		<tr>  
			<td>3.2</td>  
			<td>/v1/parkingRecord/output/<b>ex_cnpjEstablishment</b></td>  
			<td>GET</td>
			<td></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>3.3</td>  
			<td>/v1/parkingRecord/average-time/<b>ex_vehicleType</b></td>  
			<td>GET</td>
			<td></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>3.4</td>  
			<td>/v1/parkingRecord/occupancy-rate</td>  
			<td>GET</td>
			<td></td>  
			<td>user</td>  
		</tr> 
		<tr>  
			<td>3.5</td>  
			<td>/v1/parkingRecord/entry-hour/<b>ex_cnpjEstablishment</b></td>  
			<td>GET</td>
			<td></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>3.6</td>  
			<td>/v1/parkingRecord/exit-hour/<b>ex_cnpjEstablishment</b></td>  
			<td>GET</td>
			<td></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>4</td>  
			<td>/v1/user/<b>ex_username</b></td>  
			<td>DELETE</td>
			<td></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>5</td>  
			<td>/v1/establishment/<b>ex_cnpjEstablishment</b></td>  
			<td>PUT</td>
			<td><pre>{<br/>  "phoneNumber":"(38)3821-4250"<br/>}</pre></td>  
			<td>admin</td>  
		</tr> 
		<tr>  
			<td>6</td>  
			<td>/v1/vehicle</td>  
			<td>POST</td>
			<td><pre>{
    "mark":"HONDA",
    "model":"CG-125-FAN",
    "color":"BLACK",
    "plate":"HHZ-0121",
    "type":"MOTORCYCLE"
}</pre></td>  
			<td>admin</td>  
		</tr>  
	</tbody>  
</table>

> **Nota:** Devido a quantidade de recursos/endpoints presentes na api, foram apresentados apenas alguns exemplos dos recursos disponíveis na nessa, no entanto, os demais recursos são intuitivos, de facil entendimento e uso. 


# Autor

Lucas Soares Maciel

https://www.linkedin.com/in/lucas-soares-developer
