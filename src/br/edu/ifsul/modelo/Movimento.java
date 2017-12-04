/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Huelison
 */
@Entity
@Table(name = "Movimento")
public class Movimento implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_movimento", sequenceName = "seq_movimento_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_movimento", strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotNull(message = "A data de movimento não pode ser nula")
    @Temporal(TemporalType.DATE)
    @Column(name = "data", nullable = false)
    private Calendar data;
    @NotNull(message = "A descrição não pode ser nula")
    @Length(max = 50, message = "A descrição não pode ter mais que {max} caracteres")
    @NotBlank(message = "A descrição não pode estar em branco")
    @Column(name = "descricao", length = 50, nullable = false)
    private String descricao;
    @NotNull(message = "A observação deve ser informada")
    @NotBlank(message = "A observação não pode estar em branco")
    @Column(name = "observacao", nullable = false, columnDefinition = "text")
    private String observacao;
    @NotNull(message = "O valor deve ser informado")
    @Min(0)
    @Column(name = "valor", nullable = false, columnDefinition = "numeric(10,2)")
    private Double valor;
    @NotBlank(message = "O tipo de pagamento deve ser informado!")
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;//armazena o tipo de movimentação

    @ManyToOne
    @JoinColumn(name = "pessoa", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pessoa"))
    private Pessoa pessoa;

    @OneToMany(mappedBy = "movimento", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Movimento() {
    }

    public void adicionarPagamento(Pagamento obj) {
        obj.setMovimento(this);
        this.pagamentos.add(obj);
    }

    public void removerPagamentos(int idx) {
        this.pagamentos.remove(idx);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movimento other = (Movimento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

}
