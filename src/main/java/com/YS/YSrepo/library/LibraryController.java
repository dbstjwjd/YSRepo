package com.YS.YSrepo.library;

import com.YS.YSrepo.member.Member;
import com.YS.YSrepo.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.InterruptedNamingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Library> list = libraryService.getList();
        model.addAttribute("list", list);
        return "library/library";
    }

    @GetMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String create() {
        return "library/write";
    }

    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String create(String title, String content, MultipartFile[] files, Principal principal) throws IOException {
        Member member = memberService.getByUsername(principal.getName());
        Library library = libraryService.createData(title, content, member);
        if (!files[0].getOriginalFilename().isEmpty()) {
            libraryService.uploadFiles(library, files);
        }
        return "redirect:/library/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Library library = libraryService.getData(id);
        model.addAttribute("data", library);
        return "library/detail";
    }

    @GetMapping("/download/{id}/{file}")
    public ResponseEntity<Resource> download(@PathVariable("id") Integer id, @PathVariable("file") String file) throws MalformedURLException, UnsupportedEncodingException {
        Path filePath = Paths.get("uploads/library/" + id + "/" + file);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            String encodedFileName = URLEncoder.encode(resource.getFilename(), "UTF-8").replace("+", "%20");

            String contentDisposition = ContentDisposition.builder("attachment")
                    .filename(resource.getFilename(), StandardCharsets.UTF_8)
                    .build()
                    .toString();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } else {
            throw new RuntimeException("파일을 읽을 수 없습니다.");
        }
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Library library = libraryService.getData(id);
        if (!principal.getName().equals(library.getMember().getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        libraryService.deleteData(library);
        return "redirect:/library/list";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, Model model, Principal principal) {
        Library library = libraryService.getData(id);
        if (!principal.getName().equals(library.getMember().getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        model.addAttribute("data", library);
        return "library/write";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, String title, String content, MultipartFile[] files, Principal principal) throws IOException {
        Library library = libraryService.getData(id);
        if (!principal.getName().equals(library.getMember().getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        libraryService.modifyData(library, title, content);
        if (!files[0].getOriginalFilename().isEmpty()) {
            libraryService.uploadFiles(library, files);
        }
        return "redirect:/library/detail/" + id;
    }

}
