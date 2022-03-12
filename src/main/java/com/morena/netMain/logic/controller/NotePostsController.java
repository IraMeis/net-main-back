package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PNotePosts;
import com.morena.netMain.logic.service.NotePostsService;
import com.morena.netMain.logic.utils.LocalDateConvertor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class NotePostsController {

    private final NotePostsService notePostsService;

    /**
     * /api/post/getPost/{id}
     * @param id
     * @return
     */
    @GetMapping("/getPost/{id}")
    public ResponseEntity<PNotePosts> getPost(@PathVariable Long id){
        PNotePosts post = notePostsService.getById(id);
        return post == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(post);
    }

    /**
     * /api/post/createPost
     * @param post
     * @return
     */
    @PostMapping("/createPost")
    public ResponseEntity<String> createPost(@RequestBody PNotePosts post){
        notePostsService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * /api/post/updatePost
     * @param post
     * @return
     */
    @PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(@RequestBody PNotePosts post){
        return notePostsService.updatePost(post) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.notFound().build();
    }

    /**
     * /api/post/deletePost/{id}
     * @param id
     * @return
     */
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        notePostsService.deletePost(id);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * /api/post/getContent
     * @return
     */
    @GetMapping("/getContent")
    public ResponseEntity<List<PNotePosts>> getPosts(){
        return ResponseEntity.ok(notePostsService.getAllWithScope());
    }

    /**
     * /api/post/getFilteredPosts
     * @param from
     * @param to
     * @param label
     * @param inHead
     * @param inContent
     * @param inComments
     * @return
     */
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
