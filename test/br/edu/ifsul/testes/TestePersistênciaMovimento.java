/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.testes;

import br.edu.ifsul.modelo.Movimento;
import br.edu.ifsul.modelo.Pagamento;
import br.edu.ifsul.modelo.Pessoa;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Huelison
 */
public class TestePersistênciaMovimento {

    EntityManagerFactory emf;
    EntityManager em;

    public TestePersistênciaMovimento() {
    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("TA-6N1-2017-CONTROLECOLETAS-MODELPU");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void teste() {
        boolean exception = false;

        try {
            Movimento obj = new Movimento();
            obj.setDescricao("Este é um teste de persistencia");
            obj.setObservacao("aqui é a observação do teste");
            obj.setData(Calendar.getInstance());
            obj.setPessoa(em.find(Pessoa.class, 1));
            obj.setValor(50.0);
            obj.setTipo("EV");

            Pagamento pag = new Pagamento();

            pag.setData(Calendar.getInstance());
            pag.setMovimento(obj);
            pag.setValor(50.00);
            obj.getPagamentos().add(pag);
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            exception = true;
            e.printStackTrace();
        }
        Assert.assertEquals(false, exception);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
