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
import services.PersonalRecordService;

import controllers.AbstractController;
import domain.Counselor;
import domain.Curricula;
import domain.PersonalRecord;

@Controller
@RequestMapping("/curricula/personalRecord")
public class PersonalRecordCounselorController extends AbstractController {
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PersonalRecord personalRecord;
		
		personalRecord = this.personalRecordService.create();
		result = this.createEditModelAndView(personalRecord);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam Integer personalRecordId) {
		ModelAndView result;
		PersonalRecord personalRecord;
		
		Counselor counselor = this.counselorService.findByPrincipal();
		personalRecord = this.personalRecordService.findOne(personalRecordId);
		Assert.notNull(personalRecord);
		
		Curricula curricula = this.curriculaService.findByPersonalRecord(personalRecord);
		
		if(curricula.getCounselor().equals(counselor)) {
			result = this.createEditModelAndView(personalRecord);
		} else {
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
	
	
	public ModelAndView save(@Valid final PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;
		Counselor logged = this.counselorService.findByPrincipal();
		
		if(binding.hasErrors()) {
			result = this.createEditModelAndView(personalRecord);
		} else {
			try {
				this.personalRecordService.save(personalRecord);
				Curricula curricula = this.curriculaService.findByCounselor(logged);
				result = new ModelAndView("redirect:/curricula/counselor/show.do?counselorId=" + logged.getId());
				result.addObject("curricula", curricula);
				result.addObject("requestURI", "curricula/counselor/show.do");
				result.addObject("isOwner", curricula.getCounselor().equals(logged));
				result.addObject("counselorId", logged.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(personalRecord, "personalrecord.commit.error");
			}
		}
		
		return result;
	}
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord) {
		ModelAndView result;
		
		result = this.createEditModelAndView(personalRecord, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord, String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("curricula/personalRecord/create");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);
		result.addObject("personalRecordId", personalRecord.getId());
		
		return result;
	}

}
