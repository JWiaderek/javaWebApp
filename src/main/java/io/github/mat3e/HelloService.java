package io.github.mat3e;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class HelloService {
    static final String fallbackName = "world";
    static final Language fallbackLang = new Language(1L, "Hello", "en");
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);
    private LangRepository repository;

    HelloService(){
        this(new LangRepository());
    }
    HelloService(LangRepository repository){
        this.repository = repository;
    }
    String prepareGreeting(String name, String langId){
        Long langIdNum;
        try{
            langIdNum = Optional.ofNullable(langId).map(Long::valueOf).orElse(fallbackLang.getId());
        } catch (NumberFormatException e){
            logger.warn("Language id is non-numeric: " + langId);
            langIdNum = fallbackLang.getId();
        }
        var welcomeMsg = repository.findById(langIdNum).orElse(fallbackLang).getWelcomeMessage();
        return welcomeMsg + " " + Optional.ofNullable(name).orElse(fallbackName);
    }
}
