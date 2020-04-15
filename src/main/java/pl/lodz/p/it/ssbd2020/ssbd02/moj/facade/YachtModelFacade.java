/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.moj.facade;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtModelFacade extends AbstractFacade<YachtModel> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public YachtModelFacade() {
        super(YachtModel.class);
    }
}
