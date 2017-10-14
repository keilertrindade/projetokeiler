package projeto.bionet.example.com.bionet;


/**
 * Created by Administrador on 07/10/2017.
 */

public class PessoaJuridica {

    private String email;
    private String senha;

    private String rsocial;
    private String cnpj;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;


    public PessoaJuridica (){

    }

    public PessoaJuridica(String email, String senha, String rsocial, String cnpj, String cep,
                        String rua, String bairro, String cidade, String estado){
        super();
        this.email = email;
        this.senha = senha;

        this.rsocial = rsocial;
        this.cnpj = cnpj;

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

    public String getRsocial() {
        return rsocial;
    }

    public void setRsocial(String rsocial) {
        this.rsocial = rsocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
