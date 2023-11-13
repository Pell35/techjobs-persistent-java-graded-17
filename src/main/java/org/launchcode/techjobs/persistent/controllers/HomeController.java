package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("employer", employerRepository.findAll());
        model.addAttribute("skill", skillRepository.findAll());
        model.addAttribute("job",jobRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	model.addAttribute("employer", employerRepository.findAll());
    model.addAttribute("skill", skillRepository.findAll());
        model.addAttribute("job",new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
        Employer employer = employerRepository.findById(employerId).orElse(null);
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);

        if (errors.hasErrors()) {
	    model.addAttribute("job", "Add Job");
            model.addAttribute("employer", employerRepository.findAll());
            model.addAttribute("skill", skillRepository.findAll());
            return "add";
        }
        newJob.setSkills(skillObjs);
        newJob.setEmployer(employer);
        jobRepository.save(newJob);
        skillRepository.saveAll(skillObjs);
        employerRepository.save(employer);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
            model.addAttribute("job", jobRepository.findById(jobId));
            return "view";
    }

}
