package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PNoteComments;
import com.morena.netMain.logic.service.NoteCommentsService;
import lombok.RequiredArgsConstructor;
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
        if (comment == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/createComment")
    public ResponseEntity<String> createComment(@RequestBody PNoteComments comment){
        return null;
    }

    @PutMapping("/updateComment")
    public ResponseEntity<String> updateComment(@RequestBody PNoteComments comment){
        return null;
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<String> deleteComment(@RequestBody PNoteComments comment){
        return null;
    }

    @GetMapping("/getCommentsByPost/{id}")
    public ResponseEntity<List<PNoteComments>> getCommentsByPost(@PathVariable Long id){
        return null;
    }
}
