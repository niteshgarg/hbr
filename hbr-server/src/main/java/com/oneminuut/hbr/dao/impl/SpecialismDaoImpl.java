package com.oneminuut.hbr.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.SpecialismDao;
import com.oneminuut.hbr.dao.domain.Specialism;
import com.oneminuut.hbr.util.HBRUtil;

@Repository("specialismDao")
public class SpecialismDaoImpl extends GenericDaoImpl<Specialism, Long>
		implements SpecialismDao {

	private static final Logger logger = Logger
			.getLogger(SpecialismDaoImpl.class);

	public SpecialismDaoImpl() {
		super(Specialism.class);
	}

	public List<Specialism> getSpecialismForHospital(long id) {

		Session session = null;
		List<Specialism> specialisms = null;
		try {

			session = getSessionFactory().openSession();
			String queryString = "from Specialism b "
					+ " where b.hospital.id = :id and b.deleted=:deleted";

			Query query = session.createQuery(queryString);
			query.setLong("id", id);
			query.setBoolean("deleted", false);
			// query.setBoolean("available", true);
			specialisms = query.list();
		} catch (Exception e) {
			logger.fatal(HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.close();
		}
		return specialisms;

	}

}
