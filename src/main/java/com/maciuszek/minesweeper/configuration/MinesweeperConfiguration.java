package com.maciuszek.minesweeper.configuration;

import com.maciuszek.minesweeper.domain.serializer.MinesweeperBoardJsonSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("minesweeper")
@Data
public class MinesweeperConfiguration {

    @NotNull
    private Integer boardSize;
    @NotNull
    private Integer bombCount;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializers(new MinesweeperBoardJsonSerializer());
    }

}
