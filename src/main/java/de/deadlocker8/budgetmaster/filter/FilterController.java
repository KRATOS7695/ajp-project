package de.deadlocker8.budgetmaster.filter;

import de.deadlocker8.budgetmaster.controller.BaseController;
import de.deadlocker8.budgetmaster.utils.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
@RequestMapping(Mappings.FILTER)
public class FilterController extends BaseController
{
	private final FilterHelpersService filterHelpers;

	@Autowired
	public FilterController(FilterHelpersService filterHelpers)
	{
		this.filterHelpers = filterHelpers;
	}

	@PostMapping(value = "/apply")
	public String post(WebRequest request, @ModelAttribute("NewFilterConfiguration") FilterConfiguration filterConfiguration)
	{
		request.setAttribute("filterConfiguration", filterConfiguration, WebRequest.SCOPE_SESSION);
		return "redirect:" + request.getHeader("Referer");
	}

	@GetMapping("/reset")
	public String reset(WebRequest request)
	{
		FilterConfiguration filterConfiguration = FilterConfiguration.DEFAULT;
		filterConfiguration.setFilterCategories(filterHelpers.getFilterCategories());
		filterConfiguration.setFilterTags(filterHelpers.getFilterTags());
		request.setAttribute("filterConfiguration", filterConfiguration, WebRequest.SCOPE_SESSION);
		return "redirect:" + request.getHeader("Referer");
	}
}