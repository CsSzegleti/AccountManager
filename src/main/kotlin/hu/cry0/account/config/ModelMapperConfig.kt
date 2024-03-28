package hu.cry0.account.config

import hu.cry0.account.controller.dto.NewTransactionRequest
import hu.cry0.account.model.Transaction
import org.modelmapper.ModelMapper
import org.modelmapper.TypeMap
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant

@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT

        val typeMap: TypeMap<NewTransactionRequest, Transaction> = modelMapper.createTypeMap(NewTransactionRequest::class.java, Transaction::class.java)
        typeMap.addMappings { mapper ->
            mapper.map( { src -> src.timeStamp?: Instant.now()}, Transaction::timeStamp.setter)
        }
        return modelMapper
    }
}