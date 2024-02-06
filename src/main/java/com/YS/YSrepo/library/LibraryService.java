package com.YS.YSrepo.library;

import com.YS.YSrepo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private String uploadPath = "uploads/library";


    public Library createData(String title, String content, Member member) {
        Library library = new Library();
        library.setTitle(title);
        library.setContent(content);
        library.setCreateDate(LocalDateTime.now());
        library.setMember(member);
        libraryRepository.save(library);
        return library;
    }

    public void uploadFiles(Library library, MultipartFile[] files) throws IOException {
        File uploadDirectory = new File(uploadPath + "/" + library.getId());
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
        if (library.getFilePath() == null) library.setFilePath(new ArrayList<>());
        for (MultipartFile newFile : files) {
            for (int i = 0; i < library.getFilePath().size(); i++) {
                String fileName = newFile.getOriginalFilename();
                if (!library.getFilePath().get(i).equals(fileName)) {
                    File dest = new File(uploadDirectory.getPath() + File.separator + fileName);
                    FileCopyUtils.copy(newFile.getBytes(), dest);
                    library.getFilePath().add(fileName);
                }
            }
        }
        libraryRepository.save(library);
    }

    public List<Library> getList() {
        return libraryRepository.findAll();
    }

    public Library getData(Integer id) {
        Optional<Library> data = libraryRepository.findById(id);
        if (data.isPresent()) return data.get();
        else throw new RuntimeException("존재하지 않는 데이터입니다.");
    }

    public void deleteData(Library library) {
        libraryRepository.delete(library);
    }

    public void modifyData(Library library, String title, String content) {
        library.setTitle(title);
        library.setContent(content);
        libraryRepository.save(library);
    }
}
