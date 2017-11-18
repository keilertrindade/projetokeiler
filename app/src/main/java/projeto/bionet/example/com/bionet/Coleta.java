package projeto.bionet.example.com.bionet;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 02/11/2017.
 */

public class Coleta implements Serializable {

    private String id;

    private String material;
    private String medida;
    private String modalidade;
    private Float quantidade;
    private String entrega;

    private String cep;
    private String rua;
    private String num;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    private String proprietario;
    private String telefone;
    private String status;
    private Date data;

    private Float valor;

    private Boolean dinheiro;
    private Boolean debito;
    private Boolean credito;
    private Boolean mercadopago;

    public Coleta(){}

    public Coleta(String id, String material, String medida, String modalidade, Float quantidade, String entrega,
                  String cep, String rua, String num, String complemento, String bairro, String cidade,
                  String estado, String proprietario, String telefone, Date data, String status){

        this.id = id;

        this.material = material;
        this.medida = medida;
        this.modalidade = modalidade;
        this.quantidade = quantidade;
        this.entrega = entrega;

        this.cep = cep;
        this.rua = rua;
        this.num = num;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;

        this.proprietario = proprietario;
        this.telefone = telefone;
        this.status = status;
        this.data = data;


    }


    public Coleta(String id, String material, String medida, String modalidade, Float quantidade, String entrega,
                  String cep, String rua, String num, String complemento, String bairro, String cidade,
                  String estado, String proprietario, String telefone, Date data, String status, Float valor, Boolean dinheiro, Boolean debito,
                  Boolean credito, Boolean mercadopago){

        this.id = id;

        this.material = material;
        this.medida = medida;
        this.modalidade = modalidade;
        this.quantidade = quantidade;
        this.entrega = entrega;

        this.cep = cep;
        this.rua = rua;
        this.num = num;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;

        this.proprietario = proprietario;
        this.telefone = telefone;
        this.status = status;
        this.data = data;

        this.valor = valor;
        this.dinheiro = dinheiro;
        this.debito = debito;
        this.credito = credito;
        this.mercadopago = mercadopago;
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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



    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Boolean getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(Boolean dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Boolean getDebito() {
        return debito;
    }

    public void setDebito(Boolean debito) {
        this.debito = debito;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    public Boolean getMercadopago() {
        return mercadopago;
    }

    public void setMercadopago(Boolean mercadopago) {
        this.mercadopago = mercadopago;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
