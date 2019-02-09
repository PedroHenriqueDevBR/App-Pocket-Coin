# POO Projeto Final - Pocket Coin

## Descrição geral
Aplicativo desenvolvido como proposta de projeto de conclusão de disciplina, programação orientada a objetos, disponibilizada no Instituto Federal do Piauí, ministrada por professor Rogério Silva.

## Descrição do aplicativo
Este aplicativo destina-se a auxiliar pessoas no controle financeiro. dentre as principais funcionalidades que o aplicativo disponibiliza, a listagem dos dados do mês atual (incluindo todas as movimentações) é a principal forma de entender e perceber o quanto foi gasto no mês e onde o dinheiro foi gasto, a listagem dos dados acontece por meio de gráficos e mensagens.

**Logo do aplicativo**

<img src="https://github.com/PedroHenriqueBR2/POO-Projeto-Final/blob/master/app/src/main/res/mipmap-xxhdpi/ic_logo_aux.png" width="100" height="100" title="Teste" />



# Repositórios utilizados


## Gráficos
Para que houvesse a listagem dos gráficos no aplicativo, uma lib foi implementada ao app,  a lib implementada pertence ao repositório listado abaixo.
Endereço: [repositório de onde o gráfico foi utilizado](https://github.com/PhilJay/MPAndroidChart)

## Float Action Button Speed Dial
Para que o cadastro das movimentações fosse realizada de forma fácil, um botão flutuante foi adicionado a tela principal do aplicativo, a principal caracteristica desse botão flutuante é que ele serve para listar outros botões flutuantes que serão utilizados para realizar movimentações.
Observe a imagem abaixo para entender a utilização do botão.
Endereço: [repositório de onde o FloatActionButton Speed Dialog foi utilizado](https://github.com/yavski/fab-speed-dial)

![FloatActionButton Speed Dial](https://user-images.githubusercontent.com/36716898/52523813-216b0100-2c74-11e9-849d-0aeb5bad2bea.gif)


# Diagrama de classe

São sete as classes nas quais o aplicativo funciona, são elas: **Usuario**, **Movimentacao**, **Carteira**, **Categoria**, **Tag**, **Configuracao**, **NaturezaDaAcao**.

A relação entre as classes esta listada no diagrama abaixo.
![Modelagem das classes do aplicativo](https://user-images.githubusercontent.com/36716898/52525708-3fdcf680-2c8c-11e9-9ec6-7e1341d40129.png)


# Mapa do aplicativo
O aplicativo possui 10 (dez) atividades, que por sua vez serve para conectar o usuário as funcionalidade do aplicativo e para listar dados do aplicativo ao usuário, como exemplo cadastrar usuario, movimentacao, realizar login e listagem de dados. abaixo será listado o mapa do aplicativo e a descrição de cada atividade.

![mapa do aplicativo](https://user-images.githubusercontent.com/36716898/52526175-cc3ee780-2c93-11e9-991b-88163b4a8894.png)

### Login
Serve para que o usuário cadastrado entre no painel de controle particular.

<img src="https://user-images.githubusercontent.com/36716898/52526178-e24ca800-2c93-11e9-8e58-455ff17a5aa7.png" width="255" height="453" />


### Cadastrar Usuário
Serve para que um usuário possa se cadastrar fornecendo como dados necessários nome, login, senha e a senha repetida. Caso o login digitado já esteja cadastrado ou se as senhas digitadas forem diferentes o cadastro não acontecerá.

<img src="https://user-images.githubusercontent.com/36716898/52526192-f98b9580-2c93-11e9-8a63-f9eda3d97343.png" width="255" height="453" />


### Main
A unica funcionalidade da Main é a listagem do menu lateral, e a exibição de **Fragments**. 

### Home Fragment
Home é onde os principais dados do usuário será listado, total de receita e despesa no mês, informar onde o dinheiro gasto em maior quantidade, informar também se a receita é maior do que a despesa ou vice-versa, por meio de porcentagem ou valores líquidos.

<img src="https://user-images.githubusercontent.com/36716898/52526198-0dcf9280-2c94-11e9-8b76-fb30377d94cb.png" width="255" height="453" />

<img src="https://user-images.githubusercontent.com/36716898/52526201-1627cd80-2c94-11e9-9a3d-e7c1fb5acde5.png" width="255" height="453" />


### Registro Fragment
Todas as movimentações cadastradas no aplicativo será listada na tela de registros, todas as movimentaçẽos são separadas por mês de cadastro, e fica disponivel ao usuário selecionar as movimentações de qual mês.

<img src="https://user-images.githubusercontent.com/36716898/52526210-2b9cf780-2c94-11e9-9369-4ff18667dba3.png" width="255" height="453" />


### Configutações Fragment
Tela onde as principais configurações do app será listada, onde a sessão pode ser encerrada.

<img src="https://user-images.githubusercontent.com/36716898/52526216-38215000-2c94-11e9-9f89-f1e5ad3786e3.png" width="255" height="453" />


### Gerência de Carteiras
Tela para cadastrar e listar carteiras, um gráfico faz parte desta tela para mostrar de forma visual onde o dinheiro está investido.

<img src="https://user-images.githubusercontent.com/36716898/52526219-453e3f00-2c94-11e9-81ff-04d3746edc8c.png" width="255" height="453" />


### Gerência de Categoria
Tela para cadastrar e listar categorias.

<img src="https://user-images.githubusercontent.com/36716898/52526221-48392f80-2c94-11e9-8524-58f7192a903f.png" width="255" height="453" />


### Gerência de Tags
Tela para cadastrar e listar tags.

<img src="https://user-images.githubusercontent.com/36716898/52526223-4d967a00-2c94-11e9-89eb-89912919b586.png" width="255" height="453" />


### Nova Movimentação
A principal fonte de entrada de dados no aplicativo, funciona para receita ou despesa.

**Receita**

<img src="https://user-images.githubusercontent.com/36716898/52526224-538c5b00-2c94-11e9-8fa1-c64404daa197.png" width="255" height="453" />

**Despesa**

<img src="https://user-images.githubusercontent.com/36716898/52526234-5c7d2c80-2c94-11e9-9a99-9432d2ed74e7.png" width="255" height="453" />


### Nova Transferência
Transfere dinheiro de uma carteira para outra.

<img src="https://user-images.githubusercontent.com/36716898/52526239-6c950c00-2c94-11e9-9635-91d4f001e9bc.png" width="255" height="453" />
