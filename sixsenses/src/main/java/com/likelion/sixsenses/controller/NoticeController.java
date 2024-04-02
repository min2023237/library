package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.dto.NoticeResponse;
import com.likelion.sixsenses.entity.Notice;
import com.likelion.sixsenses.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/")
    public String homeView(Model model) {
        model.addAttribute("notices", noticeService.findAll());
        return "allNoticeList";
    }

    @GetMapping("/detail/{id}")
    public String readOneView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("notice", noticeService.findById(id));
        return "noticeDetail";
    }

    @GetMapping("/add")
    public String addView() {
        return "noticeAdd";
    }

    @PostMapping("/api/addNotice")
    public String addNotice(
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("writer")
            String writer
    ) {
        Notice savedNotice = noticeService.save(title, content, writer);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("notice", noticeService.findById(id));

        return "noticeModify";
    }

    @GetMapping("/api/notices")
    public ResponseEntity<List<NoticeResponse>> findAllNotices() {
        List<NoticeResponse> notices = noticeService.findAll()
                .stream()
                .map(NoticeResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(notices);
    }

    @GetMapping("/api/notice/{id}")
    public ResponseEntity<NoticeResponse> findNotice(@PathVariable long id) {
        Notice notice = noticeService.findById(id);

        return ResponseEntity.ok()
                .body(new NoticeResponse(notice));
    }

    @DeleteMapping("/api/notice/{id}")
    public /*ResponseEntity<Void>*/ String deleteNotice(
            @PathVariable Long id) {
        noticeService.delete(id);

//        return ResponseEntity.ok()
//                .build();
        return "redirect/";
    }

    @PutMapping("/api/notice/{id}")
    public /*ResponseEntity<Notice>*/ String updateNotice(
            @PathVariable("id")
            Long id,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("writer")
            String writer
    ) {
//        Notice updateNotice = noticeService.update(id, request);
//        return ResponseEntity.ok()
//                .body(updateArticle);
        noticeService.update(
                id, title, content, writer);
        return String.format("redirect:/delete/%d", id);
    }

}
