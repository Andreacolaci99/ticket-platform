package id.milestone.milestone4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.milestone.milestone4.model.Note;
import id.milestone.milestone4.repository.NoteRepository;
import id.milestone.milestone4.repository.TicketRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/create/{ticketId}")
    public String nuovaNota(@PathVariable Integer ticketId, Model model) {
        Note nota = new Note();
        nota.setTicket(ticketRepository.findById(ticketId).get());

        model.addAttribute("note", nota);
        model.addAttribute("editMode", false);
        return "/admin/categorie/note";
    }

    @PostMapping("/create")
    public String creaNota(@Valid @ModelAttribute("note") Note formNote, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            return "/admin/categorie/note";
        }

        noteRepository.save(formNote);

        return "redirect:/idraulica/edit/" + formNote.getTicket().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Note note = noteRepository.findById(id).get();
        model.addAttribute("note", note);
        model.addAttribute("editMode", true);

        return "/editTask/";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", true);
            model.addAttribute("note", note);
            return "pizzeria/offerte/edit";
        }
        noteRepository.save(note);
        return "redirect:/dettaglioTicket/" + note.getTicket().getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteNota(@PathVariable("id") Integer id) {
        Note note = noteRepository.findById(id).get();
        Integer ticketId = note.getTicket().getId();

        noteRepository.deleteById(id);
        return "redirect:/dettaglioTicket/" + ticketId;
    }
}
