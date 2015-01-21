package com.oneminuut.hbr.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.UserDao;
import com.oneminuut.hbr.dao.domain.User;
import com.oneminuut.hbr.util.HBRUtil;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public void saveUser(User user) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.fatal("User: " + user + " "
					+ HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public User getUserByEmail(String email) {
		Session session = null;
		User user = null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from User where email = :email";
			Query query = session.createQuery(queryString);
			query.setString("email", email);
			user = (User) query.uniqueResult();
		} catch (Exception e) {
			logger.fatal("Email: " + email + " "
					+ HBRUtil.getExceptionDescriptionString(e));
			throw e;
		} finally {
			session.close();
		}
		return user;
	}

}
