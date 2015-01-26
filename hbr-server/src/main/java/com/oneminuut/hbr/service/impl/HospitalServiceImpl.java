package com.oneminuut.hbr.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneminuut.hbr.dao.BedDao;
import com.oneminuut.hbr.dao.BedReservationDao;
import com.oneminuut.hbr.dao.HospitalDao;
import com.oneminuut.hbr.dao.UserDao;
import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.dao.domain.BedReservation;
import com.oneminuut.hbr.dao.domain.Department;
import com.oneminuut.hbr.dao.domain.Hospital;
import com.oneminuut.hbr.dao.domain.Unit;
import com.oneminuut.hbr.dto.BedDTO;
import com.oneminuut.hbr.dto.DepartmentDTO;
import com.oneminuut.hbr.dto.HospitalDTO;
import com.oneminuut.hbr.dto.UnitDTO;
import com.oneminuut.hbr.service.HospitalService;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private UserDao userDao;

	public List<Hospital> getAllHospitals() {
		return hospitalDao.getAll();
	}

	public HospitalDTO getHospital(long id) {

		if (null == CacheManager.getInstance().getCache("hospitalCache")) {
			Cache hospitalCache = new Cache("hospitalCache", 1,
					MemoryStoreEvictionPolicy.LFU, false, null, true, 1800,
					500, false, 0, null);

			CacheManager.getInstance().addCache(hospitalCache); // creates a
			// cache
			// called xyz.
		}

		if (null == CacheManager.getInstance().getCache("hospitalCache")
				.get("hospitals")) {
			Cache hospitalCache = CacheManager.getInstance().getCache(
					"hospitalCache");
			hospitalCache.put(new Element("hospitals",
					new HashMap<Long, HospitalDTO>()));
		}

		Cache hospitalCache = CacheManager.getInstance().getCache(
				"hospitalCache");
		Element e = hospitalCache.get("hospitals");

		Map<Long, HospitalDTO> hospitalsHashMap = (Map<Long, HospitalDTO>) e
				.getObjectValue();

		if (null == hospitalsHashMap.get(id)) {

			Hospital hospital = hospitalDao.getHospital(id);
			HospitalDTO hospitalDTO = new HospitalDTO();
			hospitalDTO.setDepartments(new HashSet<DepartmentDTO>());
			hospitalDTO.setId(hospital.getId());
			hospitalDTO.setName(hospital.getName());
			for (Department department : hospital.getDepartments()) {
				DepartmentDTO departmentDTO = new DepartmentDTO();
				hospitalDTO.getDepartments().add(departmentDTO);
				departmentDTO.setId(department.getId());
				departmentDTO.setName(department.getName());
				departmentDTO.setUnits(new HashSet<UnitDTO>());
				for (Unit unit : department.getUnits()) {
					UnitDTO unitDTO = new UnitDTO();
					departmentDTO.getUnits().add(unitDTO);
					unitDTO.setId(unit.getId());
					unitDTO.setName(unit.getName());
					unitDTO.setBeds(new HashSet<BedDTO>());
					for (Bed bed : unit.getBeds()) {
						BedDTO bedDTO = new BedDTO();
						bedDTO.setId(bed.getId());
						bedDTO.setNumber(bed.getNumber());
						unitDTO.getBeds().add(bedDTO);
					}
				}
			}
			hospitalsHashMap.put(new Long(id), hospitalDTO);
		}

		return hospitalsHashMap.get(id);

	}

	@Autowired
	private BedDao bedDao;

	public List<Bed> getBedsForUnit(long id) {
		return bedDao.getBedsForUnit(id);
	}

	public List<BedReservation> getReservationsForBed(long id, Date date) {
		return bedDao.getReservationsForBed(id, date);
	}

	@Autowired
	private BedReservationDao bedReservationDao;

	public BedReservation getBedReservationForDate(long id, Date enddate, Date startDate) {
		return bedReservationDao.getBedReservationForDate(id, enddate, startDate);
	}

	
	public void saveBedReservation(BedReservation bedReservation) {
		bedReservationDao.save(bedReservation);
	}
	
	public Bed getBed(long bedId) {
		return bedDao.get(bedId);
	}
	/*
	 * @Override public Lesson saveLesson(AddLessonForm addLessonForm, String
	 * email) { Lesson lesson = new Lesson();
	 * lesson.setUser(userDao.getUserByEmail(email)); lesson.setCreated(new
	 * Date()); lesson.setUpdated(new Date());
	 * lesson.setLessonDescription(addLessonForm.getLessonDescription());
	 * lesson.setLessonName(addLessonForm.getLessonName()); return
	 * lessonDao.save(lesson); }
	 * 
	 * @Override public List<Lesson> getAllLessons(User user) { return
	 * lessonDao.getAllLessons(user); }
	 * 
	 * public Lesson saveLesson(Lesson lesson) { return lessonDao.save(lesson);
	 * }
	 * 
	 * public HospitalDao getLessonDao() { return lessonDao; }
	 * 
	 * public void setLessonDao(HospitalDao lessonDao) { this.lessonDao =
	 * lessonDao; }
	 * 
	 * public UserDao getUserDao() { return userDao; }
	 * 
	 * public void setUserDao(UserDao userDao) { this.userDao = userDao; }
	 * 
	 * public Lesson getLesson(long id) { return lessonDao.get(id); }
	 */
}
