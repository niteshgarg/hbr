package com.oneminuut.hbr.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.BedDao;
import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.util.HBRUtil;

@Repository("bedDao")
public class BedDaoImpl extends GenericDaoImpl<Bed, Long> implements
		BedDao {

	private static final Logger logger = Logger
			.getLogger(BedDaoImpl.class);

	public BedDaoImpl() {
		super(Bed.class);
	}

	public List<Bed> getBedsForUnit(long id) {

		Session session = null;
		List<Bed> beds = null;
		try {

			session = getSessionFactory().openSession();
			String queryString = "from Bed b LEFT JOIN FETCH b.reservations br "
					+ " where b.unit.id = :id and b.deleted=:deleted";

			Query query = session.createQuery(queryString);
			query.setLong("id", id);
			query.setBoolean("deleted", false);
			// query.setBoolean("available", true);
			beds = query.list();
		} catch (Exception e) {
			logger.fatal(HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.close();
		}
		return beds;

	}
	
	
}
