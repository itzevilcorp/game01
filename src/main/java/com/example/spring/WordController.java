package com.example.spring;


import org.springframework.beans.factory.annotation.Autowired;
import com.example.spring.WordRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WordController {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/game")
    public String showForm(Model model) {
        model.addAttribute("levels", new String[]{"Easy", "Medium", "Hard"});
        model.addAttribute("selectedLevel", "");
        return "wordForm";
    }








    @PostMapping("/word")
    public String getWord(@ModelAttribute("selectedLevel") String selectedLevel, Model model, HttpSession session) {
        Word wordObj = wordRepository.findRandomWordByLevel(selectedLevel);
        model.addAttribute("word", wordObj);
        session.setAttribute("word", wordObj);
        session.setAttribute("chancesLeft", 5); // Set initial chances
        return "redirect:/showWord";
    }

    @GetMapping("/showWord")
    public String showWord(HttpSession session, Model model) {
        Word wordarray = (Word) session.getAttribute("word");
        model.addAttribute("GivenHints", wordarray.getHints());
        return "wordInput";
    }



    @GetMapping("/panel/words")
    public String showWords(Model model) {
        List<Word> words = wordRepository.findAll();
        model.addAttribute("words", words);
        return "panel";
    }





    // Add a new word
    @PostMapping("/panel/addWord")
    public String addWord(@ModelAttribute Word newWord) {
        wordRepository.save(newWord);
        return "redirect:/panel/words";
    }

    // Update an existing word
    @PostMapping("/panel/updateWord")
    public String updateWord(@ModelAttribute Word updatedWord) {
        wordRepository.save(updatedWord);
        return "redirect:/panel/words";
    }

    // Delete an existing word
    @PostMapping("/panel/deleteWord")
    public String deleteWord(@RequestParam Long deleteWordId) {
        wordRepository.deleteById(deleteWordId);
        return "redirect:/panel/words";
    }












    @PostMapping("/getWord")
    public String login(@RequestParam String word, HttpSession session, Model model) {
        Word wordObj = (Word) session.getAttribute("word");
        int chancesLeft = (int) session.getAttribute("chancesLeft");

        if (word != null && wordObj.getWord().equalsIgnoreCase(word)) {
            model.addAttribute("message", "Congratulations! You win");
            session.removeAttribute("chancesLeft");


            User user = (User) session.getAttribute("user");


            if (user != null) {
                int currentScore = user.getScore();
                int newScore = currentScore + 5;
                user.setScore(newScore);
                userService.updateScore(user.getId(), newScore);
            }

            return "wordInput";
        } else {
            chancesLeft--;
            if (chancesLeft == 0) {
                model.addAttribute("message", "Sorry! You Lose. No more chances left.");
                session.removeAttribute("chancesLeft");
            } else {
                model.addAttribute("message", "Please Try again. Left tries: " + chancesLeft);
                session.setAttribute("chancesLeft", chancesLeft);
            }
            return "wordInput";
        }
    }
}