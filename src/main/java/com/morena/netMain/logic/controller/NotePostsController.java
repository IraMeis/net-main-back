package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.model.dao.PNotePosts;
import com.morena.netMain.logic.model.filter.PostFilterRequest;
import com.morena.netMain.logic.service.NotePostsService;
import com.morena.netMain.logic.utils.LocalDateConvertor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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

        if(isNullPost(post))
            ResponseEntity.badRequest().build();

        notePostsService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    /**
     * /api/post/updatePost
     * @param post
     * @return
     */
    @PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(@RequestBody PNotePosts post){

        if(isNullPost(post))
            ResponseEntity.badRequest().build();

        return notePostsService.updatePost(post) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.notFound().build();
    }

    private static boolean isNullPost(PNotePosts post){
        return post.getContent() == null || post.getHeader() == null ||
                post.getScope() == null || post.getScope().getValue() == null;
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
     * /api/post/undeletePost/{id}
     * @param id
     * @return
     */
    @DeleteMapping("/undeletePost/{id}")
    public ResponseEntity<String> undeletePost(@PathVariable Long id){
        return notePostsService.undeletePost(id) ?
            ResponseEntity.ok("Undeleted") :
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
    @GetMapping("/getFilteredPosts")//todo @QuerydslPredicate or filterRequest (+ postMapping) as params
    public ResponseEntity<Collection<PNotePosts>> getFilteredPosts(
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate to,
            @RequestParam(required = false) String label,
            @RequestParam(required = false, defaultValue = "true") Boolean inHead,
            @RequestParam(required = false, defaultValue = "true") Boolean inContent,
            @RequestParam(required = false, defaultValue = "true") Boolean inComments,
            @RequestParam(required = false) Long[] scopes,
            @RequestParam(required = false) Long[] commentatorIds){

        PostFilterRequest request = PostFilterRequest
                .builder()
                    .from(from)
                    .to(to)
                    .label(label)
                    .inComment(inComments)
                    .inContent(inContent)
                    .inHead(inHead)
                    .commentatorIds(commentatorIds == null ? null: List.of(commentatorIds))
                    .scopes(scopes == null ? null: List.of(scopes))
                .build();

        return ResponseEntity.ok(request.isNullFilter() ?
                notePostsService.getAll() :
                notePostsService.getFilteredPosts(request));
    }
}
