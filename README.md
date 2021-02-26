<h1 align="center">
App - Pocket Coin :bar_chart:
</h1>

<p align="center">
suas finanças na pauma da sua mão.
</p>

<p align="center">
<a href="https://github.com/PedroHenriqueDevBR/"><img alt="GitHub forks" src="https://img.shields.io/badge/make%20by-PedroHenriqueDevBR-blue?style=plastic"></a>
<img alt="GitHub stars" src="https://img.shields.io/github/stars/pedrohenriquedevbr/app-pocket-coin?style=social">
</p>

<hr>

<img src="https://user-images.githubusercontent.com/36716898/52526198-0dcf9280-2c94-11e9-8b76-fb30377d94cb.png" width="255" height="453" />
<img src="https://user-images.githubusercontent.com/36716898/52526201-1627cd80-2c94-11e9-9a3d-e7c1fb5acde5.png" width="255" height="453" />
  
# :anchor: Descrição geral

Aplicativo desenvolvido como proposta de projeto de conclusão de disciplina, programação orientada a objetos, disponibilizada no Instituto Federal do Piauí, ministrada pelo professor Rogério Silva.

## Descrição do aplicativo
Pocket Coin é um aplicativo android que destina-se a auxiliar pessoas no controle de gastos. Desenvolvido utilizando como ambiente de desenvolvimento a IDEA Android Studio, Java como linguagem de programação. Entre as principais funcionalidades que o aplicativo disponibiliza, o registro dos gastos separados por mês é a principal a ser destacada, a listagem dos dados do mês atual (incluindo todas as movimentações) é a principal forma de entender e perceber o quanto de dinheiro foi gasto no mês e onde o dinheiro foi gasto, a listagem dos dados acontece por meio de gráficos e mensagens.
Caso seja de interesse do usuário, os dados das movimentações de meses anteriores também ficam registrados no banco de dados, sendo assim basta que o usuário acesse a tela de registros para selecionar o mês para a listagem dos dados.

Para que o Pocket Coin funcione de forma estável é preciso que a versão minima do android seja a 22 (Android 5.1).

### Logo do aplicativo - Pocket Coin 
<img src="https://raw.githubusercontent.com/PedroHenriqueDevBR/App-Pocket-Coin/master/app/src/main/res/mipmap-xxhdpi/ic_logo_aux.png" width="100" height="100" title="Teste" />

# Repositórios utilizados

## ObjectBox
A persistencia escolhida para este projeto foi o ObjectBox, por ser simples, rápido, e com uma curva de aprendizagem bastante consideravel.

[Site oficial do ObjectBox](https://objectbox.io/)

![imagem](https://objectbox.io/wordpress/wp-content/uploads/2017/08/Group-6-Copy-46.png)

## Gráficos
Para que houvesse a listagem dos gráficos no aplicativo, uma lib foi implementada ao app,  a lib implementada pertence ao repositório listado abaixo.

Endereço: [repositório de onde o gráfico foi utilizado](https://github.com/PhilJay/MPAndroidChart)

![imagem](https://camo.githubusercontent.com/469a2460e3ae032f18db106cfae67adeea99e8ba/68747470733a2f2f7261772e6769746875622e636f6d2f5068696c4a61792f4d5043686172742f6d61737465722f64657369676e2f666561747572655f677261706869635f736d616c6c65722e706e67)


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


# Descrição do desenvolviemnto

### Camada model

Primeiramente, antes de iniciar a codificação do aplicativo, passei um tempo  definindo como seria a camada de modelo. Levando em consideração alguns requisitos solicitados pelo professor, tais como persistência de dados e cadastro de usuário, o inicio da modelagem aconteceu a partir da classe usuário, para que logo em seguida a classe Movimentação fosse criada, o resultado final da modelagem pode ser encontrada na sessão **Diagrama de classe** na parte superior desta página.

### Camada controller

A princípio o aplicativo seria desenvolvido utilizando o modelo MVVM, porém a medida que o aplicativo aumentava a quantidade de funções aumentava, e para solucionar este problema de forma que o código fonte do aplicativo fosse legível uma nova camada fi adicionada, a camada de controle (controller), para facilitar a leitura do código fonte e futuramente a manutenção.
Mais de uma camada de controle foi adicionada ao projeto pois cada controller possui um alvo, como é o caso do controller Carteira, que possui apenas métodos que serão necessaŕios para manipular o objeto carteira.

### Camada View
A camada View contém todos os arquivos que são Activity.java e Fragment.java

### Camada Adapter
A camada Adapter controla os RecycleViews dispostos no projeto, controlando ainda a interação do usuário com algum determinado item listado.

### Camada Dal
A camada Dal controla a persistência dos dados, mais especificamente o ObjectBox e o SharedPreferences.

### Camada exceptions
A camada Exceptions, desenvolvida apenas para tornar o código fonte mais legível, através da criação de exceptions personalizados.

### Camada Testes
A camada Testes contém um arquivo (Main.java), que é responsável por realizar testes na camada de modelo através do terminal.
