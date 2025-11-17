# Telas de Atualização Criadas

## Resumo

Foram criadas 3 novas telas de atualização para o sistema CairuPay:

### 1. TelaAtualizarCliente
**Arquivo:** `src/view/TelaAtualizarCliente.java`

**Funcionalidades:**
- Buscar cliente por ID
- Exibir dados do cliente nos campos
- Atualizar nome, endereço, UF, telefone, documento e e-mail
- Validação de campos obrigatórios
- Campos desabilitados até buscar um cliente

**Como usar:**
1. Informe o ID do cliente
2. Clique em "Buscar"
3. Os campos serão habilitados e preenchidos
4. Edite os dados desejados
5. Clique em "Atualizar"

### 2. TelaAtualizarDivida
**Arquivo:** `src/view/TelaAtualizarDivida.java`

**Funcionalidades:**
- Buscar dívida por código
- Atualizar credor, devedor, data de atualização e valor
- Validação: credor deve ser diferente do devedor
- ComboBox com lista de clientes
- Campos desabilitados até buscar uma dívida

**Como usar:**
1. Informe o código da dívida
2. Clique em "Buscar"
3. Os campos serão habilitados e preenchidos
4. Selecione novo credor/devedor se necessário
5. Edite data e valor se necessário
6. Clique em "Atualizar"

### 3. TelaAtualizarPagamento
**Arquivo:** `src/view/TelaAtualizarPagamento.java`

**Funcionalidades:**
- Buscar pagamento por ID
- Exibir informações da dívida associada
- Atualizar data de pagamento e valor pago
- Validação de valor mínimo
- Campos desabilitados até buscar um pagamento

**Como usar:**
1. Informe o ID do pagamento
2. Clique em "Buscar Pagamento"
3. Os dados serão exibidos
4. Edite data e valor se necessário
5. Clique em "Atualizar"

## Métodos Adicionados nos Controllers

### ClienteController
- `atualizarCliente(Cliente cliente)` - Atualiza um cliente

### DividaController
- `atualizarDivida(Divida divida)` - Atualiza uma dívida

### PagamentoController
- `atualizarPagamento(Pagamento pagamento)` - Atualiza um pagamento

## Métodos Adicionados nos DAOs

### ClienteDAO
- `atualizar(Cliente cliente)` - Já existia, mantido

### DividaDAO
- `atualizar(Divida divida)` - Já existia, mantido

### PagamentoDAO
- `atualizar(Pagamento pagamento)` - Criado novo método

## Integração com TelaPrincipal

Para adicionar essas telas ao menu principal, você precisa:

1. Abrir `TelaPrincipal.java`
2. Adicionar itens de menu:

```java
// No menu Cliente
JMenuItem itemAtualizarCliente = new JMenuItem("Atualizar");
itemAtualizarCliente.addActionListener(e -> abrirTela(new TelaAtualizarCliente(), "Atualizar Cliente"));
menuCliente.add(itemAtualizarCliente);

// No menu Dívida
JMenuItem itemAtualizarDivida = new JMenuItem("Atualizar");
itemAtualizarDivida.addActionListener(e -> abrirTela(new TelaAtualizarDivida(), "Atualizar Dívida"));
menuDivida.add(itemAtualizarDivida);

// No menu Pagamento
JMenuItem itemAtualizarPagamento = new JMenuItem("Atualizar");
itemAtualizarPagamento.addActionListener(e -> abrirTela(new TelaAtualizarPagamento(), "Atualizar Pagamento"));
menuPagamento.add(itemAtualizarPagamento);
```

## Validações Implementadas

### TelaAtualizarCliente
- Nome obrigatório
- Documento obrigatório

### TelaAtualizarDivida
- Credor e devedor obrigatórios
- Credor deve ser diferente do devedor
- Valor obrigatório

### TelaAtualizarPagamento
- Valor pago obrigatório
- Valor deve ser maior que zero

## Observações

- Todas as telas seguem o padrão MDI (JInternalFrame)
- Campos ficam desabilitados até buscar um registro
- Validações são feitas antes de atualizar
- Mensagens de sucesso/erro são exibidas via JOptionPane
- Os campos são limpos após atualização bem-sucedida

