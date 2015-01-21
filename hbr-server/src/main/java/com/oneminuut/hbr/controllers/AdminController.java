package com.oneminuut.hbr.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oneminuut.hbr.controller.form.SecurityLiveStreamForm;
import com.oneminuut.hbr.dao.domain.Hospital;
import com.oneminuut.hbr.service.BedReservationService;
import com.oneminuut.hbr.service.HospitalService;
import com.oneminuut.hbr.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private BedReservationService sectionService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String prepareHome(ModelMap map, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (null == (String) session.getAttribute("email")) {
			return "redirect:/login.htm";
		}

		SecurityLiveStreamForm liveStreamForm = new SecurityLiveStreamForm();
		map.addAttribute("securityLiveStreamForm", liveStreamForm);

		List<Hospital> list = hospitalService.getAllHospitals();
		session.setAttribute("hospitalList", list);

		return "home";
	}

	/*@RequestMapping(value = "/showAddLessonForm", method = RequestMethod.GET)
	public ModelAndView showAddLessonForm(ModelMap map,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("addLesson");

		AddLessonForm form = new AddLessonForm();
		modelAndView.addObject("addLessonForm", form);

		return modelAndView;
	}

	@RequestMapping(value = "/showAddQuestionForm", method = RequestMethod.GET)
	public ModelAndView showAddQuestionForm(ModelMap map,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("addQuestion");

		AddQuestionForm form = new AddQuestionForm();
		modelAndView.addObject("addQuestionForm", form);

		return modelAndView;
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
	public String addQuestion(
			final RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("addQuestionForm") AddQuestionForm addQuestionForm,
			BindingResult result, ModelMap map, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (result.hasErrors()) {
			return "addQuestion";
		}
		addQuestionForm.setSectionId((Long) session.getAttribute("sectionId"));
		questionService.saveQuestion(addQuestionForm);

		return "redirect:/questions.htm?sectionId="
				+ addQuestionForm.getSectionId();

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HospitalService getLessonService() {
		return lessonService;
	}

	public void setLessonService(HospitalService lessonService) {
		this.lessonService = lessonService;
	}

	@RequestMapping(value = "/addLesson", method = RequestMethod.POST)
	public String addLesson(
			final RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("addLessonForm") AddLessonForm addLessonForm,
			BindingResult result, ModelMap map, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (result.hasErrors()) {
			return "addLesson";
		}

		lessonService.saveLesson(addLessonForm,
				((String) session.getAttribute("email")));

		return "redirect:/home.htm";

	}

	@RequestMapping(value = "/showAddSectionForm", method = RequestMethod.GET)
	public ModelAndView showAddSectionForm(ModelMap map,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("addSection");
		HttpSession session = request.getSession();

		AddSectionForm form = new AddSectionForm();
		form.setLessonId((Long) session.getAttribute("lessonId"));
		modelAndView.addObject("addSectionForm", form);

		return modelAndView;
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public ModelAndView questions(@RequestParam Long sectionId,
			HttpServletRequest request) {
		HttpSession session = request.getSession();

		ModelAndView modelAndView = new ModelAndView("questions");

		List<Unit> questions = questionService
				.getQuestionsForSection(sectionId);

		session.setAttribute("questionList", questions);
		session.setAttribute("sectionId", sectionId);

		return modelAndView;
	}

	@RequestMapping(value = "/lessonDetails", method = RequestMethod.GET)
	public ModelAndView lessonDetails(@RequestParam Long lessonId,
			HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (null == lessonId) {
			lessonId = (Long) session.getAttribute("lessonId");
		}
		ModelAndView modelAndView = new ModelAndView("lessonDetails");

		Lesson lesson = lessonService.getLesson(lessonId);

		LessonDetailsForm lessonDetailsForm = new LessonDetailsForm();
		lessonDetailsForm.setId(lessonId);
		lessonDetailsForm.setLessonDescription(lesson.getLessonDescription());
		lessonDetailsForm.setLessonName(lesson.getLessonName());

		modelAndView.addObject("lessonDetailsForm", lessonDetailsForm);

		List<Section> sections = sectionService.getAllSectionsForLesson(lesson);

		session.setAttribute("sectionList", sections);
		session.setAttribute("lessonId", lessonId);

		return modelAndView;
	}

	@RequestMapping(value = "/deleteLesson", method = RequestMethod.GET)
	public String deteleLesson(@RequestParam Long lessonId,
			HttpServletRequest request) {
		Lesson lesson = lessonService.getLesson(lessonId);

		lesson.setDeleted(true);
		lessonService.saveLesson(lesson);

		return "redirect:/home.htm";
	}

	@RequestMapping(value = "/deleteSection", method = RequestMethod.GET)
	public String deleteSection(@RequestParam Long sectionId,
			HttpServletRequest request) {
		Section section = sectionService.getSection(sectionId);
		HttpSession session = request.getSession();

		section.setDeleted(true);
		//sectionService.saveSection(section);

		return "redirect:/lessonDetails.htm?lessonId="
				+ (Long) session.getAttribute("lessonId");
	}

	@RequestMapping(value = "/addSection", method = RequestMethod.POST)
	public String addSection(
			final RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("addSectionForm") AddSectionForm addSectionForm,
			BindingResult result, ModelMap map, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (result.hasErrors()) {
			return "addSection";
		}

		if (addSectionForm.getSectionType() == -1) {
			result.rejectValue("sectionType", "", "Please select a Section.");
			return "addSection";
		} else if (SectionTypeEnum.get(addSectionForm.getSectionType()).equals(
				SectionTypeEnum.POWERPOINT)) {
			if (null == addSectionForm.getSectionDetails()
					.getOriginalFilename()) {
				result.rejectValue("sectionDetails", "",
						"Please provide pptx/ppt file for upload.");
				return "addSection";
			} else if ("".equalsIgnoreCase(addSectionForm.getSectionDetails()
					.getOriginalFilename())) {
				result.rejectValue("sectionDetails", "",
						"Please provide pptx/ppt file for upload.");
				return "addSection";
			} else if (!addSectionForm.getSectionDetails()
					.getOriginalFilename().contains(".pptx")
					&& !addSectionForm.getSectionDetails()
							.getOriginalFilename().contains(".ppt")) {
				result.rejectValue("sectionDetails", "",
						"Please provide pptx/ppt file for upload.");
				return "addSection";
			}

		} else if (SectionTypeEnum.get(addSectionForm.getSectionType()).equals(
				SectionTypeEnum.VIDEO)) {
			if (null == addSectionForm.getSectionDetails()
					.getOriginalFilename()) {
				result.rejectValue("sectionDetails", "",
						"Please provide Video for upload.");
				return "addSection";
			} else if ("".equalsIgnoreCase(addSectionForm.getSectionDetails()
					.getOriginalFilename())) {
				result.rejectValue("sectionDetails", "",
						"Please provide Video for upload.");
				return "addSection";
			} else if (!addSectionForm.getSectionDetails()
					.getOriginalFilename().contains(".mp4")) {
				result.rejectValue("sectionDetails", "",
						"Please provide a mp4 Video for upload.");
				return "addSection";
			}

		}
		 * else if (SectionTypeEnum.get(addSectionForm.getSectionType()).equals(
		 * SectionTypeEnum.EDUGRADE) && (null == addSectionForm.getTeacherId()
		 * || "" .equalsIgnoreCase(addSectionForm.getTeacherId()))) {
		 * result.rejectValue("teacherId", "",
		 * "Please provide TeacherId for EduGrade."); return "addSection"; }
		 

		addSectionForm.setLessonId((Long) session.getAttribute("lessonId"));
		//sectionService.saveSection(addSectionForm);

		return "redirect:/lessonDetails.htm?lessonId="
				+ addSectionForm.getLessonId();

	}

	@RequestMapping(value = "/viewSlideShow", method = RequestMethod.GET)
	public String viewSlideShow(@RequestParam Long lessonId, ModelMap map,
			HttpServletRequest request) {
		HttpSession session = request.getSession();

		map.addAttribute("brokerUrl",
				PropertiesFileReaderUtil.getApplicationProperty("broker-url"));

		List<Section> sections = sectionService
				.getAllSectionsForLesson(lessonService.getLesson(lessonId));

		if (sections.size() == 0) {
			return "redirect:/lessonDetails.htm?lessonId="
					+ (Long) session.getAttribute("lessonId");

		}
		Long sectionId = sections.get(0).getId();

		map.addAttribute("topic",
				PropertiesFileReaderUtil.getApplicationProperty("base.topic")
						+ lessonId);
		map.addAttribute("serverUrl",
				PropertiesFileReaderUtil.getPropertyValue("server.url")
						+ "/lessons/" + lessonId + "/" + sectionId);

		return "slideShow";

	}

	public BedReservationService getSectionService() {
		return sectionService;
	}

	public void setSectionService(BedReservationService sectionService) {
		this.sectionService = sectionService;
	}
*/
}
