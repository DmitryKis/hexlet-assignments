package exercise.mapper;

import exercise.dto.*;
import exercise.model.Author;
import exercise.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class BookMapper {

    // BEGIN
    public abstract Book map(BookDTO dto);

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    public abstract BookDTO map(Book dto);

    @Mapping(source = "authorId", target = "author")
    public abstract Book map(BookCreateDTO dto);
    // END

    @Mapping(target = "author", source = "authorId")
    public abstract void update(BookUpdateDTO dto, @MappingTarget Book model);
}
