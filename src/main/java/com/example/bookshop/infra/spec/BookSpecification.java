package com.example.bookshop.infra.spec;


import com.example.bookshop.entity.BookEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
@Join(path = "authors",alias = "auth")
@And({
        @Spec(path = "auth.name" , params = "authorName" , spec = Like.class),
        @Spec(path = "title", params = "bookTitle", spec = Like.class)
})
public interface BookSpecification extends Specification<BookEntity> {

}
