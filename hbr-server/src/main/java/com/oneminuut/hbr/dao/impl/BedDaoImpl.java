package com.oneminuut.hbr.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.BedDao;
import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.dao.domain.BedReservation;
import com.oneminuut.hbr.util.HBRUtil;

@Repository("bedDao")
public class BedDaoImpl extends GenericDaoImpl<Bed, Long> implements BedDao {

	private static final Logger logger = Logger.getLogger(BedDaoImpl.class);

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

	public List<BedReservation> getReservationsForBed(long id, Date date) {

		Session session = null;
		List<BedReservation> bedReservations = null;
		try {

			session = getSessionFactory().openSession();
			String queryString = "from BedReservation br "
					+ " where br.bed.id = :id and (br.startDate = :date or br.endDate = :date or br.startDate = :yesterdayDate or br.endDate = :yesterdayDate or ("
					+ "br.startDate < :date and br.endDate > :date)) order by br.endDate asc";

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, -1);
			Date yesterdayDate = c.getTime();

			Query query = session.createQuery(queryString);
			query.setLong("id", id);
			query.setDate("yesterdayDate", yesterdayDate);
			query.setDate("date", date);
			// query.setBoolean("deleted", false);
			// query.setBoolean("available", true);
			bedReservations = query.list();
		} catch (Exception e) {
			logger.fatal(HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.close();
		}
		return bedReservations;

	}

}
