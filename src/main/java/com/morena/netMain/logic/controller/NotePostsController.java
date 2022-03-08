package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PNotePosts;
import com.morena.netMain.logic.service.NotePostsService;
import com.morena.netMain.logic.utils.LocalDateConvertor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class NotePostsController {

    private final NotePostsService notePostsService;

    @GetMapping("/getPost/{id}")
    public ResponseEntity<PNotePosts> getPost(@PathVariable Long id){
        return null;
    }

    @PostMapping("/createPost")
    public ResponseEntity<String> createPost(@RequestBody PNotePosts post){
        return null;
    }

    @PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(@RequestBody PNotePosts post){
        return null;
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestBody PNotePosts post){
        return null;
    }

    @GetMapping("/getContent")
    public ResponseEntity<List<PNotePosts>> getPosts(){
        return ResponseEntity.ok(notePostsService.getAllWithScope());
    }

    @GetMapping("/getFilteredPosts")
    public ResponseEntity<List<PNotePosts>> getFilteredPosts(
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate to,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) Boolean inHead,
            @RequestParam(required = false) Boolean inContent,
            @RequestParam(required = false) Boolean inComments){
        return null;
    }
}
