# POO Projeto Final - Pocket Coin

## Descrição geral
Aplicativo desenvolvido como proposta de projeto de conclusão de disciplina, programação orientada a objetos, disponibilizada no Instituto Federal do Piauí, ministrada por professor Rogério Silva.

## Descrição do aplicativo
Este aplicativo destina-se a auxiliar pessoas no controle financeiro. dentre as principais funcionalidades que o aplicativo disponibiliza, a listagem dos dados do mês atual (incluindo todas as movimentações) é a principal forma de entender e perceber o quanto foi gasto no mês e onde o dinheiro foi gasto, a listagem dos dados acontece por meio de gráficos e mensagens.


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

[Mapa do aplicativo]

### Login
Serve para que o usuário cadastrado entre no painel de controle particular.

[imagem]


### Cadastrar Usuário
Serve para que um usuário possa se cadastrar fornecendo como dados necessários nome, login, senha e a senha repetida. Caso o login digitado já esteja cadastrado ou se as senhas digitadas forem diferentes o cadastro não acontecerá.

[imagem]


### Main
A unica funcionalidade da Main é a listagem do menu lateral, e a exibição de **Fragments**. 

### Home Fragment
Home é onde os principais dados do usuário será listado, total de receita e despesa no mês, informar onde o dinheiro gasto em maior quantidade, informar também se a receita é maior do que a despesa ou vice-versa, por meio de porcentagem ou valores líquidos.

[imagem]

[imagem]


### Registro Fragment
Todas as movimentações cadastradas no aplicativo será listada na tela de registros, todas as movimentaçẽos são separadas por mês de cadastro, e fica disponivel ao usuário selecionar as movimentações de qual mês.

[imagem]


### Configutações Fragment
Tela onde as principais configurações do app será listada, onde a sessão pode ser encerrada.

[imagem]


### Gerência de Carteiras
Tela para cadastrar e listar carteiras, um gráfico faz parte desta tela para mostrar de forma visual onde o dinheiro está investido.

[imagem]


### Gerência de Categoria
Tela para cadastrar e listar categorias.

[imagem]


### Gerência de Tags
Tela para cadastrar e listar tags.

[imagem]


### Nova Movimentação
A principal fonte de entrada de dados no aplicativo, funciona para receita ou despesa.

[imagem]

[imagem]


### Nova Transferência
Transfere dinheiro de uma carteira para outra.

[imagem]
