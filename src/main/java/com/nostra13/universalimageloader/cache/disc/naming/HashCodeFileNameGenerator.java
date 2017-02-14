package com.nostra13.universalimageloader.cache.disc.naming;

public class HashCodeFileNameGenerator implements FileNameGenerator {
    public String generate(String imageUri) {
        return String.valueOf(imageUri.hashCode());
    }
}
