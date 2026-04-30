# T6 - Identificação de Isomorfismo em Árvores

Implementação em **Java** do Trabalho Prático 6 da disciplina **Resolução de Problemas com Grafos**.

O objetivo do trabalho é verificar se duas árvores não direcionadas são **isomorfas**, ou seja, se possuem a mesma estrutura de conexões, mesmo que os vértices tenham rótulos ou numerações diferentes.

A solução utiliza:

- leitura de duas entradas no formato `algs4`;
- representação principal do grafo com `Graph.java`;
- lista de adjacência com `Bag<Integer>`;
- validação das entradas como árvores;
- cálculo do centro ou dos centros da árvore;
- codificação canônica;
- comparação das codificações finais.

## Vídeo explicativo

Link do vídeo: https://youtu.be/3njUczj7RJ8

## Estrutura do projeto

```text
T6-final-Bag/
├── README.md
├── T6.md
├── dados/
│   ├── invalid-ciclo3.txt
│   ├── iso-path4-a.txt
│   ├── iso-path4-b.txt
│   ├── nao-iso-estrela5.txt
│   ├── nao-iso-path5.txt
│   ├── unico-centro-a.txt
│   └── unico-centro-b.txt
├── imgs/
│   ├── UNIFOR_logo1b.png
│   └── exemplo.png
├── refs/
│   └── youtube_videos.md
└── src/
    ├── Bag.java
    ├── Graph.java
    ├── In.java
    ├── Main.java
    ├── Stack.java
    ├── StdIn.java
    ├── StdOut.java
    └── TreeIsomorphism.java
```

## Arquivos principais

### `Main.java`

É o ponto de entrada do programa.

Responsabilidades:

- receber dois arquivos pela linha de comando;
- construir os grafos com `Graph` e `In`;
- imprimir a lista de adjacência de cada árvore;
- chamar a validação das entradas;
- exibir os centros encontrados;
- gerar e comparar as codificações canônicas;
- imprimir o veredito final.

A leitura das duas entradas é feita assim:

```java
Graph tree1 = new Graph(new In(args[0]));
Graph tree2 = new Graph(new In(args[1]));
```

### `Graph.java`

É a estrutura principal de representação do grafo, conforme solicitado no enunciado.

A lista de adjacência é implementada com `Bag<Integer>`:

```java
private Bag<Integer>[] adj;
```

Cada posição do array representa um vértice, e cada `Bag<Integer>` armazena os vizinhos desse vértice.

### `TreeIsomorphism.java`

Concentra a lógica principal do trabalho.

Responsabilidades:

- validar se o grafo é uma árvore;
- verificar se o grafo possui `E = V - 1`;
- verificar se o grafo é conexo;
- encontrar o centro ou os centros por remoção iterativa de folhas;
- gerar a codificação canônica;
- ordenar os códigos dos filhos;
- retornar a representação final da árvore.

## Como compilar

Entre na pasta `src`:

```bash
cd src
```

Compile todos os arquivos Java:

```bash
javac *.java
```

Durante a compilação, podem aparecer avisos como:

```text
Note: In.java uses or overrides a deprecated API.
Note: Graph.java uses unchecked or unsafe operations.
```

Esses avisos não impedem a execução. Eles estão relacionados aos arquivos base da biblioteca `algs4`, principalmente ao uso de recursos antigos em `In.java` e ao array genérico de `Bag<Integer>` em `Graph.java`.

## Como executar

Ainda dentro da pasta `src`, execute o programa passando dois arquivos de entrada:

```bash
java Main ../dados/iso-path4-a.txt ../dados/iso-path4-b.txt
```

Outros testes disponíveis:

```bash
java Main ../dados/nao-iso-path5.txt ../dados/nao-iso-estrela5.txt
java Main ../dados/unico-centro-a.txt ../dados/unico-centro-b.txt
java Main ../dados/invalid-ciclo3.txt ../dados/iso-path4-a.txt
```

## Exemplo de execução

Comando:

```bash
java Main ../dados/iso-path4-a.txt ../dados/iso-path4-b.txt
```

Saída esperada:

```text
========================================
T6 - Isomorfismo em Arvores
========================================

Arvore 1 - lista de adjacencia:
4 vertices, 3 edges 
0: 1 
1: 2 0 
2: 3 1 
3: 2 

Arvore 2 - lista de adjacencia:
4 vertices, 3 edges 
0: 2 
1: 3 2 
2: 1 0 
3: 1 

Validacao da entrada 1:
Entrada valida: o grafo e uma arvore.

Validacao da entrada 2:
Entrada valida: o grafo e uma arvore.

Centros da arvore 1: [1, 2]
Centros da arvore 2: [1, 2]

Codificacao canonica da arvore 1:
((())())

Codificacao canonica da arvore 2:
((())())

Veredito final:
As arvores sao isomorfas.
```

## Interpretação da saída

A saída mostra:

1. a lista de adjacência das duas entradas;
2. a validação de que cada entrada é uma árvore;
3. os centros encontrados em cada árvore;
4. a codificação canônica de cada árvore;
5. o veredito final.

No exemplo acima, as duas árvores geram a mesma codificação canônica:

```text
((())())
```

Como as codificações são iguais, o programa conclui que as árvores são isomorfas.

## Validação das entradas

Antes de comparar as árvores, o programa verifica se cada entrada realmente representa uma árvore.

A validação ocorre em `TreeIsomorphism.java`, no método `isTree()`.

São verificadas três condições:

1. o grafo precisa ter pelo menos um vértice;
2. o número de arestas precisa ser igual a `V - 1`;
3. o grafo precisa ser conexo.

A conectividade é verificada por uma busca em profundidade no método `dfsConnectivity()`.

Se alguma dessas condições falhar, o programa interrompe a comparação e informa que as entradas precisam representar árvores válidas.

## Centro da árvore

O centro da árvore é encontrado no método `getCenters()`, em `TreeIsomorphism.java`.

A ideia é remover as folhas da árvore em camadas:

1. calcula-se o grau de cada vértice;
2. os vértices com grau `0` ou `1` são colocados em uma `Bag<Integer>` de folhas;
3. essas folhas são processadas;
4. os graus dos vizinhos são reduzidos;
5. novos vértices com grau `1` passam a ser novas folhas;
6. o processo continua até sobrarem um ou dois centros.

A implementação usa `Bag<Integer>` para armazenar as folhas e as novas folhas durante esse processo.

## Codificação canônica

A codificação canônica é gerada em `TreeIsomorphism.java`, principalmente nos métodos `getCanonicalEncoding()` e `encode()`.

O método `getCanonicalEncoding()` obtém o centro ou os centros da árvore e chama o método recursivo `encode()`.

O método `encode()` funciona assim:

- se o vértice não possui filhos, retorna `()`;
- se possui filhos, calcula recursivamente o código de cada filho;
- armazena os códigos dos filhos em uma `Bag<String>`;
- converte temporariamente essa `Bag<String>` para array;
- ordena os códigos com `Arrays.sort()`;
- concatena os códigos ordenados dentro de parênteses.

Exemplo de código gerado:

```text
((())())
```

## Por que ordenar os códigos dos filhos?

A ordenação dos códigos dos filhos é necessária porque a ordem dos vizinhos no arquivo de entrada pode variar.

Sem essa ordenação, duas árvores estruturalmente iguais poderiam gerar strings diferentes apenas porque os filhos foram lidos em ordens diferentes.

Por isso, os códigos dos filhos são ordenados lexicograficamente antes da concatenação.

A `Bag` é usada para armazenar os códigos dos filhos, mas como a `Bag` do `algs4` não possui operação de ordenação, os códigos são convertidos temporariamente para array apenas nessa etapa.

Isso mantém a representação principal com `Graph.java` e `Bag`, ao mesmo tempo em que garante uma codificação canônica correta.

## Resultado esperado

Se as codificações canônicas das duas árvores forem iguais, o programa informa:

```text
As arvores sao isomorfas.
```

Se forem diferentes, informa:

```text
As arvores nao sao isomorfas.
```

Caso alguma entrada não seja uma árvore válida, a comparação é interrompida.
