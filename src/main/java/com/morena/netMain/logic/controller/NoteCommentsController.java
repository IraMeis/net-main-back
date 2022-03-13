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

    @GetMapping("/getComment/{id}")
    public ResponseEntity<PNoteComments> getComment(@PathVariable Long id) {
        PNoteComments comment = noteCommentsService.getCommentById(id);
        return comment == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(comment);
    }

    @PostMapping("/createComment")
    public ResponseEntity<String> createComment(@RequestBody PNoteComments comment){
        return noteCommentsService.createComment(comment) ?
                ResponseEntity.status(HttpStatus.CREATED).body("Created") :
                ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateComment")
    public ResponseEntity<String> updateComment(@RequestBody PNoteComments comment){
        return noteCommentsService.updateComment(comment) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        noteCommentsService.deleteComment(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/getCommentsByPost/{id}")
    public ResponseEntity<List<PNoteComments>> getCommentsByPost(@PathVariable Long id){
        return ResponseEntity.ok(noteCommentsService.getAllByPostRef(id));
    }
}
