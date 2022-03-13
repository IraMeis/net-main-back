package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PNoteComments;
import com.morena.netMain.logic.service.NoteCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class NoteCommentsController {

    private  final NoteCommentsService noteCommentsService;

    /**
     * /api/comment/getComment/{id}
     * @param id
     * @return
     */
    @GetMapping("/getComment/{id}")
    public ResponseEntity<PNoteComments> getComment(@PathVariable Long id) {
        PNoteComments comment = noteCommentsService.getCommentById(id);
        return comment == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(comment);
    }

    /**
     * /api/comment/createComment
     * @param comment
     * @return
     */
    @PostMapping("/createComment")
    public ResponseEntity<String> createComment(@RequestBody PNoteComments comment){
        return noteCommentsService.createComment(comment) ?
                ResponseEntity.status(HttpStatus.CREATED).body("Created") :
                ResponseEntity.badRequest().build();
    }

    /**
     * /api/comment/updateComment
     * @param comment
     * @return
     */
    @PutMapping("/updateComment")
    public ResponseEntity<String> updateComment(@RequestBody PNoteComments comment){
        return noteCommentsService.updateComment(comment) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.badRequest().build();
    }

    /**
     * /api/comment/deleteComment/{id}
     * @param id
     * @return
     */
    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        noteCommentsService.deleteComment(id);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * /api/comment/getCommentsByPost/{id}
     * @param id
     * @return
     */
    @GetMapping("/getCommentsByPost/{id}")
    public ResponseEntity<List<PNoteComments>> getCommentsByPost(@PathVariable Long id){
        return ResponseEntity.ok(noteCommentsService.getAllByPostRef(id));
    }
}
