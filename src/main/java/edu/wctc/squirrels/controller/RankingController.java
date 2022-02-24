package edu.wctc.squirrels.controller;

import edu.wctc.squirrels.entity.Sighting;
import edu.wctc.squirrels.entity.Squirrel;
import edu.wctc.squirrels.repo.SquirrelRepository;
import edu.wctc.squirrels.service.LocationService;
import edu.wctc.squirrels.service.SightingService;
import edu.wctc.squirrels.service.SquirrelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RankingController {
    private SquirrelService squirrelService;
    private SightingService sightingService;
    private LocationService locationService;

    @Autowired
    public RankingController(SquirrelService sqs, SightingService sis, LocationService los) {
        this.squirrelService = sqs;
        this.sightingService = sis;
        this.locationService = los;
    }

    @PostMapping("/rank")
    public String processRanking(Model model,
                                  @Valid @ModelAttribute Squirrel squirell) {
        int squirrelId = squirell.getId();
        Squirrel sq = squirrelService.getSquirrel(squirrelId);
        sq.setRanking(squirell.getRanking());

        squirrelService.saveRanking(sq);

        model.addAttribute("pageTitle", "Thank You!");
        model.addAttribute("squirrel", squirrelService.getSquirrel(squirrelId));
        model.addAttribute("rankings", "rankings");

        return "rating-confirmation";
    }

    @RequestMapping("/rankings")
    public String showRankings(Model model,
                               @RequestParam("id") int squirrelId) {
        model.addAttribute("pageTitle", "Rankings");
        model.addAttribute("squirrel", squirrelService.getSquirrel(squirrelId));
        return "rankings";
    }

    @RequestMapping("/ranking-form")
    public String showRankingsForm(Model model,
                               @RequestParam("id") int squirrelId) {
        model.addAttribute("pageTitle", "Rankings");
        model.addAttribute("squirrel", squirrelService.getSquirrel(squirrelId));
        return "ranking-form";
    }
}
