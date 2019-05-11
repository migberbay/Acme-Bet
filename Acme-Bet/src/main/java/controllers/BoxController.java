
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.BoxService;
import domain.Box;

@Controller
@RequestMapping("box/")
public class BoxController extends AbstractController {

	//Services

	@Autowired
	private BoxService	boxService;


	// Constructors -----------------------------------------------------------

	public BoxController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final List<Box> boxes = new ArrayList<>();

		final UserAccount logged = LoginService.getPrincipal();

		boxes.addAll(this.boxService.findByUserAccountId(logged.getId()));

		res = new ModelAndView("box/list");
		res.addObject("boxes", boxes);

		return res;
	}

	@RequestMapping(value = "/listChildren", method = RequestMethod.GET)
	public ModelAndView listChildren(@RequestParam final int boxId) {
		ModelAndView res;

		final Collection<Box> children = this.boxService.findByParentBox(this.boxService.findOne(boxId));

		res = new ModelAndView("box/listChildren");
		res.addObject("children", children);

		return res;
	}

	//Create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Box box = this.boxService.create(LoginService.getPrincipal());

		res = this.createEditModelAndView(box);
		return res;

	}

	//Edit-------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int boxId) {
		ModelAndView res;
		final Box box = this.boxService.findOne(boxId);

		if (!LoginService.getPrincipal().equals(box.getUserAccount()))
			res = new ModelAndView("error/access");
		else {
			Assert.notNull(box);
			res = this.createEditModelAndView(box);
		}
		return res;
	}

	//Save-------------------------------------------------------------	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Box box, final BindingResult binding) {
		ModelAndView res;

		final Box reboxed = this.boxService.reconstructBox(box);

		if (binding.hasErrors())
			res = this.createEditModelAndView(reboxed);
		else
			try {
				this.boxService.save(reboxed);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(box, "message.commit.error");

				for (int i = 0; i < e.getStackTrace().length; i++)
					System.out.println(e.getStackTrace()[i]);
			}
		return res;
	}

	//Delete-----------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int boxId) {
		ModelAndView res;
		final Box box = this.boxService.findOne(boxId);
		if (!LoginService.getPrincipal().equals(box.getUserAccount()))
			res = new ModelAndView("error/access");
		else
			try {
				this.boxService.delete(box);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(box, "message.commit.error");
			}
		return res;
	}

	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView res;
		res = this.createEditModelAndView(box, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final Box box, final String messageCode) {
		ModelAndView res;

		final UserAccount logged = LoginService.getPrincipal();

		res = new ModelAndView("box/edit");
		res.addObject("box", box);
		res.addObject("boxes", this.boxService.findByUserAccountId(logged.getId()));
		res.addObject("errorMessage", messageCode);

		return res;
	}

}
