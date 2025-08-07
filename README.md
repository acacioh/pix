<div align="center">
    <img src="./images/logo_home_onnitech.png" width="200">
    <p align="center">
      <a href="https://www.onnitech.com.br" target="_blank"><strong>www.onnitech.com.br</strong></a>
    </p>
</div>

## Índice
1. [Introdução](#introduction)
2. [Informações técnicas](#stack)
3. [Iniciando](#getting-started)
4. [Funcionalidades do projeto](#functions)

## <a name="introduction"></a> Introdução

Projeto idealizado para centralizar as requisições de callback para o recebimento das notificações dos webhooks do BTG.

#### <p style="color: yellow;">Importante</p>

Como o BTG possui apenas 1 ambiente para testes este serviço não está rodando em DEV, apenas em HML.

As alterações que forem feitas deverão continuar sendo enviadas para a branch `onnibank-dev`, porém o deploy dessa branch foi desativado.

## <a name="stack"></a>Informações técnicas
Você vai precisar das seguintes ferramentas:
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle 8.1.1](https://gradle.org/releases/)

## <a name="getting-started"></a> Iniciando
Para rodar o projeto siga os seguintes passos:
- Clone o projeto [pix-webhook-solution](https://github.com/Seven-Brasil-Informatica/pix-webhook-solution)
```bash
git clone git@github.com:Seven-Brasil-Informatica/pix-webhook-solution.git
```
- Abra o projeto na IDE de sua escolha, recomendamos o [IntelliJ](https://www.jetbrains.com/pt-br/idea/).
- Se no futuro existir a necessidade de expor endpoints será necessário realizar antes as configurações de segurança padrão do grupo.
- Essa API não cria transações, ela recebe um request, processa as regras de negócio e realiza o redirecionamento para o endpoint alvo e retorna a resposta por completo.

## <a name="functions"></a>Funcionalidades do projeto

<div align="center">
<h3>
<i>Processar requisição</i>
</h3>
</div>

Para iniciar o processamento de uma requisição são levadas em consideração duas variáveis, uma declarada como propriedade do yml com o nome de `onnitech.ispb-indirect-participant:38320462` que corresponde aos 8 dígitos do CNPJ da Onnibank, e uma outra presente no payload da requisição.

<b>PIX_PAYMENT</b>

Para esta requisição será considerado que quando no PAYLOAD da requisição o campo `debitParty.bank` iniciar com o valor definido na propriedade `onnitech.ispb-indirect-participant` definida no yml a requisição será do tipo PIX_INDIRETO, caso contrário será PIX_MERCHANT.
- Notificações de transferência PIX de saída
- Método: POST
- PATH: /callback/pix-payment
- ENDPOINT PIX_INDIRETO: /service-gateway/pix-cashout/callback/payment
- ENDPOINT PIX_MERCHANT: /service-gateway/pix/callback/cash-out

<b>PIX_PAYMENT_INBOUND</b>

Para esta requisição será considerado que quando no PAYLOAD da requisição o campo `creditParty.bank` iniciar com o valor definido na propriedade `onnitech.ispb-indirect-participant` definida no yml a requisição será do tipo PIX_INDIRETO, caso contrário será PIX_MERCHANT.
- Notificações de transferências PIX de entrada
- Método: POST
- PATH: /callback/pix-payment-inbound
- ENDPOINT PIX_INDIRETO: /service-gateway/pix-cashin/callback/inbound
- ENDPOINT PIX_MERCHANT: /service-gateway/pix/callback/cash-in

<b>PIX_REVERSAL</b>

Para esta requisição será considerado que quando no PAYLOAD da requisição o campo `creditParty.bank` iniciar com o valor definido na propriedade `onnitech.ispb-indirect-participant` definida no yml a requisição será do tipo PIX_INDIRETO, caso contrário será PIX_MERCHANT.
- Notificações de devolução de PIX de saída
- Método: POST
- PATH: /callback/pix-reversal
- ENDPOINT PIX_INDIRETO: /service-gateway/pix-cashout/callback/reversal
- ENDPOINT PIX_MERCHANT: /service-gateway/pix/callback/pix-cash-out-reversal

<b>PIX_REVERSAL_INBOUND</b>

Para esta requisição será considerado que quando no PAYLOAD da requisição o campo `debitParty.bank` iniciar com o valor definido na propriedade `onnitech.ispb-indirect-participant` definida no yml a requisição será do tipo PIX_INDIRETO, caso contrário será PIX_MERCHANT.
- Notificações de devolução de PIX de entrada
- Método: POST
- PATH: /callback/pix-reversal-inbound
- ENDPOINT PIX_INDIRETO: /service-gateway/pix-cashin/callback/reversal
- ENDPOINT PIX_MERCHANT: /service-gateway/pix/callback/pix-cash-in-reversal

<b>CASH_IN_APPROVAL</b>

- Esta requisição será será PIX_INDIRETO.
- Notificações para confirmação do pix de cash-in
- Método: POST
- PATH: /callback/pix-approval
- ENDPOINT PIX_INDIRETO: /service-gateway/pix-cashin/callback/approval

<b>DICT_CLAIM</b>

- Esta requisição será será PIX_INDIRETO.
- Notificações do fluxo de reivindicações de chave;
- Método:  POST
- PATH: /callback/claim
- ENDPOINT PIX_INDIRETO: /service-gateway/pix-dict/callback/claim