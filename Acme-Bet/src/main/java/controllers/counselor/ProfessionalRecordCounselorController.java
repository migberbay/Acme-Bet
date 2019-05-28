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
import services.ProfessionalRecordService;

import controllers.AbstractController;
import domain.Counselor;
import domain.Curricula;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/curricula/professionalRecord")
public class ProfessionalRecordCounselorController extends AbstractController {

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private CounselorService counselorService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.create();
		result = this.createEditModelAndView(professionalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		Counselor counselor = this.counselorService.findByPrincipal();
		professionalRecord = this.professionalRecordService
				.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);

		Curricula curricula = this.curriculaService.findByCounselor(counselor);

		if (curricula.getCounselor().equals(counselor)) {
			result = this.createEditModelAndView(professionalRecord);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord,
			BindingResult binding) {
		ModelAndView result;
		Counselor logged = this.counselorService.findByPrincipal();
		Curricula curricula = this.curriculaService.findByCounselor(logged);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(professionalRecord);
		} else {
			try {
				this.professionalRecordService.save(professionalRecord, curricula);
				result = new ModelAndView(
						"redirect:/curricula/counselor/show.do?counselorId="
								+ logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "history/brotherhood/show.do");
				result.addObject("isOwner",
						curricula.getCounselor().equals(logged));
				result.addObject("counselorId", logged.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord,
						"educationRecord.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer professionalRecordId) {
		ModelAndView result;
		final Counselor logged = this.counselorService.findByPrincipal();
		final Curricula curricula = this.curriculaService
				.findByCounselor(logged);
		final ProfessionalRecord professionalRecord = this.professionalRecordService
				.findOne(professionalRecordId);

		if (curricula.getCounselor().equals(logged)) {
			try {
				this.professionalRecordService.delete(professionalRecord);
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
	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curricula/professionalRecord/create");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", messageCode);
		result.addObject("professionalRecordId", professionalRecord.getId());

		return result;
	}

}
