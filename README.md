# trabalho do richard

integrantes: 
Guilherme Queiroz
Gabrielly Rodrigues
Victor Guilherme
Guilherme Carvalho


Este projeto consiste em um sistema simples em Java que realiza operações de impressão utilizando a DLL E1_Impressora01.dll.
O código permite abrir conexão, fechar conexão, imprimir textos, abrir gaveta e outras funções fornecidas pela biblioteca nativa da impressora.

Estrutura Geral do Programa

O programa é executado no console e utiliza a biblioteca JNA para acessar funções nativas da impressora.
O usuário escolhe uma função através do menu e informa os parâmetros necessários.

As principais operações são:
	•	Abrir conexão com a impressora
	•	Imprimir texto com configuração personalizada
	•	Abrir gaveta (Elgin)
	•	Fechar conexão
	•	Sair do sistema


Arquivos Importantes
	•	Main.java – Arquivo principal com o menu e a chamada das funções.
	•	ImpressoraDLL.java – Interface com todas as funções importadas da DLL.
	•	E1_Impressora01.dll – Biblioteca nativa usada para acessar a impressora.
	•	libs/ – Pasta onde fica a DLL e os arquivos XML.




Funcionamento do Programa

Abertura de Conexão


Antes de realizar qualquer impressão, o programa precisa abrir a conexão 

Se a conexão falhar, nenhuma outra função pode ser usada.



Impressão de Texto

O usuário informa:
	•	texto a ser impresso
	•	alinhamento (esquerda, centro, direita)
	•	estilo (negrito, sublinhado, etc.)
	•	tamanho da fonte

O método usado é: ImpressoraDLL.INSTANCE.ImpressaoTexto(dados, posicao, estilo, tamanho);


Se o retorno for positivo, a impressão foi enviada com sucesso.

Abertura da Gaveta

Função utilizada para abrir gavetas Elgin que estão conectadas à impressora:

ImpressoraDLL.INSTANCE.AbreGavetaElgin();

Isso garante que a impressora não fique travada para o próximo uso


Como funciona o menu

O programa mostra opções numéricas.
O usuário digita o número desejado, e a função correspondente é chamada.



1 - Abrir conexão  
2 - Impressão de texto  
3 - Abrir gaveta  
0 - Sair  


Observações Importantes
	•	Não é possível imprimir antes de abrir a conexão.
	•	O programa só envia comandos; o driver da impressora faz o restante.
	•	A DLL deve estar acessível no classpath ou na pasta do próprio projeto.

