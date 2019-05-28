package controllers.counselor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CounselorService;
import services.CurriculaService;
import services.EducationRecordService;

import controllers.AbstractController;
import domain.Counselor;
import domain.Curricula;
import domain.EducationRecord;

@Controller
@RequestMapping("/curricula/educationRecord")
public class EducationRecordCounselorController extends AbstractController {

	@Autowired
	private EducationRecordService educationRecordService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private CounselorService counselorService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;

		Counselor counselor = this.counselorService.findByPrincipal();
		educationRecord = this.educationRecordService
				.findOne(educationRecordId);
		Assert.notNull(educationRecord);

		Curricula curricula = this.curriculaService.findByCounselor(counselor);

		if (curricula.getCounselor().equals(counselor)) {
			result = this.createEditModelAndView(educationRecord);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord,
			BindingResult binding) {
		ModelAndView result;
		Counselor logged = this.counselorService.findByPrincipal();
		Curricula curricula = this.curriculaService.findByCounselor(logged);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(educationRecord);
		} else {
			try {
				this.educationRecordService.save(educationRecord, curricula);
				result = new ModelAndView(
						"redirect:/curricula/counselor/show.do?counselorId="
								+ logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "history/brotherhood/show.do");
				result.addObject("isOwner",
						curricula.getCounselor().equals(logged));
				result.addObject("counselorId", logged.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord,
						"educationRecord.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer educationRecordId) {
		ModelAndView result;
		final Counselor logged = this.counselorService.findByPrincipal();
		final Curricula curricula = this.curriculaService
				.findByCounselor(logged);
		final EducationRecord educationRecord = this.educationRecordService
				.findOne(educationRecordId);

		if (curricula.getCounselor().equals(logged)) {
			try {
				curricula.getEducationRecords().remove(educationRecord);
				this.educationRecordService.delete(educationRecord);
				result = new ModelAndView(
						"redirect:/curricula/counselor/show.do?counselorId="
								+ logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "curricula/counselor/show.do");
				result.addObject("isOwner", curricula.getCounselor()
						.equals(logged));
				result.addObject("counselorId", logged.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView(
						"redirect:/curricula/counselor/show.do?counselorId="
								+ logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "curricula/counselor/show.do");
				result.addObject("isOwner", curricula.getCounselor()
						.equals(logged));
				result.addObject("counselorId", logged.getId());
			}
		} else {
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
	
	//Ancillary methods
	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curricula/educationRecord/create");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", messageCode);
		result.addObject("educationRecordId", educationRecord.getId());

		return result;
	}

}
