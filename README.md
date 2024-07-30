# 📚 README

## 🌟 Visão Geral

Este projeto Java é uma aplicação para gerenciamento de vendedores, departamentos e níveis de trabalho em um sistema de vendas. A aplicação permite inserção, atualização, exclusão e consulta de informações sobre vendedores, departamentos e níveis de trabalho, utilizando boas práticas de programação e logging.

## 🗂️ Estrutura do Projeto

### 📦 Pacotes e Classes

#### `model.entities`

- **`Seller`** 🧑‍💼
  - **Descrição**: Representa um vendedor.
  - **Atributos**:
    - `id`: Identificador do vendedor.
    - `name`: Nome do vendedor.
    - `birthDate`: Data de nascimento.
    - `baseSalary`: Salário base.
    - `department`: Departamento do vendedor.
    - `workLevel`: Nível de trabalho.
  - **Métodos**:
    - Getters e setters para todos os atributos.

- **`Department`** 🏢
  - **Descrição**: Representa um departamento.
  - **Atributos**:
    - `id`: Identificador do departamento.
    - `name`: Nome do departamento.
  - **Métodos**:
    - Getters e setters para todos os atributos.

- **`WorkLevel`** 📊
  - **Descrição**: Enum que define os níveis de trabalho.
  - **Valores**:
    - `JUNIOR`
    - `PLENO`
    - `SENIOR`

#### `model.dao`

- **`SellerDao`** 🔄
  - **Descrição**: Interface para operações de CRUD em vendedores.
  - **Métodos**:
    - `insert(Seller seller)`: Insere um novo vendedor.
    - `update(Seller seller)`: Atualiza um vendedor existente.
    - `deleteById(Integer id)`: Deleta um vendedor pelo ID.
    - `findById(Integer id)`: Encontra um vendedor pelo ID.
    - `findAll()`: Encontra todos os vendedores.

- **`DepartmentDao`** 🔄
  - **Descrição**: Interface para operações de CRUD em departamentos.
  - **Métodos**:
    - `insert(Department department)`: Insere um novo departamento.
    - `update(Department department)`: Atualiza um departamento existente.
    - `deleteById(Integer id)`: Deleta um departamento pelo ID.
    - `findById(Integer id)`: Encontra um departamento pelo ID.
    - `findAll()`: Encontra todos os departamentos.

- **`DaoFactory`** 🏭
  - **Descrição**: Fábrica para criação de instâncias de DAOs.
  - **Métodos**:
    - `createSellerDaoJDBC()`: Cria uma instância de `SellerDao` usando JDBC.
    - `createDepartmentDaoJDBC()`: Cria uma instância de `DepartmentDao` usando JDBC.

#### `services.department`

- **`DepartmentService`** 🛠️
  - **Descrição**: Gerencia as operações principais para departamentos.
  - **Métodos**:
    - `mainDepartment(Scanner sc)`: Exibe um menu de opções para o usuário.
    - `departmentInsert(Scanner sc)`: Insere um novo departamento.
    - `findDepartmentById(Scanner sc)`: Encontra e exibe um departamento pelo ID.
    - `showDepartment()`: Exibe todos os departamentos ativos.
    - `deleteById(Scanner sc)`: Deleta um departamento pelo ID.
    - `updateDepartment(Scanner sc)`: Atualiza os detalhes de um departamento.

#### `services.seller`

- **`SellerService`** 🛠️
  - **Descrição**: Gerencia as operações principais para vendedores.
  - **Métodos**:
    - `mainSeller(Scanner sc)`: Exibe um menu de opções para o usuário.
    - `sellerInsert(Scanner sc)`: Insere um novo vendedor.
    - `findById(Scanner sc)`: Encontra e exibe um vendedor pelo ID.
    - `showSeller()`: Exibe todos os vendedores ativos.
    - `deleteById(Scanner sc)`: Deleta um vendedor pelo ID.
    - `updateSeller(Scanner sc)`: Atualiza os detalhes de um vendedor.

- **`SellerUpdateService`** 🔧
  - **Descrição**: Fornece métodos para atualizar os detalhes de um vendedor existente.
  - **Métodos**:
    - `updateSellerDetails(Scanner sc, Seller seller)`: Permite ao usuário atualizar vários detalhes do vendedor.
    - `updateSellerName(Scanner sc, Seller seller)`: Atualiza o nome do vendedor.
    - `updateSellerBirthDate(Scanner sc, Seller seller)`: Atualiza a data de nascimento do vendedor.
    - `updateSellerBaseSalary(Scanner sc, Seller seller)`: Atualiza o salário base do vendedor.
    - `updateSellerDepartment(Scanner sc, Seller seller)`: Atualiza o departamento do vendedor.
    - `updateSellerWorkLevel(Scanner sc, Seller seller)`: Atualiza o nível de trabalho do vendedor.

#### `services.worklevel`

- **`WorkLevelService`** 🛠️
  - **Descrição**: Gerencia as operações principais para níveis de trabalho.
  - **Métodos**:
    - `mainWorkLevel(Scanner sc)`: Exibe um menu de opções para o usuário.
    - `showWorkLevels()`: Exibe todos os níveis de trabalho disponíveis.

#### `utilities`

- **`LoggerUtility`** 📜
  - **Descrição**: Utilitário para logging de mensagens usando SLF4J.
  - **Métodos**:
    - `info(String message, Object... params)`: Loga mensagens de informação.
    - `error(String message, Object... params)`: Loga mensagens de erro.
    - `debug(String message, Object... params)`: Loga mensagens de debug.
    - `warn(String message, Object... params)`: Loga mensagens de aviso.

## ⚙️ Configuração

### Dependências

Certifique-se de incluir as seguintes dependências no seu projeto:

- SLF4J para logging.
- Driver JDBC para o banco de dados usado.
- Outras bibliotecas necessárias para a interação com o banco de dados e funcionalidades específicas.

### Banco de Dados

- **Configuração**: Verifique o arquivo de configuração do banco de dados (`application.properties` ou similar) para garantir que as credenciais e URLs estejam corretos.
- **Estrutura**: As tabelas `sellers`, `departments` e `work_levels` devem estar criadas e configuradas de acordo com o esquema esperado pela aplicação.

## 🎯 Exemplos de Uso

### Menu Principal

```bash
# Iniciar a aplicação
java -cp target/your-project.jar application.Main

Menu Principal
Escolha uma das opções:
1 - Mostrar Funcionários
2 - Inserir Funcionários
3 - Procurar por ID
4 - Deletar Por ID
5 - Atualizar Funcionário
9 - Sair

Menu Departamentos
Escolha uma das opções:
1 - Mostrar Departamentos
2 - Inserir Departamento
3 - Procurar por ID
4 - Deletar Por ID
5 - Atualizar Departamento
9 - Sair

Atualização de Vendedor
Escolha o campo para atualizar:
1 - Nome
2 - Data de Nascimento
3 - Salário Base
4 - Departamento
5 - Nível de Trabalho
6 - Sair e Salvar


Inserção de Funcionário
Digite o nome do novo vendedor: João Silva
Digite a data de nascimento (yyyy-MM-dd): 1985-06-15
Digite o salário base: 2500.00
Escolha o departamento:
1 - Vendas
2 - Marketing
Escolha o nível de trabalho:
1 - Junior
2 - Pleno
3 - Senior


Exibição de Níveis de Trabalho
Níveis de trabalho disponíveis:
1 - Junior
2 - Pleno
3 - Senior


📝 Logging
Os logs são configurados para capturar informações detalhadas sobre a execução da aplicação. Verifique os arquivos de log para depuração e monitoramento de erros.

🤝 Contribuições
Se você deseja contribuir para o projeto, por favor, envie um pull request ou abra um issue. Agradecemos desde já suas contribuições!

Sinta-se à vontade para ajustar conforme necessário!




