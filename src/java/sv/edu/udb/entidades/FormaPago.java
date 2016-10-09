/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author JAVA
 */
@Entity
@Table(name = "FORMA_PAGO", catalog = "MODULO5S2", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "FormaPago.findAll", query = "SELECT f FROM FormaPago f")})
public class FormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDFORMA_PAGO")
    private Integer idformaPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idformaPago")
    private List<Factura> facturaList;

    public FormaPago() {
    }

    public FormaPago(Integer idformaPago) {
        this.idformaPago = idformaPago;
    }

    public FormaPago(Integer idformaPago, String descripcion) {
        this.idformaPago = idformaPago;
        this.descripcion = descripcion;
    }

    public Integer getIdformaPago() {
        return idformaPago;
    }

    public void setIdformaPago(Integer idformaPago) {
        this.idformaPago = idformaPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idformaPago != null ? idformaPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormaPago)) {
            return false;
        }
        FormaPago other = (FormaPago) object;
        if ((this.idformaPago == null && other.idformaPago != null) || (this.idformaPago != null && !this.idformaPago.equals(other.idformaPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.udb.entidades.FormaPago[ idformaPago=" + idformaPago + " ]";
    }
    
}
