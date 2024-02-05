package com.YS.YSrepo.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private String uploadPath = "uploads/library";


    public Library createData(String title, String content) {
        Library library = new Library();
        library.setTitle(title);
        library.setContent(content);
        library.setCreateDate(LocalDateTime.now());
        libraryRepository.save(library);
        return library;
    }

    public void uploadFiles(Library library, MultipartFile[] files) throws IOException {
        File uploadDirectory = new File(uploadPath + "/" + library.getId());
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
        if (library.getFilePath() == null) library.setFilePath(new ArrayList<>());
        for (MultipartFile file : files) {
            String fileName = library.getId() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDirectory.getPath() + File.separator + fileName);
            FileCopyUtils.copy(file.getBytes(), dest);
            library.getFilePath().add("/library/file/" + fileName);
        }
        libraryRepository.save(library);
    }

    public List<Library> getList() {
        return libraryRepository.findAll();
    }
}
