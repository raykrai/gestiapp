package com.salesianostriana.dam.gestiapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.gestiapp.formbeans.ReserveFormBean;
import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.model.RoomCategory;
import com.salesianostriana.dam.gestiapp.model.TimeZone;
import com.salesianostriana.dam.gestiapp.service.AppUserService;
import com.salesianostriana.dam.gestiapp.service.ReserveCheckerService;
import com.salesianostriana.dam.gestiapp.service.ReserveService;
import com.salesianostriana.dam.gestiapp.service.RoomCategoryService;
import com.salesianostriana.dam.gestiapp.service.TimeZoneService;

@Controller
public class ReserveController {

	@Autowired
	private ReserveService reserveService;
	@Autowired
	private ReserveCheckerService reserveCheckerService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private TimeZoneService timeZoneService;
	@Autowired
	private RoomCategoryService roomCategoryService;

	// Cambiar la ruta del mapping con la plantilla del formulario en cuestión , LA
	// QUE ESTÁ PUESTA ES DE EJEMPLO - 22/09/2019.

	@GetMapping("/formReserve")
	public String showFormReserve(Model model) {
		List<TimeZone> timeZoneList = timeZoneService.findAll();
		List<RoomCategory> categories = roomCategoryService.findAll();

		model.addAttribute("timeZoneList", timeZoneList);
		model.addAttribute("categories", categories);
		model.addAttribute("reserveFormBean", new ReserveFormBean());

		return "formReserve";

	}

	// Cambiar la ruta del mapping con la plantilla del formulario en cuestión , LA
	// QUE ESTÁ PUESTA ES DE EJEMPLO - 22/09/2019.

	@PostMapping("/formReserve/submit")
	public String submitFormReserve(@ModelAttribute("reserveFormBean") ReserveFormBean reserveFormBean, Model model) {

		model.addAttribute("rooms", reserveService.findByReserve(reserveFormBean.getDate(),
				reserveFormBean.getTimeZone().getTime(), reserveFormBean.getRoomCategory()));
		model.addAttribute("reserveFormBean", reserveFormBean);

		if (reserveService.findByReserve(reserveFormBean.getDate(), reserveFormBean.getTimeZone().getTime(),
				reserveFormBean.getRoomCategory()).isEmpty()) {
			return "reserveComplet";
		} else {
			return "reserve";
		}

	}

	@PostMapping("/formReserve/save")
	public String submitFormReserve2(@ModelAttribute("reserveFormBean") ReserveFormBean reserveFormBean) {
		
		String plantilla = "redirect:/myreserves";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();

		Reserve r = new Reserve();

		r.setReservedRoom(reserveFormBean.getReservedRoom());
		r.setReserveUser(appUserService.findById(appUserService.searchByEmail(user.getUsername()).getId()));
		r.setDate(reserveFormBean.getDate());
		r.setTimeZone(reserveFormBean.getTimeZone());

		if (reserveCheckerService.checkReserve(r.getDate(), r.getTimeZone(), r.getReservedRoom())) {
			reserveService.save(r);
		} else {
			plantilla = "reservedDay";
		}
		return plantilla;
	}

}
