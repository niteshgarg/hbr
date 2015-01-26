package com.oneminuut.hbr.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.BedReservationDao;
import com.oneminuut.hbr.dao.domain.BedReservation;
import com.oneminuut.hbr.util.HBRUtil;

@Repository("bedReservationDao")
public class BedReservationDaoImpl extends GenericDaoImpl<BedReservation, Long>
		implements BedReservationDao {

	private static final Logger logger = Logger
			.getLogger(BedReservationDaoImpl.class);

	public BedReservationDaoImpl() {
		super(BedReservation.class);
	}

	public BedReservation getBedReservationForDate(long bedId, Date endDate,
			Date startDate) {
		Session session = null;
		BedReservation bedReservation = null;
		try {

			session = getSessionFactory().openSession();

			String queryString = "from BedReservation br "
					+ " where br.bed.id = :id and br.deleted = :deleted and (br.startDate = :date or br.endDate = :date or (br.startDate < :date and br.endDate > :date)"
					+ " or br.startDate = :startdate or br.endDate = :startdate or (br.startDate < :startdate and br.endDate > :startdate)) ";

			Query query = session.createQuery(queryString);
			query.setLong("id", bedId);
			query.setDate("date", endDate);
			query.setDate("startdate", startDate);
			query.setBoolean("deleted", false);
			bedReservation = (BedReservation) query.uniqueResult();
		} catch (Exception e) {
			logger.fatal(HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.close();
		}
		return bedReservation;
	}

}
