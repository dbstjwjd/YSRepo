package com.YS.YSrepo.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Library> list = libraryService.getList();
        model.addAttribute("list", list);
        return "library/library";
    }

    @GetMapping("/write")
    public String create() {
        return "library/write";
    }

    @PostMapping("/write")
    public String create(String title, String content, MultipartFile[] files) throws IOException {
        Library library = libraryService.createData(title, content);
        if (!files[0].getOriginalFilename().isEmpty()) {
            libraryService.uploadFiles(library, files);
        }
        return "redirect:/library/list";
    }
}
