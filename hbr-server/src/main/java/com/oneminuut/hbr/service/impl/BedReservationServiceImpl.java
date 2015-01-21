package com.oneminuut.hbr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneminuut.hbr.dao.DepartmentDao;
import com.oneminuut.hbr.dao.HospitalDao;
import com.oneminuut.hbr.dao.UserDao;
import com.oneminuut.hbr.service.BedReservationService;

@Service("bedReservationService")
public class BedReservationServiceImpl implements BedReservationService {

	@Autowired
	private DepartmentDao sectionDao;

	@Autowired
	private HospitalDao lessonDao;

	@Autowired
	private UserDao userDao;

	public HospitalDao getLessonDao() {
		return lessonDao;
	}

	public void setLessonDao(HospitalDao lessonDao) {
		this.lessonDao = lessonDao;
	}

	public DepartmentDao getSectionDao() {
		return sectionDao;
	}

	public void setSectionDao(DepartmentDao sectionDao) {
		this.sectionDao = sectionDao;
	}

/*	public List<Section> getAllSectionsForLesson(Lesson lesson) {
		return sectionDao.getAllSectionsForLesson(lesson);
	}

	public Section saveSection(Section section) {
		return sectionDao.save(section);
	}

	public Section getSection(Long id) {
		return sectionDao.get(id);
	}

	public List<SlideNotes> getSlideNotesForSection(long section_id) {
		return sectionDao.getSlideNotesForSection(section_id);
	}*/
}
