package org.n3r.web.controller.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.n3r.core.lang.RDate;
import org.n3r.web.controller.demo.domain.Artist;
import org.n3r.web.controller.demo.domain.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private Object results;

    @RequestMapping("index")
    public String index(Map<String, Object> result) {

        result.put("today", RDate.toDateStr());

        User user = new User("lv", "yong", "test");
        result.put("user", user);

        return "demo/index";
    }

    @RequestMapping(value = "html/{fileName}", method = RequestMethod.GET)
    public String example7(Model model, @PathVariable String fileName)
    {
        return "demo/" + fileName;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String doSearch(@ModelAttribute("search") Search search, Model model)
    {
        model.addAttribute("styleList", this.getStyleList());
        model.addAttribute("countryList", this.getCountryList());
        model.addAttribute("search", search);
        model.addAttribute("results", this.getSearchResults());
        return "demo/example3";
    }

    @RequestMapping(value = "/example3", method = RequestMethod.GET)
    public String setupForm(Model model)
    {
        model.addAttribute("styleList", this.getStyleList());
        model.addAttribute("countryList", this.getCountryList());
        model.addAttribute("search", new Search());
        return "demo/example3";
    }

    @RequestMapping(value = "/example4", method = RequestMethod.GET)
    public String example5(Model model) {
        model.addAttribute("listArtits", this.getResults());
        return "demo/example4";
    }

    private List<String> getStyleList()
    {
        return Arrays.asList("Rock", "Pop", "Metal", "House", "RnB", "Dance", "Country", "Techno", "Classical");
    }

    private List<String> getCountryList()
    {
        return Arrays.asList("United States", "United Kingdom", "China", "Japan", "France", "Spain", "Germany",
                "Canada", "Australia");
    }

    public ArrayList<User> getResults() {
        ArrayList<User> users = Lists.newArrayList();
        users.add(new User("lv", "yong", "n3", Lists.newArrayList(new User("c", "c1", "")), false));
        users.add(new User("zhang", "san", "cuc", Lists.newArrayList(new User("c2", "c2", "")), true));
        users.add(new User("li", "si", "cu", Lists.newArrayList(new User("c3", "c3", "")), true));
        return users;
    }

    private List<Artist> getSearchResults()
    {
        List<Artist> results = new ArrayList<Artist>();

        results.add(new Artist(
                "Mariah",
                "Carrey",
                Arrays.asList("Mariah Carey", "Emotions", "Music Box", "Merry Christmas", "DayDream", "ButterFly"),
                "Mariah Carey (born March 27, 1970) is an American singer, songwriter, record producer, and actress. She made her recording debut in 1990 under the guidance of Columbia Records executive Tommy Mottola, and released her self-titled debut studio album, Mariah Carey.",
                true));

        results.add(new Artist(
                "Elvis",
                "Presley",
                Arrays.asList("Elvis Presley", "Elvis", "Loving You", "Elvis' Christmas Album", "Elvis Is Back!",
                        "G.I. Blues"),
                "Elvis Aaron Presleya (January 8, 1935 – August 16, 1977) was one of the most popular American singers of the 20th century. A cultural icon, he is widely known by the single name Elvis. He is often referred to as the 'King of Rock and Roll' or simply 'the King'.",
                false));

        results.add(new Artist(
                "John",
                "Lennon",
                Arrays.asList("John Lennon", "Imagine", "Some Time in New York City", "Mind Games",
                        "Walls and Bridges", "Rock 'n' Roll"),
                "John Winston Lennon (9 October 1940 – 8 December 1980) was an English musician and singer-songwriter who rose to worldwide fame as one of the founding members of The Beatles, one of the most commercially successful and critically acclaimed acts in the history of popular music. Along with fellow Beatle Paul McCartney, he formed one of the most successful songwriting partnerships of the 20th century.",
                false));
        return results;
    }
}
