import java.io.File
import java.util.*

class Funcionario(val nome: String, val cargo: String, val salario: String){
    override fun toString(): String {
        return "Nome: $nome | Cargo: $cargo | Salário: R$ $salario"
    }
}

class GerenciarFuncionarios{
    private val file = File("Funcionarios.txt")
    private val funcionarios = mutableListOf<Funcionario>()

    init {
        if (file.exists()){
            file.forEachLine {
                val (nome, cargo, salario) = it.split(";")
                funcionarios.add(Funcionario(nome, cargo, salario))
            }
        } else{
            file.createNewFile()
        }
    }

    fun cadastrarFuncionario(){
        print("Digite o nome do funcionário: ")
        var nome = readln()
        nome = nome.replaceFirstChar { it.titlecase() }
        print("Digite o cargo do funcionário: ")
        var cargo = readln()
        cargo = cargo.replaceFirstChar { it.titlecase() }
        print("Digite o salário do funcionário: R$ ")
        val salario = readln()
        if (nome != null && cargo != null && salario != null){
            val funcionario = Funcionario(nome, cargo, salario)
            println("Funcionário adicionado com sucesso!\n")
            funcionarios.add(funcionario)
            file.appendText("$nome;$cargo;$salario;\n")
        } else{
            println("Dados inválidos\n")
        }
    }

    fun listarFuncionarios(){
        if (funcionarios.isEmpty()){
            println("Nenhuma pessoa adicionada ainda\n")
        } else{
            println("Lista de funcionários: ")
            for (funcionario in funcionarios){
                println(funcionario)
            }

        }
    }

    fun buscarFuncionario(){
        print("Digite o nome do funcionário que deseja buscar: ")
        val nome = readln()
        if (nome != null){
            val funcionario = funcionarios.find { it.nome.equals(nome, true) }

            if (funcionario != null){
                println(funcionario)
            } else {
                println("Pessoa não encontrada!")
            }
        } else{
            println("Nome inválido!")
        }
    }

    fun excluirFuncionario() {
        print("Digite o nome do funcionário que deseja remover: ")
        val nome = readln()
        val funcionario = funcionarios.find { it.nome.equals(nome, true) }
        if (funcionario != null) {
            funcionarios.removeIf { it.nome.equals(nome, true) }
            println("Funcionário removido!")
            val funcionariosStr = funcionarios.joinToString("") {
                "${it.nome};${it.cargo};${it.salario};\n"
            }
            file.writeText(funcionariosStr)
        } else {
            println("Funcionário não existente")
        }
    }

}

fun main() {
    val gerenciador = GerenciarFuncionarios()

    do {
        println("\n1 - Adicionar funcionário")
        println("2 - Exibir lista de funcionários")
        println("3 - Buscar funcionário pelo nome")
        println("4 - Remover funcionário pelo nome")
        println("5 - Sair\n")

        print("Selecione uma opção: ")
        val opcao = readln().toIntOrNull()

        when(opcao){
            1 -> {
                gerenciador.cadastrarFuncionario()
            }
            2 -> {
                gerenciador.listarFuncionarios()
            }
            3 -> {
                gerenciador.buscarFuncionario()
            }
            4 -> {
                gerenciador.excluirFuncionario()
            }
            5 -> {
                println("Encerrando programa...")
                break
            }
        }
    } while (true)
}
