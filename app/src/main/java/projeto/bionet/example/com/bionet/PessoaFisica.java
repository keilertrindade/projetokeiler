package projeto.bionet.example.com.bionet;

/**
 * Created by Administrador on 07/10/2017.
 */

public class PessoaFisica {
    private String email;
    private String senha;

    private String nome;
    private String sobrenome;
    private String cpf;

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

    public PessoaFisica (){

    }

    public PessoaFisica(String email, String senha, String nome, String sobrenome, String cpf, String cep,
                        String rua, String bairro, String cidade, String estado){
        super();
        this.email = email;
        this.senha = senha;

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;

        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
