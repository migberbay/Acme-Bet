package controllers.counselor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Counselor;
import domain.Curricula;

import services.CounselorService;
import services.CurriculaService;

@Controller
@RequestMapping("/curricula/counselor")
public class CurriculaCounselorController extends AbstractController {

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private CounselorService counselorService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(
			@RequestParam(required = false) final Integer counselorId) {
		final ModelAndView result;
		Counselor counselor;

		if (counselorId != null) {
			counselor = this.counselorService.findOne(counselorId);
		} else {
			counselor = this.counselorService.findByPrincipal();
		}

		Curricula curricula = this.curriculaService.findByCounselor(counselor);
		Boolean hasPersonalRecord = true;

		if (curricula.getPersonalRecord() == null) {
			hasPersonalRecord = false;
		}

		Boolean isOwner = false;

		try {
			Counselor logged = counselorService.findByPrincipal();
			if (logged.getId() == curricula.getCounselor().getId()) {
				isOwner = true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		result = new ModelAndView("curricula/show");
		
		result.addObject("curricula", curricula);
		result.addObject("personalRecord", curricula.getPersonalRecord());
		result.addObject("educationRecords", curricula.getEducationRecords());
		result.addObject("endorserRecords", curricula.getEndorserRecords());
		result.addObject("miscellaneousRecords", curricula.getMiscellaneousRecords());
		result.addObject("professionalRecords", curricula.getProfessionalRecords());
		result.addObject("isOwner", isOwner);
		result.addObject("hasPersonalRecord", hasPersonalRecord);
		
		return result;
		
	}
	

}
