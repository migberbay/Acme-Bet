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
import services.MiscellaneousRecordService;

import controllers.AbstractController;
import domain.Counselor;
import domain.Curricula;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/curricula/miscellaneousRecord")
public class MiscellaneousRecordCounselorController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private CounselorService counselorService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		Counselor counselor = this.counselorService.findByPrincipal();
		miscellaneousRecord = this.miscellaneousRecordService
				.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);

		Curricula curricula = this.curriculaService.findByCounselor(counselor);

		if (curricula.getCounselor().equals(counselor)) {
			result = this.createEditModelAndView(miscellaneousRecord);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord,
			BindingResult binding) {
		ModelAndView result;
		Counselor logged = this.counselorService.findByPrincipal();
		Curricula curricula = this.curriculaService.findByCounselor(logged);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(miscellaneousRecord);
		} else {
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord, curricula);
				result = new ModelAndView(
						"redirect:/curricula/counselor/show.do?counselorId="
								+ logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "history/brotherhood/show.do");
				result.addObject("isOwner",
						curricula.getCounselor().equals(logged));
				result.addObject("counselorId", logged.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord,
						"educationRecord.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer miscellaneousRecordId) {
		ModelAndView result;
		final Counselor logged = this.counselorService.findByPrincipal();
		final Curricula curricula = this.curriculaService
				.findByCounselor(logged);
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService
				.findOne(miscellaneousRecordId);

		if (curricula.getCounselor().equals(logged)) {
			try {
				curricula.getMiscellaneousRecords().remove(miscellaneousRecord);
				this.miscellaneousRecordService.delete(miscellaneousRecord);
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
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curricula/miscellaneousRecord/create");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", messageCode);
		result.addObject("miscellaneousRecordId", miscellaneousRecord.getId());

		return result;
	}

}
