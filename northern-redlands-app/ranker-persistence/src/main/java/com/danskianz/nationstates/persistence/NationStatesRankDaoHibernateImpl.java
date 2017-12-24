package com.danskianz.nationstates.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * Rank Persistence Service Hibernate Implementation
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@DedicatedRankerStore
public class NationStatesRankDaoHibernateImpl implements NationStatesRankDao {

    @PersistenceContext(unitName = PersistenceConstants.PERSISTENCE_UNIT)
    private EntityManager entityManager;

    public NationStatesRankDaoHibernateImpl() throws ClassNotFoundException {
        Class.forName(PersistenceConstants.DRIVER_DB_DERBY);
    }

    @Override
    @Transactional
    public void save(NationStatesRank rank) {

        try {
            entityManager.persist(rank);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<NationStatesRank> findAll() {

        List<NationStatesRank> resultList;

        try {
            TypedQuery<NationStatesRank> all = entityManager
                    .createNamedQuery("NationStatesRank.findAll",
                            NationStatesRank.class);

            resultList = all.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }

        return resultList;
    }

    @Override
    @Transactional
    public List<NationStatesRank> findAllMostRecent() {
        List<NationStatesRank> resultList;

        try {

            TypedQuery<NationStatesRank> all = entityManager
                    .createNamedQuery("NationStatesRank.findLatestRankings",
                            NationStatesRank.class);

            resultList = all.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }

        return resultList;
    }

    @Override
    @Transactional
    public List<NationStatesRank> findAllByName(String nation) {

        List<NationStatesRank> resultList;

        try {
            TypedQuery<NationStatesRank> forNation = entityManager
                    .createNamedQuery("NationStatesRank.findByNationName",
                            NationStatesRank.class)
                    .setParameter("nation", nation);

            resultList = forNation.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }

        return resultList;
    }

    @Override
    @Transactional
    public NationStatesRank findLatestByName(String nation) {
        
        NationStatesRank result;
        
        try {
            TypedQuery<NationStatesRank> forNation = entityManager
                    .createNamedQuery("NationStatesRank"
                            + ".findLatestRankingsByNationName",
                            NationStatesRank.class)
                    .setParameter("nation", nation);

            result = forNation.getSingleResult();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
        
        return result;
    }

}
