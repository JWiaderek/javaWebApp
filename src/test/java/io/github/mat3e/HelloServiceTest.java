package io.github.mat3e;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HelloServiceTest {
    private final static  String welcome = "Hello";
    private final static String fallbackIdWelcome = "Hola";

    @Test
    public void test_prepareGreeting_nullName_returnsFallback() throws Exception {
        var mockRepo = getMockLangRepo();
        var ServiceTest = new HelloService(mockRepo);
        String name = null;
        var result = ServiceTest.prepareGreeting(null, "-1");
        assertEquals(welcome + " " + HelloService.fallbackName,result);
    }

    @Test
    public void test_prepareGreeting_name_returnsName() throws Exception {
        var mockRepo = getMockLangRepo();
        var ServiceTest = new HelloService(mockRepo);
        String name = "sample";
        var result = ServiceTest.prepareGreeting(name, "-1");
        assertEquals(welcome + " " + name,result);
    }

    @Test
    public void test_prepareGreeting_nullLanguage_returnsFallbackId() throws Exception {
        var mockRepo = fallbackLangIdRepository(fallbackIdWelcome);
        var ServiceTest = new HelloService(mockRepo);

        var result = ServiceTest.prepareGreeting(null, null);
        assertEquals(fallbackIdWelcome + " " + HelloService.fallbackName,result);
    }

    @Test
    public void test_prepareGreeting_textLanguage_returnsFallbackId() throws Exception {
        var mockRepo = fallbackLangIdRepository(fallbackIdWelcome);
        var ServiceTest = new HelloService(mockRepo);

        var result = ServiceTest.prepareGreeting(null, "abc");
        assertEquals(fallbackIdWelcome + " " + HelloService.fallbackName,result);
    }

    @Test
    public void test_prepareGreeting_nonExistingLanguage_returnsFallbackId() throws Exception {
        var mockRepo = new LangRepository() {
            @Override
            Optional<Language> findById(Long id) {
                return Optional.empty();
            }
        };
        var ServiceTest = new HelloService(mockRepo);

        var result = ServiceTest.prepareGreeting(null, null);
        assertEquals("Hello " + HelloService.fallbackName,result);
    }

    private static LangRepository fallbackLangIdRepository(String fallbackIdWelcome) {
        return new LangRepository() {
            @Override
            Optional<Language> findById(Long id) {
                if (id.equals(HelloService.fallbackLang.getId())) {
                    return Optional.of(new Language(null, fallbackIdWelcome, null));
                }
                return Optional.empty();
            }
        };
    }


    private LangRepository getMockLangRepo() {
        return new LangRepository() {
            @Override
            Optional<Language> findById(Long id) {
                return Optional.of(new Language(null, welcome, null));
            }
        };
    }
}
