package io.github.mat3e;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LangRepository {
    private List<Language> languages;

    LangRepository(){
        languages = new ArrayList<>();
        languages.add(new Language(1L,"Hello", "en"));
        languages.add(new Language(2L,"Siema", "pl"));
    }

    Optional<Language> findById(Long id){
        return languages.stream().filter(l -> l.getId().equals(id)).findFirst();
    }
}
