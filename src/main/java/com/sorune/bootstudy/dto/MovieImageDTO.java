package com.sorune.bootstudy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String path;
    public String getImageURL(){
        try {
            return URLDecoder.decode(path+"/"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getThumbImageURL(){
        try {
            return URLDecoder.decode(path+"/s_"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
