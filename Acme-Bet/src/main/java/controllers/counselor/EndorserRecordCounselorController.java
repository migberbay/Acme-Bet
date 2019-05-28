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
import services.EndorserRecordService;

import controllers.AbstractController;
import domain.Counselor;
import domain.Curricula;
import domain.EndorserRecord;

@Controller
@RequestMapping("/curricula/endorserRecord")
public class EndorserRecordCounselorController extends AbstractController {

	@Autowired
	private EndorserRecordService endorserRecordService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private CounselorService counselorService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		Counselor counselor = this.counselorService.findByPrincipal();
		endorserRecord = this.endorserRecordService
				.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);

		Curricula curricula = this.curriculaService.findByCounselor(counselor);

		if (curricula.getCounselor().equals(counselor)) {
			result = this.createEditModelAndView(endorserRecord);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord,
			BindingResult binding) {
		ModelAndView result;
		Counselor logged = this.counselorService.findByPrincipal();
		Curricula curricula = this.curriculaService.findByCounselor(logged);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(endorserRecord);
		} else {
			try {
				this.endorserRecordService.save(endorserRecord, curricula);
				result = new ModelAndView(
						"redirect:/curricula/counselor/show.do?counselorId="
								+ logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "curricula/counselor/show.do");
				result.addObject("isOwner",
						curricula.getCounselor().equals(logged));
				result.addObject("counselorId", logged.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord,
						"educationRecord.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer endorserRecordId) {
		ModelAndView result;
		final Counselor logged = this.counselorService.findByPrincipal();
		final Curricula curricula = this.curriculaService
				.findByCounselor(logged);
		final EndorserRecord endorserRecord = this.endorserRecordService
				.findOne(endorserRecordId);

		if (curricula.getCounselor().equals(logged)) {
			try {
				this.endorserRecordService.delete(endorserRecord);
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
	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curricula/endorserRecord/create");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", messageCode);
		result.addObject("endorserRecordId", endorserRecord.getId());

		return result;
	}

}
