package com.morena.netMain.logic.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ResponseIf<T> extends Option<T, ErrorDescription> {

    public ResponseIf (String reason){
        this.setObject(null);
        this.setOr(new Message(reason));
    }

    public ResponseIf (T obj){
        this.setObject(obj);
        this.setOr(null);
    }

    public ResponseEntity<?> get (){
        if(getObject()==null && getOr()==null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        if(getObject()!= null)
            return ResponseEntity.status(HttpStatus.OK).body(getObject());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getOr());

    }

    public ResponseEntity<?> get (HttpStatus ok, HttpStatus notOk){
        if(getObject()==null && getOr()==null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        if(getObject()!= null)
            return ResponseEntity.status(ok).body(getObject());

        return ResponseEntity.status(notOk).body(getOr());

    }
}
