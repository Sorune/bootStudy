package com.sorune.bootstudy.controller;

import com.sorune.bootstudy.dto.BoardDTO;
import com.sorune.bootstudy.dto.PageRequestDTO;
import com.sorune.bootstudy.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping({"/"})
    public String list(Model model) {

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list........"+pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping({"/register"})
    public void register(){
        log.info("register.........get");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO board, RedirectAttributes rtts) {
        log.info("dto...."+board);

        //add Entity num
        Long bno = boardService.register(board);
        rtts.addFlashAttribute("msg",bno);
        return "redirect:board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("bno : "+bno);
        BoardDTO dto = boardService.read(bno);
        log.info("dto : "+dto);
        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes rtts) {
        log.info("bno : "+bno);
        boardService.removeWithReplies(bno);

        rtts.addFlashAttribute("msg",bno);
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO board, @ModelAttribute("requestDTO") PageRequestDTO requestDTO , RedirectAttributes rtts) {
        log.info("dto : "+board);
        boardService.modify(board);

        rtts.addAttribute("page",requestDTO.getPage());
        rtts.addAttribute("msg",board.getBno());

        return "redirect:/board/read";
    }
    
}
