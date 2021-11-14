package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guzt
 */
@SpringBootApplication
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        String banner = "\n" +

                "       __                              __                        __                            __                      __              __ \n" +
                "      /  |                            /  |                      /  |                          /  |                    /  |            /  |\n" +
                "      $$ |____    ______    ______   _$$ |_           _______  _$$ |_     ______    ______   _$$ |_           ______  $$ |   __       $$ |\n" +
                "      $$      \\  /      \\  /      \\ / $$   |         /       |/ $$   |   /      \\  /      \\ / $$   |         /      \\ $$ |  /  |      $$ |\n" +
                "      $$$$$$$  |/$$$$$$  |/$$$$$$  |$$$$$$/         /$$$$$$$/ $$$$$$/    $$$$$$  |/$$$$$$  |$$$$$$/         /$$$$$$  |$$ |_/$$/       $$ |\n" +
                "      $$ |  $$ |$$ |  $$ |$$ |  $$ |  $$ | __       $$      \\   $$ | __  /    $$ |$$ |  $$/   $$ | __       $$ |  $$ |$$   $$<        $$/ \n" +
                "      $$ |__$$ |$$ \\__$$ |$$ \\__$$ |  $$ |/  |       $$$$$$  |  $$ |/  |/$$$$$$$ |$$ |        $$ |/  |      $$ \\__$$ |$$$$$$  \\        __ \n" +
                "      $$    $$/ $$    $$/ $$    $$/   $$  $$/       /     $$/   $$  $$/ $$    $$ |$$ |        $$  $$/       $$    $$/ $$ | $$  |      /  |\n" +
                "      $$$$$$$/   $$$$$$/   $$$$$$/     $$$$/        $$$$$$$/     $$$$/   $$$$$$$/ $$/          $$$$/         $$$$$$/  $$/   $$/       $$/ \n" +
                "                                                                                                                                          ";
        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }


        logger.info(banner);
    }

}
