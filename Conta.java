import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Conta {

    private int numero;
    private Cliente nome_titular;
    protected double saldo;

    public Conta(int numero, String nome_titular, String cpfTitular){
        this.numero = numero;
        this.nome_titular = new Cliente(nome_titular, cpfTitular);
        this.saldo = 0;
    }

    public void depositar(double valor){
        this.saldo = this.getSaldo() + valor;
    }

    public abstract boolean sacar(double valor);

    public double getSaldo() {
        return saldo;
    }
    public int getNumero() {
        return numero;
    }
    public Cliente getNome_titular() {
        return nome_titular;
    }
        
    public void setNome_titular (Cliente nome_titular){
        this.nome_titular = nome_titular;
    }
}

class ContaEspecial extends Conta {
    private double limite;

    public ContaEspecial(int numero, String nome_titular, String cpfTitular, double limite){
        super(numero, nome_titular, cpfTitular);
        this.limite = limite;

    }

    @Override
    public boolean sacar(double valor){
        if(valor <= this.limite + this.saldo){
            this.saldo -= valor;
            return true;
        }
        else return false;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite){
        this.limite = limite;
    }
}

class ContaPoupanca extends Conta {

    public ContaPoupanca(int numero, String nome_titular, String cpfTitular){
        super(numero, nome_titular, cpfTitular);
    }

    public void reajustar(double percentual){
        saldo = saldo + saldo*percentual;
    }

    @Override
    public boolean sacar(double valor){
        if(this.getSaldo() >= valor){
            this.saldo -= valor;
            return true;
        }
        else return false;
    }
}

class ContaNormal extends Conta {

    public ContaNormal(int numero, String nome_titular, String cpfTitular){
        super(numero, nome_titular, cpfTitular);
    }

    @Override
    public boolean sacar(double valor){
        if(this.getSaldo() >= valor){
            this.saldo -= valor;
            return true;
        }
        else return false;
    }
}

class Cliente {
    private String nome;
    private String cpf;

    public Cliente (String nome, String cpf){
        this.setNome(nome);
        this.setCpf(cpf);
    }

    public String getNome(){
        return nome;
    }

    public void setNome (String nome){
        this.nome = nome;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf (String cpf){
        this.cpf = cpf;
    }
}


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Conta> contas = new ArrayList<>();
        int numeroDaConta = 0;
        
        while (true) {
            System.out.println("Digite 1 para criar uma conta, 2 para ver o saldo de uma conta, 3 para sacar, 4 para depositar ou qualquer outra tecla para sair:");
            String opcao = scanner.nextLine();
            
            if (opcao.equals("1")) {
                System.out.println("Digite o nome do titular da conta:");
                String nomeTitular = scanner.nextLine();

                System.out.println("Digite o cpf do titular da conta:");
                String cpf = scanner.nextLine();

                numeroDaConta++;
                System.out.println("Digite o tipo de conta a ser criada: 1 para normal, 2 para especial, 3 para poupança. outro numero: inválido.");
                String tipoConta = scanner.nextLine();
                
                switch(tipoConta){
                    case "1":
                        Conta conta = new ContaNormal(numeroDaConta, nomeTitular, cpf);
                        contas.add(conta);
        
                        System.out.println("Conta criada com sucesso! O número da conta é " + conta.getNumero());
                        break;
                    case "2":
                        System.out.println("Insira o limite da conta especial:");
                        double limite = scanner.nextDouble();
                        scanner.nextLine();

                        ContaEspecial contaE = new ContaEspecial(numeroDaConta, nomeTitular, cpf, limite); 
                        contas.add(contaE);

                        System.out.println("Conta criada com sucesso! O número da conta é " + contaE.getNumero());
                        break;
                    case "3":
                        ContaPoupanca contaP = new ContaPoupanca(numeroDaConta, nomeTitular, cpf); 
                        contas.add(contaP);

                        System.out.println("Conta criada com sucesso! O número da conta é " + contaP.getNumero());
                        break;
                    default: break;
                }
            
            } else if (opcao.equals("2")) {
                System.out.println("Digite o número da conta:");
                int numeroConta = scanner.nextInt();
                scanner.nextLine();

                Conta conta = null;
                for (Conta c : contas) {
                    if (c.getNumero() == numeroConta) {
                        conta = c;
                        break;
                    }
                }

                if (conta == null) {
                    System.out.println("Conta não encontrada.");
                } else {
                    System.out.println("Saldo da conta de " + conta.getNome_titular().getNome() + ": R$" + conta.getSaldo());
                }
            } else if (opcao.equals("3")) {
                System.out.println("Digite o número da conta:");
                int numeroConta = scanner.nextInt();
                scanner.nextLine();

                Conta conta = null;
                for (Conta c : contas) {
                    if (c.getNumero() == numeroConta) {
                        conta = c;
                        break;
                    }
                }

                if (conta == null) {
                    System.out.println("Conta não encontrada.");
                } else {
                    System.out.println("Digite o valor a ser sacado:");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    if (conta.sacar(valor)) {
                        System.out.println("Saque efetuado com sucesso! Novo saldo: R$" + conta.getSaldo());
                    } else {
                        System.out.println("Saldo insuficiente.");
                    }
                }

            } else if (opcao.equals("4")) {
                System.out.println("Digite o número da conta:");
                int numeroConta = scanner.nextInt();

                Conta conta = null;
                for (Conta c : contas) {
                    if (c.getNumero() == numeroConta) {
                        conta = c;
                        break;
                    }
                }
                if (conta == null) {
                    System.out.println("Conta não encontrada.");
                } else {
                    System.out.println("Digite o valor a ser depositado:");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    conta.depositar(valor);
                    System.out.println("Novo saldo: R$" + conta.getSaldo());
                }
            } else break;
        }
        scanner.close();
    }
}
