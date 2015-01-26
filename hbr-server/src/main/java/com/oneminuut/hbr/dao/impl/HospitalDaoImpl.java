package com.oneminuut.hbr.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.HospitalDao;
import com.oneminuut.hbr.dao.domain.Hospital;
import com.oneminuut.hbr.util.HBRUtil;

@Repository("hospitalDao")
public class HospitalDaoImpl extends GenericDaoImpl<Hospital, Long> implements
		HospitalDao {

	private static final Logger logger = Logger
			.getLogger(HospitalDaoImpl.class);

	public HospitalDaoImpl() {
		super(Hospital.class);
	}

	public Hospital getHospital(long id) {

		Session session = null;
		Hospital hospital = null;
		try {

			session = getSessionFactory().openSession();
			String queryString = "from Hospital h JOIN FETCH h.departments d JOIN FETCH d.units u JOIN FETCH u.beds b"
					+ " where h.id = :id and h.deleted=:deleted and d.deleted=:deleted and u.deleted=:deleted and b.deleted=:deleted";
			/*
			 * String queryString =
			 * "from Hospital h JOIN FETCH h.departments d JOIN FETCH d.units u"
			 * +
			 * " JOIN FETCH u.beds b where h.id = :id and h.deleted=:deleted and d.deleted=:deleted "
			 * +
			 * "and u.deleted=:deleted and b.deleted=:deleted and b.available=:available"
			 * ;
			 */
			Query query = session.createQuery(queryString);
			query.setLong("id", id);
			query.setBoolean("deleted", false);
			// query.setBoolean("available", true);
			hospital = (Hospital) query.uniqueResult();
		} catch (Exception e) {
			logger.fatal(HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.close();
		}
		return hospital;

	}

}
