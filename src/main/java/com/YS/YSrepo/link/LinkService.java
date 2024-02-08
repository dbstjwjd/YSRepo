package com.YS.YSrepo.link;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public void saveLink(String name, String address) {
        Link link = new Link();
        link.setName(name);
        link.setAddress(address);
        linkRepository.save(link);
    }

    public void modifyLink(Link link, String name, String address) {
        link.setName(name);
        link.setAddress(address);
        linkRepository.save(link);
    }

    public void deleteLink(Link link) {
        linkRepository.delete(link);
    }

    public List<Link> getList() {
        return linkRepository.findAll();
    }

    public Link getLink(Integer id) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isPresent()) return link.get();
        else throw new RuntimeException("존재하지 않는 데이터입니다.");
    }
}
